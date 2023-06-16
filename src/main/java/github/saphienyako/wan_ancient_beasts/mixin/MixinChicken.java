package github.saphienyako.wan_ancient_beasts.mixin;

import github.saphienyako.wan_ancient_beasts.entity.Eater;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.goal.AvoidEntityGoal;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.Chicken;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Chicken.class)
public abstract class MixinChicken extends Animal {
    protected MixinChicken(EntityType<? extends Animal> p_27557_, Level p_27558_) {
        super(p_27557_, p_27558_);
    }

    @Inject(at=@At("HEAD"), method = "registerGoals()V", cancellable = false)
    protected void registerGoals(CallbackInfo ci) {
        this.goalSelector.addGoal(3, new AvoidEntityGoal<>(this, Eater.class, 16.0F, 2.0D, 2.0D));
    }
}
