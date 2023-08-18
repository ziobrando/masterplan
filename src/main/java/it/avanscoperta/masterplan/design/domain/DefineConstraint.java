package it.avanscoperta.masterplan.design.domain;

import it.avanscoperta.masterplan.common.domain.Priority;
import it.avanscoperta.masterplan.configuration.domain.UserId;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

public record DefineConstraint(
        @TargetAggregateIdentifier
        UserId userId,          // TODO make it an external
        String label,
        Priority priority,      // TODO wrap into a type
        AvailabilityConstraint constraint) {
}