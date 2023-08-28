package it.avanscoperta.masterplan.planning.query;

import it.avanscoperta.masterplan.common.domain.FixedTimeInterval;
import it.avanscoperta.masterplan.common.domain.Priority;
import it.avanscoperta.masterplan.common.domain.Slot;
import it.avanscoperta.masterplan.planning.domain.PlannedActivity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

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


    public boolean hasRoomFor(PlannedActivity plannedActivity, RequestInterval requestInterval) {

        logger.warn("Running a mocked up implementation");
        this.availableSlots(plannedActivity.priority()).stream();
        // FIXME: this calls for a Slot-based implementation.
        return plannedActivity.duration().toMinutes() < 240;
    }

    private List<Slot> availableSlots(Priority priority) {
        List<Slot> slots = new ArrayList<>();
        if (eventsForDay.events.isEmpty()) {
            slots.add(new Slot(
                    new FixedTimeInterval(
                            this.day.atStartOfDay(), this.day.plusDays(1).atStartOfDay()
                    )
            ));
        } else {
            throw new RuntimeException("You're too impatient!");
        }

        return slots;

    }

    public void reserveEvent(PlannedEvent plannedEvent) {
        eventsForDay.addEvent(plannedEvent);
        logger.debug("Added event " + plannedEvent + " to " + day );
    }
}
