package it.avanscoperta.masterplan.planning.query;

import it.avanscoperta.masterplan.configuration.domain.UserId;
import it.avanscoperta.masterplan.planning.domain.PlannedActivity;

public interface PersonalAvailabilityView {
    boolean isAvailableFor(PlannedActivity plannedActivity);
    UserId getUserId();

    void reserveEvent(PlannedEvent plannedEvent);

    boolean includesEvent(PlannedEvent plannedEvent);
}
