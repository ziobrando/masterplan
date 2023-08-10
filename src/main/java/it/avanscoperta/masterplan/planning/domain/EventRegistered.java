package it.avanscoperta.masterplan.planning.domain;

import it.avanscoperta.masterplan.common.domain.FixedTimeInterval;

public record EventRegistered(
        PersonalAvailabilityId personalAvailabilityId,
        String eventLabel,
        EventParty eventParty,
        FixedTimeInterval duration) {
}
