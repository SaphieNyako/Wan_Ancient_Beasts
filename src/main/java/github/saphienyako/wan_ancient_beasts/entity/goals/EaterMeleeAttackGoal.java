package github.saphienyako.wan_ancient_beasts.entity.goals;

import github.saphienyako.wan_ancient_beasts.entity.Eater;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;

import javax.annotation.Nonnull;

public class EaterMeleeAttackGoal extends MeleeAttackGoal {

    protected final Eater entity;

    public EaterMeleeAttackGoal(Eater entity, double speedModifier, boolean followingTargetEvenIfNotSeen) {
        super(entity, speedModifier, followingTargetEvenIfNotSeen);
        this.entity = entity;
    }

    @Override
    protected void checkAndPerformAttack(@Nonnull LivingEntity enemy, double distanceToEnemySqr) {
        double d0 = this.getAttackReachSqr(enemy);
        if (distanceToEnemySqr <= d0 + 4 && getTicksUntilNextAttack() <= 0) {
            this.resetAttackCooldown();
            this.mob.doHurtTarget(enemy);
        }
    }

    @Override
    public void start() {
        //TODO play sound
        super.start();
    }
}
