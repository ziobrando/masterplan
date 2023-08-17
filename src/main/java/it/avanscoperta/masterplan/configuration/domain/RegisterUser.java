package it.avanscoperta.masterplan.configuration.domain;

import org.axonframework.modelling.command.TargetAggregateIdentifier;

public record RegisterUser(
        @TargetAggregateIdentifier
        UserId userId,
        String username,
        PlanningHorizon planningHorizon
) {
    public RegisterUser(UserId userId, String username) {
        this(userId, username, PlanningHorizon.DEFAULT);
    }
}
