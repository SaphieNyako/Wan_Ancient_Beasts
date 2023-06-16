package github.saphienyako.wan_ancient_beasts.entity.goals;

import github.saphienyako.wan_ancient_beasts.entity.Eater;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.ai.goal.Goal;

import javax.annotation.Nonnull;
import java.util.EnumSet;

public class SleepingCheckGoal extends Goal {

    private final Eater entity;
    private final Goal parent;

    public SleepingCheckGoal(Eater entity, Goal parent) {
        this.entity = entity;;
        this.parent = parent;
    }

    @Override
    public boolean canContinueToUse() {
        return !this.entity.shouldBeSleeping() && this.parent.canContinueToUse();
    }

    @Override
    public boolean isInterruptable() {
        return this.parent.isInterruptable();
    }

    @Override
    public void start() {
        this.parent.start();
    }

    @Override
    public void stop() {
        this.parent.stop();
    }

    @Override
    public void tick() {
        this.parent.tick();
    }

    @Override
    public void setFlags(@Nonnull EnumSet<Flag> flags) {
        this.parent.setFlags(flags);
    }

    @Nonnull
    @Override
    public EnumSet<Flag> getFlags() {
        return this.parent.getFlags();
    }

    @Override
    public boolean canUse() {
        return !this.entity.shouldBeSleeping() && this.parent.canUse();
    }
}
