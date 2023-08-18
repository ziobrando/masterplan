package it.avanscoperta.masterplan.planning.query;

import it.avanscoperta.masterplan.configuration.domain.PlanningHorizon;
import it.avanscoperta.masterplan.configuration.domain.UserId;
import it.avanscoperta.masterplan.planning.domain.PlannedActivity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * A somewhat sophisticated read model optimized for answering planning questions.
 * </br>
 * This implementation follows the assumption that having a Day-based data structure will help
 * answering those queries. However, this is not the whole story, and a continuous-time implementation
 * might turn out better.
 */
@Document(collection = "Personal Availability")
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

    public ClusteredPersonalAvailabilityView(
            String personalAvailabilityId,
            UserId userId,
            PlanningHorizon planningHorizon) {
        this.personalAvailabilityId = personalAvailabilityId;
        this.userId = userId;
        this.planningHorizon = planningHorizon;
        initDays((int) planningHorizon.duration().days());
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
        logger.warn("Method isAvailableFor() hasn't been implemented yet.");
        return false;
    }
}
