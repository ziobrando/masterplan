package it.avanscoperta.masterplan.configuration.domain;

import java.time.Duration;

public record PlanningHorizonAdjusted(
        UserId userId,
        Duration planningHorizonDuration) {
}
