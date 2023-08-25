package it.avanscoperta.masterplan.planning.query;

import it.avanscoperta.masterplan.common.domain.Slot;
import it.avanscoperta.masterplan.configuration.domain.PlanningHorizon;
import it.avanscoperta.masterplan.configuration.domain.UserId;
import it.avanscoperta.masterplan.planning.domain.PlannedActivity;
import it.avanscoperta.masterplan.planning.domain.PlannedEventId;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * A somewhat sophisticated read model optimized for answering planning questions.
 * </br>
 * This implementation follows the assumption that having a Day-based data structure will help
 * answering those queries. However, this is not the whole story, and a continuous-time implementation
 * might turn out better.
 */
@Document(collection = "Continuous Personal Availability")
public class ContinuousPersonalAvailabilityView implements PersonalAvailabilityView {

    private static final Logger logger = LoggerFactory.getLogger(ContinuousPersonalAvailabilityView.class);

    @Id
    private String personalAvailabilityId;
    private final UserId userId;
    private PlanningHorizon planningHorizon;
    PlannedEvents plannedEvents = new PlannedEvents();

    @Override
    public UserId getUserId() {
        return userId;
    }

    @Override
    public void reserveEvent(PlannedEvent plannedEvent) {
        plannedEvents.add(plannedEvent);
    }

    @Override
    public boolean includesEvent(PlannedEvent plannedEvent) {
            return plannedEvents.includes(plannedEvent);
    }

    public ContinuousPersonalAvailabilityView(
            @NotNull
            String personalAvailabilityId,
            UserId userId,
            PlanningHorizon planningHorizon) {
        this.personalAvailabilityId = personalAvailabilityId;
        this.userId = userId;
        this.planningHorizon = planningHorizon;
    }

    @Override
    public boolean isAvailableFor(PlannedActivity plannedActivity) {
        AtomicBoolean result = new AtomicBoolean(false);

        LocalDateTime searchIntervalStart = LocalDateTime.now(); // TODO: add more flexibility;
        LocalDateTime searchIntervalEnd = LocalDateTime.now().plusDays(planningHorizon.duration().days());

        availableSlots(searchIntervalStart, searchIntervalEnd).forEach(
                (slot) -> { if (slot.hasRoomFor(plannedActivity)) {
                    result.set(true);
                    logger.debug("Found availability on slot: " + slot);
                }}
        );

        return result.get();
    }

    private Iterable<Slot> availableSlots(LocalDateTime from, LocalDateTime to) {
        return plannedEvents.toAvailableSlots(from, to);
    }


    private static class PlannedEventComparator implements Comparator<PlannedEvent> {
        public int compare(PlannedEvent one, PlannedEvent another) {
            return (one.fixedTimeInterval().fromTime().compareTo(another.fixedTimeInterval().fromTime()));
        }
    }


    private class PlannedEvents {
        SortedSet<PlannedEvent> plannedEvents = new TreeSet<>(new PlannedEventComparator());

        public Iterable<Slot> toAvailableSlots(LocalDateTime from, LocalDateTime to) {
            List<Slot> slots = new ArrayList<>();
            slots.add(new Slot(from, to));
            return slots;
        }

        public void add(PlannedEvent plannedEvent) {
            plannedEvents.add(plannedEvent);

        }

        public boolean includes(PlannedEvent plannedEvent) {
            return plannedEvents.contains(plannedEvent);
        }
    }
}
