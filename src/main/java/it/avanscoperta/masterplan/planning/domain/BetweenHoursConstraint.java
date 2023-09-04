package it.avanscoperta.masterplan.planning.domain;

import it.avanscoperta.masterplan.common.domain.Slot;

import java.time.LocalDateTime;
import java.time.LocalTime;

public record BetweenHoursConstraint(
        PotentialActivity potentialActivity,
        LocalTime dailyStart,
        LocalTime dailyEnd)
        implements PlanningConstraint{
    @Override
    public boolean isSatisfiedBy(Slot potentialSlot) {
        // FIXME: the slot overlaps with the constraint, and the overlapping area is longer than the activity.
        return this.overlaps(potentialSlot) &&
                potentialActivity.shorterThanSlot(this.overlappingSlot(potentialSlot));
    }

    private Slot overlappingSlot(Slot potentialSlot) {
        LocalDateTime slotStart = potentialSlot.timeInterval().fromTime();
        LocalDateTime slotEnd = potentialSlot.timeInterval().toTime();
        LocalDateTime locatedStart = dailyStart.atDate(
                potentialSlot.timeInterval().fromTime().toLocalDate());
        LocalDateTime locatedEnd = dailyEnd.atDate(
                potentialSlot.timeInterval().toTime().toLocalDate());

        return new Slot(
                slotStart.isAfter(locatedStart) ? slotStart : locatedStart,
                slotEnd.isBefore(locatedEnd) ? slotEnd : locatedEnd
        );
    }

    private boolean overlaps(Slot potentialSlot) {
        return potentialSlot.timeInterval().fromTime().toLocalTime().isBefore(dailyEnd) &&
                potentialSlot.timeInterval().toTime().toLocalTime().isAfter(dailyStart);
    }

    @Override
    public String toString() {
        return "BetweenHoursConstraint{" +
                ", dailyStart=" + dailyStart +
                ", dailyEnd=" + dailyEnd +
                '}';
    }
}
