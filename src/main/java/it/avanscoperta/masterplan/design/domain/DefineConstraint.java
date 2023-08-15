package it.avanscoperta.masterplan.design.domain;

import it.avanscoperta.masterplan.configuration.domain.UserId;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

public record DefineConstraint(
        @TargetAggregateIdentifier
        UserId userId,  // TODO make it an external
        String save_the_weekends,
        int priority,   // TODO wrap into a type
        AvailabilityConstraint constraint) {
}
