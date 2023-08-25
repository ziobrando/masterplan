package it.avanscoperta.masterplan.planning.query;

import it.avanscoperta.masterplan.planning.domain.PlannedActivity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;

/**
 * Massively exploratory coding.
 * An available day is a container of events, or a segmentation of the exploration space.
 * I have no idea whether this is a good idea or not.
 * The alternative is to play on a continuum.
 */
public class AvailableDay {
    static Logger logger = LoggerFactory.getLogger(AvailableDay.class);
    LocalDate day;
    EventsForDay eventsForDay = new EventsForDay();

    public AvailableDay(LocalDate day) {
        this.day = day;
    }


    public boolean hasRoomFor(PlannedActivity plannedActivity) {
        // FIXME: this calls for a Slot-based implementation.
        return plannedActivity.duration().toMinutes() < 240;
    }

    public void reserveEvent(PlannedEvent plannedEvent) {
        eventsForDay.addEvent(plannedEvent);
        logger.debug("Added event " + plannedEvent + " to " + day );
    }
}
