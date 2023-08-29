package it.avanscoperta.masterplan.planning.query;

import it.avanscoperta.masterplan.common.domain.FixedTimeInterval;
import it.avanscoperta.masterplan.common.domain.Slot;
import it.avanscoperta.masterplan.configuration.domain.PlanningHorizon;
import it.avanscoperta.masterplan.configuration.domain.UserId;
import it.avanscoperta.masterplan.design.domain.AvailabilityConstraint;
import it.avanscoperta.masterplan.planning.domain.PotentialActivity;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
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


    @Override
    public void registerConstraint(AvailabilityConstraint availabilityConstraint) {

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
    public boolean isAvailableFor(PotentialActivity potentialActivity, RequestInterval requestInterval) {
        AtomicBoolean result = new AtomicBoolean(false);

        availableSlots(requestInterval.fixedTimeInterval()).forEach(
                (slot) -> { if (slot.hasRoomFor(potentialActivity)) {
                    result.set(true);
                    logger.debug("Found availability on slot: " + slot);
                }}
        );

        return result.get();
    }

    @Override
    public boolean isAvailableFor(PotentialActivity potentialActivity) {
        return isAvailableFor(potentialActivity, defaultRequestInterval());
    }

    @Override
    public Optional<Slot> firstAvailableSlot(PotentialActivity potentialActivity, RequestInterval requestInterval) {
        return availableSlots(requestInterval.fixedTimeInterval())
                .stream().sequential()
                .filter((slot -> slot.hasRoomFor(potentialActivity)))
                .findFirst();
    }


    @Override
    public Optional<LocalDate> firstAvailableDate(PotentialActivity potentialActivity, RequestInterval requestInterval) {
        return firstAvailableSlot(potentialActivity, requestInterval)
                .map((slot -> slot.timeInterval().fromTime().toLocalDate()));
    }



    private RequestInterval defaultRequestInterval() {
        return new RequestInterval(new FixedTimeInterval(
                LocalDateTime.now(), LocalDateTime.now().plusDays(planningHorizon.duration().days())
        ));
    }

    private List<Slot> availableSlots(FixedTimeInterval searchInterval) {
        return plannedEvents.toAvailableSlots(searchInterval);
    }


    private static class PlannedEventComparator implements Comparator<PlannedEvent> {
        public int compare(PlannedEvent one, PlannedEvent another) {
            return (one.fixedTimeInterval().fromTime().compareTo(another.fixedTimeInterval().fromTime()));
        }
    }


    private class PlannedEvents {
        SortedSet<PlannedEvent> plannedEvents = new TreeSet<>(new PlannedEventComparator());

        public List<Slot> toAvailableSlots(FixedTimeInterval timeInterval) {
            List<Slot> slots = new ArrayList<>();
            slots.add(new Slot(timeInterval));
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
