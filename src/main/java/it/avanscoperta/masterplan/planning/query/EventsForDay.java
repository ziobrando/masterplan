package it.avanscoperta.masterplan.planning.query;

import java.util.ArrayList;
import java.util.List;

/**
 * Another stretched idea, with some inspiration from Sandi Metz: a typed container for
 * <code>PlannedEvents</code> with time-based ordering in place.
 */
public class EventsForDay {
    List<PlannedEvent> events = new ArrayList<>();
}
