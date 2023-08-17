package it.avanscoperta.masterplan.configuration.domain;

import java.time.Duration;

public record PlanningHorizon(Duration duration) {
    public static final PlanningHorizon DEFAULT = new PlanningHorizon(Duration.ofDays(15));
}
