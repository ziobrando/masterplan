package it.avanscoperta.masterplan.planning.domain;

import it.avanscoperta.masterplan.common.domain.FixedTimeInterval;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

public record RegisterEvent(
        @TargetAggregateIdentifier
        PersonalAvailabilityId personalAvailabilityId,
        String eventLabel,
        EventParty eventParty,
        FixedTimeInterval duration) {
}

