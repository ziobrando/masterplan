package it.avanscoperta.masterplan.planning.query;

import it.avanscoperta.masterplan.configuration.domain.PlanningHorizon;
import it.avanscoperta.masterplan.configuration.domain.UserId;
import it.avanscoperta.masterplan.planning.domain.PlannedActivity;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
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
    public boolean isAvailableFor(PlannedActivity plannedActivity) {
        AtomicBoolean result = new AtomicBoolean(false);
        availableDays.forEach(
                (day) -> {
                    if (day.hasRoomFor(plannedActivity)) {
                        result.set(true);
                        logger.debug("Found availability on day: " + day);
                    }
                }
        );
        return result.get();
    }
}
