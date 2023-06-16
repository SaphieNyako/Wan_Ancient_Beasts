package github.saphienyako.wan_ancient_beasts.mixin;

import github.saphienyako.wan_ancient_beasts.entity.Eater;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.goal.AvoidEntityGoal;
import net.minecraft.world.entity.npc.AbstractVillager;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(Villager.class)
public abstract class MixinVillager extends AbstractVillager {
    public MixinVillager(EntityType<? extends AbstractVillager> p_35267_, Level p_35268_) {
        super(p_35267_, p_35268_);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(3, new AvoidEntityGoal<>(this, Eater.class, 16.0F, 1.2D, 1.5D));
    }

}
