package it.avanscoperta.masterplan.planning.query;

import it.avanscoperta.masterplan.common.domain.FixedTimeInterval;
import it.avanscoperta.masterplan.common.domain.Priority;
import it.avanscoperta.masterplan.configuration.domain.PlanningHorizon;
import it.avanscoperta.masterplan.configuration.domain.UserId;
import it.avanscoperta.masterplan.planning.domain.PersonalAvailabilityId;
import it.avanscoperta.masterplan.planning.domain.PlannedActivity;
import it.avanscoperta.masterplan.planning.domain.PlannedEventId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("[MasterPlan | Planning]: PersonalAvailability View")
public class PersonalAvailabilityViewTest {

    private static PersonalAvailabilityId personalAvailabilityId;
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
        PlannedActivity plannedActivity = new PlannedActivity(Duration.ofHours(2), Priority.STANDARD);
        assertTrue(view.isAvailableFor(plannedActivity));
    }

    private static Stream<Arguments> alternativeImplementations() {

        return Stream.of(
                Arguments.of(new ClusteredPersonalAvailabilityView(
                                PersonalAvailabilityId.generate().id(),
                                UserId.generate(),
                                PlanningHorizon.DEFAULT
                        ),
                        Arguments.of(new ContinuousPersonalAvailabilityView(
                                        PersonalAvailabilityId.generate().id(),
                                        UserId.generate(),
                                        PlanningHorizon.DEFAULT
                                )
                        )
                )
        );
    }

    @ParameterizedTest
    @MethodSource("alternativeImplementations")
    public void can_reserve_an_event(PersonalAvailabilityView view) {
        FixedTimeInterval tomorrowMorning = new FixedTimeInterval(
                LocalDateTime.now().plusDays(1).withHour(9).truncatedTo(ChronoUnit.MINUTES),
                LocalDateTime.now().plusDays(1).withHour(11).truncatedTo(ChronoUnit.MINUTES)
        );
        PlannedEvent plannedEvent = new PlannedEvent(PlannedEventId.generate(), "Test Event", tomorrowMorning);

        view.reserveEvent(plannedEvent);
        PlannedActivity overlappingActivity = new PlannedActivity(Duration.ofHours(2), Priority.STANDARD);

        // FIXME: Here the types will explode.
        assertFalse(view.isAvailableFor(overlappingActivity));

    }

}
