package it.avanscoperta.masterplan.planning.domain;

import java.time.LocalTime;

public record BetweenHoursConstraint(LocalTime dailyStart, LocalTime dailyEnd)
        implements PlanningConstraint{
}
