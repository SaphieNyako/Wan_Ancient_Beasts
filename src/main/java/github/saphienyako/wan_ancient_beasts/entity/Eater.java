package github.saphienyako.wan_ancient_beasts.entity;

import github.saphienyako.wan_ancient_beasts.entity.goals.EaterAttackPlayerGoal;
import github.saphienyako.wan_ancient_beasts.entity.goals.EaterMeleeAttackGoal;
import github.saphienyako.wan_ancient_beasts.entity.goals.RoarFirstGoal;
import github.saphienyako.wan_ancient_beasts.item.ModItems;
import github.saphienyako.wan_ancient_beasts.tags.ModItemTags;
import net.minecraft.core.BlockPos;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.goal.target.ResetUniversalAngerTargetGoal;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.npc.AbstractVillager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.raid.Raider;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.constant.DefaultAnimations;
import software.bernie.geckolib.core.animatable.GeoAnimatable;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.animation.RawAnimation;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.util.GeckoLibUtil;

import java.util.Random;
import java.util.UUID;

public class Eater extends Animal implements GeoEntity, NeutralMob {

    //Eater animation can't be used in a different STATE
    public static final EntityDataAccessor<Integer> STATE = SynchedEntityData.defineId(Eater.class, EntityDataSerializers.INT);
    private static final RawAnimation BITE = RawAnimation.begin().thenPlay("bite");
    private static final RawAnimation RUN = RawAnimation.begin().thenLoop("run");
    private static final RawAnimation SLEEP = RawAnimation.begin().thenLoop("sleep");
    private static final RawAnimation START_SLEEP = RawAnimation.begin().thenPlay("start_sleep");
    private static final RawAnimation WAKE_UP = RawAnimation.begin().thenPlay("wake_up");
    private static final RawAnimation ROAR = RawAnimation.begin().thenPlay("roar");
    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);

    public static final EntityDataAccessor<Boolean> ROARED = SynchedEntityData.defineId(Eater.class, EntityDataSerializers.BOOLEAN);
    public static final EntityDataAccessor<Boolean> RUNNING = SynchedEntityData.defineId(Eater.class, EntityDataSerializers.BOOLEAN);

    protected Eater(EntityType<? extends Animal> animal, Level level) {
        super(animal, level);
        if(!level.isClientSide) MinecraftForge.EVENT_BUS.register(this);
    }

    public static AttributeSupplier.Builder getDefaultAttributes() {
        return Mob.createMobAttributes().add(Attributes.MOVEMENT_SPEED, Attributes.MOVEMENT_SPEED.getDefaultValue())
                .add(Attributes.MAX_HEALTH, 32.0D)
                .add(Attributes.KNOCKBACK_RESISTANCE, 0.5)
                .add(Attributes.ARMOR_TOUGHNESS, 2)
                .add(Attributes.ARMOR, 5)
                .add(Attributes.ATTACK_DAMAGE, 10.0D)
                .add(Attributes.ATTACK_KNOCKBACK, 0.5)
                .add(Attributes.MOVEMENT_SPEED, 0.4D);
    }

    public static boolean canSpawn(EntityType<? extends Eater> entity, LevelAccessor level, MobSpawnType reason, BlockPos pos, RandomSource random) {
        return isBrightEnoughToSpawn(level, pos);
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
       // this.entityData.define(ANGRY, false);
        this.entityData.define(ROARED, false);
        this.entityData.define(RUNNING, false);
        this.entityData.define(STATE, 0);
    }

    @Override
    public boolean isFood(ItemStack stack) {
        return stack.is(ModItemTags.EATER_FOOD);
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(3, new WaterAvoidingRandomStrollGoal(this, 0.4D));
        this.goalSelector.addGoal(6, new LookAtPlayerGoal(this, Player.class, 6.0F));
        this.goalSelector.addGoal(7, new RandomLookAroundGoal(this));
        this.goalSelector.addGoal(2, new BreedGoal(this, 0.5D));
        //roar first
        this.targetSelector.addGoal(5, new RoarFirstGoal(this, Player.class, true));
        //target
        this.targetSelector.addGoal(2, new EaterAttackPlayerGoal(this, Player.class, true));
        this.targetSelector.addGoal(4, new NearestAttackableTargetGoal<>(this, AbstractVillager.class, true, (entity) -> {
            return !entity.isBaby();
        }));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Raider.class, false, false));
        this.targetSelector.addGoal(4, new NearestAttackableTargetGoal<>(this, Animal.class, true, (entity) -> {
            return !(entity instanceof Eater);
        }));
        //TODO attack crusher and charger
        this.targetSelector.addGoal(2, new HurtByTargetGoal(this));
        //move
        this.goalSelector.addGoal(2, new MoveTowardsTargetGoal(this, 0.1f, 16));
        //attack in range
        this.goalSelector.addGoal(1, new EaterMeleeAttackGoal(this, 1.0f, true));
        //reset
        this.targetSelector.addGoal(5, new ResetUniversalAngerTargetGoal<>(this, false));
    }

    @Nullable
    @Override
    public AgeableMob getBreedOffspring(@NotNull ServerLevel level,@NotNull AgeableMob ageableMob) {
        return ModEntities.EATER.get().create(level);
    }

    @Override
    public void tick() {
        super.tick();
        if (!this.level().isClientSide) {
            this.updatePersistentAnger((ServerLevel)this.level(), true);
        }
    }

    public Eater.State getState(){
        Eater.State[] states = Eater.State.values();
        return states[Mth.clamp(this.entityData.get(STATE), 0, states.length -1)];
    }

    public void setState(Eater.State state) {this.entityData.set(STATE, state.ordinal());}

    public boolean getRoared(){return this.entityData.get(ROARED);}

    public void setRoared(boolean roared){this.entityData.set(ROARED, roared);}

    public boolean getRunning(){return this.entityData.get(RUNNING);}

    public void setRunning(boolean running){this.entityData.set(RUNNING, running);}

    @NotNull
    @Override
    public InteractionResult mobInteract(@NotNull Player player,@NotNull InteractionHand hand) {
        ItemStack itemstack = player.getItemInHand(hand);
        if(isFood(itemstack)) {
            return super.mobInteract(player, hand);
        }
        return InteractionResult.SUCCESS;
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controller) {
        controller.add(DefaultAnimations.genericWalkIdleController(this));
       controller.add(eaterRunAnimation(this, RUN));
       controller.add(
               addAnimation(BITE, State.BITE),
               addAnimation(ROAR, State.ROAR),
               addAnimation(SLEEP, State.SLEEP),
               addAnimation(START_SLEEP, State.START_SLEEP),
               addAnimation(WAKE_UP, State.WAKE_UP)
       );
    }

    public static <T extends LivingEntity & GeoAnimatable> AnimationController<T> eaterRunAnimation(T entity, RawAnimation runAnimation) {
        return new AnimationController<>(entity, "run", 1, state -> {
            if (entity instanceof Eater eater && eater.getRunning())
                return state.setAndContinue(runAnimation);

            state.getController().forceAnimationReset();

            return PlayState.STOP;
        });
    }



    private <T extends LivingEntity & GeoAnimatable> AnimationController<?> addAnimation(RawAnimation animation, State animationState) {
        return new AnimationController<>(this, animation.toString(), 0, state -> {
            if (this.getState() == animationState)
                return state.setAndContinue(animation);
            state.getController().forceAnimationReset();
            return PlayState.STOP;
        });
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return cache;
    }



    @Override
    public int getRemainingPersistentAngerTime() {
        return 0;
    }

    @Override
    public void setRemainingPersistentAngerTime(int p_21673_) {

    }

    @Nullable
    @Override
    public UUID getPersistentAngerTarget() {
        return null;
    }

    @Override
    public void setPersistentAngerTarget(@Nullable UUID p_21672_) {

    }

    @Override
    public void startPersistentAngerTimer() {

    }

    @SubscribeEvent
    public void dropTooth(LivingDeathEvent event){
        if (event.getSource().getEntity() == this) {
            Random random = new Random();
            //TODO lower to 2%
            if(random.nextInt(99) < 40) {
                spawnAtLocation(ModItems.EATER_TOOTH.get());
            }
        }
    }

    public enum State {
        IDLE, WALK, ROAR, BITE, START_SLEEP, SLEEP, WAKE_UP
    }
}
