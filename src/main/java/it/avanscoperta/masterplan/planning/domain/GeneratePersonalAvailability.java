package it.avanscoperta.masterplan.planning.domain;

import it.avanscoperta.masterplan.configuration.domain.UserId;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

public record GeneratePersonalAvailability(
        @TargetAggregateIdentifier
        PersonalAvailabilityId personalAvailabilityId,
        UserId userId) {
}
