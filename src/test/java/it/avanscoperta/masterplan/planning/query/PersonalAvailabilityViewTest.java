package it.avanscoperta.masterplan.planning.query;

import it.avanscoperta.masterplan.configuration.domain.PlanningHorizon;
import it.avanscoperta.masterplan.configuration.domain.UserId;
import it.avanscoperta.masterplan.planning.domain.PersonalAvailabilityId;
import it.avanscoperta.masterplan.planning.domain.PlannedActivity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DisplayName("[MasterPlan | Planning]: PersonalAvailability View")
public class PersonalAvailabilityViewTest {

    private PersonalAvailabilityId personalAvailabilityId;
    private UserId userId;

    @BeforeEach
    void setUp() {
        personalAvailabilityId = PersonalAvailabilityId.generate();
        userId = UserId.generate();
    }

    /**
     * Current assumptions: I am writing tests for the expected Black Box behaviour.
     */

    @Test
    public void can_create_a_personal_availability() {
        PersonalAvailabilityView view = new ClusteredPersonalAvailabilityView(
                personalAvailabilityId.id(),
                userId,
                PlanningHorizon.DEFAULT
        );
        assertNotNull(view);
    }

    @Test
    public void a_fresh_one_should_be_always_available() {
        PersonalAvailabilityView view = new ClusteredPersonalAvailabilityView(
                personalAvailabilityId.id(),
                userId,
                PlanningHorizon.DEFAULT
        );
        PlannedActivity plannedActivity = new PlannedActivity(Duration.ofHours(2));
        assertTrue(view.isAvailableFor(plannedActivity));
    }


}
