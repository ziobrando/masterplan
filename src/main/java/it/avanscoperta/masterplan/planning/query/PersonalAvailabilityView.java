package it.avanscoperta.masterplan.planning.query;

import it.avanscoperta.masterplan.common.domain.Slot;
import it.avanscoperta.masterplan.configuration.domain.UserId;
import it.avanscoperta.masterplan.design.domain.AvailabilityConstraint;
import it.avanscoperta.masterplan.planning.domain.PotentialActivity;

import java.time.LocalDate;
import java.util.Optional;

public interface PersonalAvailabilityView {
    boolean isAvailableFor(PotentialActivity potentialActivity, RequestInterval requestInterval);
    boolean isAvailableFor(PotentialActivity potentialActivity);
    UserId getUserId();

    void reserveEvent(PlannedEvent plannedEvent);

    boolean includesEvent(PlannedEvent plannedEvent);

    Optional<LocalDate> firstAvailableDate(PotentialActivity potentialActivity, RequestInterval requestInterval);
    Optional<Slot> firstAvailableSlot(PotentialActivity potentialActivity, RequestInterval requestInterval);

    void registerConstraint(AvailabilityConstraint availabilityConstraint);
}
