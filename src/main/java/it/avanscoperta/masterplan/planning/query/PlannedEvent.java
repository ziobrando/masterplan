package it.avanscoperta.masterplan.planning.query;

import it.avanscoperta.masterplan.common.domain.FixedTimeInterval;
import it.avanscoperta.masterplan.planning.domain.PlannedEventId;

public record PlannedEvent (
        PlannedEventId plannedEventId,
        String label,
        FixedTimeInterval fixedTimeInterval) {
}
