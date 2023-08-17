package it.avanscoperta.masterplan.planning.domain;

import it.avanscoperta.masterplan.common.domain.FixedTimeInterval;
import it.avanscoperta.masterplan.common.domain.Priority;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

public record RegisterEvent(
        @TargetAggregateIdentifier
        PersonalAvailabilityId personalAvailabilityId,
        String eventLabel,
        Priority priority,
        EventParty eventParty,
        FixedTimeInterval duration) {
}

