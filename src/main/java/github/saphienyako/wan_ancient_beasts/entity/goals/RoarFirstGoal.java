package github.saphienyako.wan_ancient_beasts.entity.goals;

import github.saphienyako.wan_ancient_beasts.entity.Eater;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.player.Player;

public class RoarFirstGoal extends NearestAttackableTargetGoal<Player> {

    private final Eater entity;
    private int ticksLeft = 0;


    public RoarFirstGoal(Eater entity, Class<Player> player, boolean mustSee) {
        super(entity, player, mustSee);
        this.entity = entity;
    }


    @Override
    public void tick() {
        if(this.ticksLeft > 0 ) {

            this.ticksLeft--;
            if(this.ticksLeft <= 0){
                reset();
            }
            else if( this.ticksLeft == 40){
                this.entity.setState(Eater.State.ROAR);
            }
        }
    }

    private void reset() {
        this.ticksLeft = -1;
        this.entity.setState(Eater.State.IDLE);
    }

    @Override
    public void stop() {
        this.entity.setRoared(true);
    }

    @Override
    public void start() {
          this.ticksLeft = 50;
    }

    @Override
    public boolean canContinueToUse() {
        return this.ticksLeft > 0;
    }

    @Override
    public boolean canUse() {
        //same as AttackPlayer Goal
            if (!entity.isBaby()) {
                if (super.canUse()) {
                    for (Eater eater : entity.level().getEntitiesOfClass(Eater.class, entity.getBoundingBox().inflate(16.0D, 8.0D, 16.0D))) {
                        if (eater.isBaby()) {
                            return true;
                        }
                    }
                }
            }
            return false;
        }
}
