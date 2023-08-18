package it.avanscoperta.masterplan.planning.query;

import it.avanscoperta.masterplan.planning.domain.PlannedActivity;

import java.time.LocalDate;

/**
 * Massively exploratory coding.
 * An available day is a container of events, or a segmentation of the exploration space.
 * I have no idea whether this is a good idea or not.
 * The other alternative is to play on a continuum.
 */
public class AvailableDay {
    LocalDate day;
    EventsForDay eventsForDay;

    public AvailableDay(LocalDate day) {
        this.day = day;
    }


    public boolean hasRoomFor(PlannedActivity plannedActivity) {
        // FIXME: this calls for a Slot-based implementation.
        return plannedActivity.duration().toMinutes() < 240;
    }
}
