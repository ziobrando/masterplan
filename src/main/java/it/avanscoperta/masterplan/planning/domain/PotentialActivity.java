package it.avanscoperta.masterplan.planning.domain;

import it.avanscoperta.masterplan.common.domain.Priority;
import it.avanscoperta.masterplan.common.domain.Slot;

import java.time.Duration;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;



public class PotentialActivity {
    Duration duration;
    Priority priority;
    private List<PlanningConstraint> constraints = new ArrayList<PlanningConstraint>();

    public PotentialActivity(Duration duration, Priority priority) {
        this.duration = duration;
        this.priority = priority;
    }

    public PotentialActivity(Duration duration) {
        this(duration, Priority.STANDARD);
    }

    public PotentialActivity happeningBetween(LocalTime dailyStart, LocalTime dailyEnd) {
        this.constraints.add(new BetweenHoursConstraint(this, dailyStart, dailyEnd));
        return this;
    }

    public boolean fitsInto(Slot potentialSlot) {
        return shorterThanSlot(potentialSlot) &&
                matchingConstraints(potentialSlot);
    }

    public boolean shorterThanSlot(Slot potentialSlot) {
        return this.duration.compareTo(potentialSlot.timeInterval().duration()) <= 0;
    }

    private boolean matchingConstraints(Slot potentialSlot) {
        return this.constraints
                .stream()
                .map((constraint) -> constraint.isSatisfiedBy(potentialSlot))
                .reduce(Boolean.TRUE, Boolean::logicalAnd);
    }


    public Duration duration() {
        return duration;
    }

    public Priority priority() {
        return priority;
    }

    @Override
    public String toString() {
        return "PotentialActivity{" +
                "duration=" + duration +
                ", priority=" + priority +
                ", constraints=" + constraints +
                '}';
    }
}

