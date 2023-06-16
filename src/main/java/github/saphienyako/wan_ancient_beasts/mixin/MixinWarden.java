package github.saphienyako.wan_ancient_beasts.mixin;

import github.saphienyako.wan_ancient_beasts.entity.Eater;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.goal.AvoidEntityGoal;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.warden.Warden;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import javax.annotation.Nullable;

@Mixin(Warden.class)
public abstract class MixinWarden extends Monster {
    @Shadow public abstract Brain<Warden> getBrain();

    protected MixinWarden(EntityType<? extends Monster> p_33002_, Level p_33003_) {
        super(p_33002_, p_33003_);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(3, new AvoidEntityGoal<>(this, Eater.class, 32.0F, 1.5D, 1.6D));
    }


    @Inject(at=@At("HEAD"), method = "canTargetEntity", cancellable = true )
    protected void canTargetEntity(@Nullable Entity p_219386_, CallbackInfoReturnable<LivingEntity> cir) {
    if(p_219386_ instanceof Eater){ cir.cancel();}
    }
}
