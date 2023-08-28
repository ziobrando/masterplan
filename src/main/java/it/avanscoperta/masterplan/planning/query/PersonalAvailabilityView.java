package it.avanscoperta.masterplan.planning.query;

import it.avanscoperta.masterplan.common.domain.Slot;
import it.avanscoperta.masterplan.configuration.domain.UserId;
import it.avanscoperta.masterplan.design.domain.AvailabilityConstraint;
import it.avanscoperta.masterplan.planning.domain.PlannedActivity;

import java.time.LocalDate;
import java.util.Optional;

public interface PersonalAvailabilityView {
    boolean isAvailableFor(PlannedActivity plannedActivity, RequestInterval requestInterval);
    boolean isAvailableFor(PlannedActivity plannedActivity);
    UserId getUserId();

    void reserveEvent(PlannedEvent plannedEvent);

    boolean includesEvent(PlannedEvent plannedEvent);

    Optional<LocalDate> firstAvailableDate(PlannedActivity plannedActivity, RequestInterval requestInterval);
    Optional<Slot> firstAvailableSlot(PlannedActivity plannedActivity, RequestInterval requestInterval);

    void registerConstraint(AvailabilityConstraint availabilityConstraint);
}
