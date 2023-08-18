package it.avanscoperta.masterplan.configuration.domain;

import it.avanscoperta.masterplan.common.domain.DaysDuration;

public record PlanningHorizon(DaysDuration duration) {
    public static final PlanningHorizon DEFAULT = new PlanningHorizon(DaysDuration.of(15));
}
