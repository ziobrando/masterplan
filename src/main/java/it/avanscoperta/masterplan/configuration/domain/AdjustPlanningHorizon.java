package it.avanscoperta.masterplan.configuration.domain;

import org.axonframework.modelling.command.TargetAggregateIdentifier;

import java.time.Duration;

public record AdjustPlanningHorizon(
        @TargetAggregateIdentifier
        UserId userId,
        Duration duration) {
}

