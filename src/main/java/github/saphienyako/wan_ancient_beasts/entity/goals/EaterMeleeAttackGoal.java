package github.saphienyako.wan_ancient_beasts.entity.goals;

import github.saphienyako.wan_ancient_beasts.entity.Eater;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Items;

import javax.annotation.Nonnull;

public class EaterMeleeAttackGoal extends MeleeAttackGoal {

    protected final Eater entity;
    private int ticksLeft = 0;

    public EaterMeleeAttackGoal(Eater entity, double speedModifier, boolean followingTargetEvenIfNotSeen) {
        super(entity, speedModifier, followingTargetEvenIfNotSeen);
        this.entity = entity;
    }

    @Override
    public void tick() {
        if(this.ticksLeft > 0) {
            this.ticksLeft--;
            if(this.ticksLeft <= 0){
                reset();
            } else if(this.ticksLeft == 10) {
                //TODO play sound
                LivingEntity livingentity = this.mob.getTarget();
                if (livingentity != null) {
                    this.mob.getLookControl().setLookAt(livingentity, 30.0F, 30.0F);
                    double d0 = this.mob.getPerceivedTargetDistanceSquareForMeleeAttack(livingentity);
                    this.checkAndPerformAttack(livingentity, d0);
                }
            } else if(this.ticksLeft == 25) {
                this.entity.setState(Eater.State.BITE);
            }
        }
    }

    @Override
    protected void checkAndPerformAttack(@Nonnull LivingEntity enemy, double distanceToEnemySqr) {
        double d0 = this.getAttackReachSqr(enemy);
        if (distanceToEnemySqr <= d0 + 4 && getTicksUntilNextAttack() <= 0) {
            this.resetAttackCooldown();
            this.mob.doHurtTarget(enemy);
            //remove shield on impact
            if (enemy instanceof Player player) {

                for (int k = 0; k < player.getInventory().offhand.size(); ++k) {
                    if (!player.getInventory().offhand.get(k).isEmpty() && player.getInventory().offhand.get(k).getItem() == Items.SHIELD) {
                        player.getInventory().offhand.get(k).shrink(1);
                    }
                }
            }
        }
    }

    @Override
    public void start() {
        this.ticksLeft = 30;
        this.entity.setState(Eater.State.IDLE);
        this.entity.setRunning(true);
        super.start(); //move to target
    }

    private void reset(){
        this.entity.setRunning(false);
        this.entity.setState(Eater.State.IDLE);
        this.ticksLeft = -1;
    }

    @Override
    public boolean canContinueToUse() {
        return this.ticksLeft > 0 && super.canContinueToUse();
    }

    @Override
    public void stop() {
        this.entity.setRunning(false);
        this.entity.setState(Eater.State.IDLE);
        super.stop();
    }
}
