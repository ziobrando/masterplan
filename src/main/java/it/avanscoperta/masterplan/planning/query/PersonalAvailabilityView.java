package it.avanscoperta.masterplan.planning.query;

import it.avanscoperta.masterplan.configuration.domain.PlanningHorizon;
import it.avanscoperta.masterplan.configuration.domain.UserId;
import it.avanscoperta.masterplan.planning.domain.PlannedActivity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

/**
 * A somewhat sophisticated read model optimized for answering planning questions
 */
@Document(collection = "Personal Availability")
public class PersonalAvailabilityView {

    private static final Logger logger = LoggerFactory.getLogger(PersonalAvailabilityView.class);

    @Id
    private String personalAvailabilityId;
    private UserId userId;
    private PlanningHorizon planningHorizon;
    List<AvailableDay> availableDays = new ArrayList<>();




    public PersonalAvailabilityView(
            String personalAvailabilityId,
            UserId userId,
            PlanningHorizon planningHorizon) {
        this.personalAvailabilityId = personalAvailabilityId;
        this.userId = userId;
        this.planningHorizon = planningHorizon;
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

    public boolean isAvailableFor(PlannedActivity plannedActivity) {
        logger.warn("Method isAvailableFor() hasn't been implemented yet.");
        return false;
    }
}
