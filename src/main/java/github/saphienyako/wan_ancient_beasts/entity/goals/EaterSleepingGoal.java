package github.saphienyako.wan_ancient_beasts.entity.goals;

import github.saphienyako.wan_ancient_beasts.entity.Eater;
import net.minecraft.world.entity.ai.goal.Goal;

public class EaterSleepingGoal extends Goal {

    private final Eater entity;

    public EaterSleepingGoal(Eater entity){
        this.entity = entity;
    }

    @Override
    public void start() {
        this.entity.setState(Eater.State.SLEEP);
        super.start();
    }

    @Override
    public boolean canContinueToUse() {
        return entity.shouldBeSleeping() && entity.getLastHurtByMob() == null;
    }

    @Override
    public boolean canUse() {
        return entity.shouldBeSleeping();
    }
}
