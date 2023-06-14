package github.saphienyako.wan_ancient_beasts.entity.goals;

import github.saphienyako.wan_ancient_beasts.entity.Eater;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.animal.PolarBear;
import net.minecraft.world.entity.player.Player;

public class EaterAttackPlayerGoal extends NearestAttackableTargetGoal<Player> {

    protected final Eater entity;


    public EaterAttackPlayerGoal(Eater entity, Class<Player> player, boolean mustSee) {
        super(entity, player, mustSee);
        this.entity = entity;
    }

    @Override
    public boolean canContinueToUse() {
        return super.canContinueToUse() && entity.getRoared();
    }

    public boolean canUse() {
        if (!entity.isBaby()) {
            if (super.canUse()) {
                for (Eater eater : entity.level().getEntitiesOfClass(Eater.class, entity.getBoundingBox().inflate(16.0D, 8.0D, 16.0D))) {
                    if (eater.isBaby()) {
                        return this.entity.getRoared();
                    }
                }
            }
        }
        return false;
    }

    @Override
    public void stop() {
        super.stop();
        this.entity.setRoared(false);
    }

    @Override
    protected double getFollowDistance() {
        return super.getFollowDistance() * 0.5D;
    }
}
