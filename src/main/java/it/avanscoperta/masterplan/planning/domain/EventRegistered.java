package it.avanscoperta.masterplan.planning.domain;

import it.avanscoperta.masterplan.common.domain.FixedTimeInterval;
import it.avanscoperta.masterplan.common.domain.Priority;

public record EventRegistered(
        PersonalAvailabilityId personalAvailabilityId,
        String eventLabel,
        Priority priority,
        EventParty eventParty,
        FixedTimeInterval duration) {
}
