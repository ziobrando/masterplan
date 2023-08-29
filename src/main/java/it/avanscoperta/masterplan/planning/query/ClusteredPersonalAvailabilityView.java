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
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * A somewhat sophisticated read model optimized for answering planning questions.
 * </br>
 * This implementation follows the assumption that having a Day-based data structure will help
 * answering those queries. However, this is not the whole story, and a continuous-time implementation
 * might turn out better.
 */
@Document(collection = "Clustered Personal Availability")
public class ClusteredPersonalAvailabilityView implements PersonalAvailabilityView {

    private static final Logger logger = LoggerFactory.getLogger(ClusteredPersonalAvailabilityView.class);

    @Id
    private String personalAvailabilityId;
    private UserId userId;
    private PlanningHorizon planningHorizon;
    List<AvailableDay> availableDays = new ArrayList<>();

    @Override
    public UserId getUserId() {
        return userId;
    }

    @Override
    public void reserveEvent(PlannedEvent plannedEvent) {
        availableDays.stream()
                .filter((day)-> plannedEvent.fixedTimeInterval().spansOver(day.day))
                .forEach((day) -> day.reserveEvent(plannedEvent));
    }

    @Override
    public boolean includesEvent(PlannedEvent plannedEvent) {
        return availableDays.stream()
                .filter((day)-> day.day.isEqual(plannedEvent.fixedTimeInterval().fromTime().toLocalDate()))
                .findFirst()
                .get()
                .eventsForDay.events.contains(plannedEvent);
    }

    @Override
    public Optional<LocalDate> firstAvailableDate(PotentialActivity potentialActivity, RequestInterval requestInterval) {
        return availableDays.stream()
                .filter((day) ->
                            day.hasRoomFor(potentialActivity, requestInterval)
                        )
                .findFirst()
                .map(availableDay -> availableDay.day);
    }

    @Override
    public Optional<Slot> firstAvailableSlot(PotentialActivity potentialActivity, RequestInterval requestInterval) {
        throw new RuntimeException("Not Yet");
    }

    @Override
    public void registerConstraint(AvailabilityConstraint availabilityConstraint) {

    }

    public ClusteredPersonalAvailabilityView(
            @NotNull
            String personalAvailabilityId,
            UserId userId,
            PlanningHorizon planningHorizon) {
        this.personalAvailabilityId = personalAvailabilityId;
        this.userId = userId;
        this.planningHorizon = planningHorizon;
        initDays(planningHorizon.duration().days());
    }

    private void initDays(int days) {
        for (int day=0; day <= days;  day++)
        {
            availableDays.add(new AvailableDay(LocalDate.now().plusDays(day)));
        }
        logger.debug("Added " + days + " empty days to personal availability");
    }

    /**
     * TODO: this shouldn't be here.
     * @param userId the <code>UserId</code> of the BusyPerson
     * @return an instance to <code>this</code>
     */
    public PersonalAvailabilityView withUserId(UserId userId) {
        this.userId = userId;
        return this;
    }

    @Override
    public boolean isAvailableFor(PotentialActivity potentialActivity, RequestInterval requestInterval) {
        AtomicBoolean result = new AtomicBoolean(false);
        // TODO: need to filter for Request Interval
        availableDays.forEach(
                (day) -> {
                    if (day.hasRoomFor(potentialActivity, requestInterval)) {
                        result.set(true);
                        logger.debug("Found availability on day: " + day);
                    }
                }
        );
        return result.get();
    }

    @Override
    public boolean isAvailableFor(PotentialActivity potentialActivity) {
        return isAvailableFor(potentialActivity, defaultRequestInterval());
    }

    private RequestInterval defaultRequestInterval() {
        return new RequestInterval(new FixedTimeInterval(
                LocalDateTime.now(), LocalDateTime.now().plusDays(planningHorizon.duration().days())
        ));
    }
}
