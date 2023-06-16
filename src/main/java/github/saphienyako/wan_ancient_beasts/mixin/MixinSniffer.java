package github.saphienyako.wan_ancient_beasts.mixin;


import github.saphienyako.wan_ancient_beasts.entity.Eater;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.goal.AvoidEntityGoal;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.sniffer.Sniffer;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(Sniffer.class)
public abstract class MixinSniffer extends Animal {
    protected MixinSniffer(EntityType<? extends Animal> p_27557_, Level p_27558_) {
        super(p_27557_, p_27558_);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(3, new AvoidEntityGoal<>(this, Eater.class, 16.0F, 1.5D, 1.6D));
    }
}
