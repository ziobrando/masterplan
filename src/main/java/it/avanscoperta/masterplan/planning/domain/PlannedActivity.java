package it.avanscoperta.masterplan.planning.domain;

import it.avanscoperta.masterplan.common.domain.Priority;

import java.time.Duration;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public record PlannedActivity(
        Duration duration,
        Priority priority) {
    private static final List<PlanningConstraint> constraints = new ArrayList<PlanningConstraint>();

    public PlannedActivity(Duration duration) {
        this(duration, Priority.STANDARD);
    }

    public PlannedActivity happeningBetween(LocalTime dailyStart, LocalTime dailyEnd) {
        this.constraints.add(new BetweenHoursConstraint(dailyStart, dailyEnd));
        return this;
    }
}

