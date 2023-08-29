package it.avanscoperta.masterplan.planning.query;

import it.avanscoperta.masterplan.common.domain.FixedTimeInterval;
import it.avanscoperta.masterplan.common.domain.Priority;
import it.avanscoperta.masterplan.common.domain.Slot;
import it.avanscoperta.masterplan.configuration.domain.PlanningHorizon;
import it.avanscoperta.masterplan.configuration.domain.UserId;
import it.avanscoperta.masterplan.design.domain.AvailabilityConstraint;
import it.avanscoperta.masterplan.planning.domain.PersonalAvailabilityId;
import it.avanscoperta.masterplan.planning.domain.PotentialActivity;
import it.avanscoperta.masterplan.planning.domain.PlannedEventId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("[MasterPlan | Planning]: PersonalAvailability View")
public class PersonalAvailabilityViewTest {

    private static PersonalAvailabilityId personalAvailabilityId;
    private UserId userId;
    private FixedTimeInterval tomorrowMorning;
    private PlannedEvent plannedEvent;
    private LocalDate dayAfterTomorrow;
    private FixedTimeInterval fullDayTomorrow;

    @BeforeEach
    void setUp() {
        personalAvailabilityId = PersonalAvailabilityId.generate();
        userId = UserId.generate();
        tomorrowMorning = new FixedTimeInterval(
                LocalDateTime.now().plusDays(1).withHour(9).truncatedTo(ChronoUnit.MINUTES),
                LocalDateTime.now().plusDays(1).withHour(11).truncatedTo(ChronoUnit.MINUTES)
        );
        dayAfterTomorrow = LocalDate.now().plusDays(2);
        fullDayTomorrow = new FixedTimeInterval(
                LocalDateTime.now().plusDays(1).withHour(0).truncatedTo(ChronoUnit.MINUTES),
                LocalDateTime.now().plusDays(2).withHour(0).truncatedTo(ChronoUnit.MINUTES)
        );
    }

    /**
     * Current assumptions: I am writing tests for the expected Black Box behaviour.
     */
    private static Stream<Arguments> alternativeImplementations() {

        return Stream.of(
                Arguments.of(new ClusteredPersonalAvailabilityView(
                                PersonalAvailabilityId.generate().id(),
                                UserId.generate(),
                                PlanningHorizon.DEFAULT
                        )
                ),
                Arguments.of(new ContinuousPersonalAvailabilityView(
                                PersonalAvailabilityId.generate().id(),
                                UserId.generate(),
                                PlanningHorizon.DEFAULT
                        )
                )

        );
    }


    @ParameterizedTest
    @MethodSource("alternativeImplementations")
    @DisplayName("Can create a PersonalAvailabilityView")
    public void can_create_a_personal_availability(PersonalAvailabilityView view) {
        assertNotNull(view);
    }

    @ParameterizedTest
    @MethodSource("alternativeImplementations")
    @DisplayName("A newly created one should have space available")
    public void a_fresh_one_should_be_always_available(PersonalAvailabilityView view) {
        PotentialActivity potentialActivity = new PotentialActivity(Duration.ofHours(2), Priority.STANDARD);
        assertTrue(view.isAvailableFor(potentialActivity));
    }

    @ParameterizedTest
    @MethodSource("alternativeImplementations")
    @DisplayName("Can reserve an event")
    public void can_reserve_an_event(PersonalAvailabilityView view) {
        FixedTimeInterval tomorrowMorning = new FixedTimeInterval(
                LocalDateTime.now().plusDays(1).withHour(9).truncatedTo(ChronoUnit.MINUTES),
                LocalDateTime.now().plusDays(1).withHour(11).truncatedTo(ChronoUnit.MINUTES)
        );
        PlannedEvent plannedEvent = new PlannedEvent(PlannedEventId.generate(), "Test Event", tomorrowMorning);

        view.reserveEvent(plannedEvent);
        PotentialActivity overlappingActivity = new PotentialActivity(Duration.ofHours(2), Priority.STANDARD);

        assertTrue(view.includesEvent(plannedEvent));
    }

    @ParameterizedTest
    @MethodSource("alternativeImplementations")
    @DisplayName("Can define constraints")
    public void can_define_constraints(PersonalAvailabilityView view) {
        plannedEvent = new PlannedEvent(PlannedEventId.generate(), "Test Event", tomorrowMorning);

        // FIXME: this is a joke right now.
        AvailabilityConstraint flexibleWorkingHours = new AvailabilityConstraint();

        view.registerConstraint(flexibleWorkingHours);
        PotentialActivity overlappingActivity = new PotentialActivity(Duration.ofHours(2), Priority.STANDARD);

        //assertTrue(view.hasConstraint(flexibleWorkingHours));
    }


    @ParameterizedTest
    @MethodSource("alternativeImplementations")
    @DisplayName("Full Day events will impact availability")
    public void full_day_events_will_impact_availability(PersonalAvailabilityView view) {
        PlannedEvent fullDayEvent = new PlannedEvent(PlannedEventId.generate(), "Test Event", fullDayTomorrow);
        assertEquals(Duration.ofDays(1), fullDayEvent.fixedTimeInterval().duration());
        view.reserveEvent(fullDayEvent);

        PotentialActivity overlappingActivity = new PotentialActivity(Duration.ofHours(24), Priority.STANDARD);
        RequestInterval requestInterval = new RequestInterval(
                new FixedTimeInterval(LocalDateTime.now(), LocalDateTime.now().plusMonths(2))
        );


        Slot expectedSlot = new Slot(
                LocalDateTime.now().plusDays(1).withHour(0).truncatedTo(ChronoUnit.HOURS),
                LocalDateTime.now().plusDays(2).withHour(0).truncatedTo(ChronoUnit.HOURS));

        assertEquals(
                expectedSlot,
                view.firstAvailableSlot(overlappingActivity, requestInterval).get()
        );

    }



    @ParameterizedTest
    @MethodSource("alternativeImplementations")
    @DisplayName("Existing events will impact availability")
    public void existing_events_will_impact_availability(PersonalAvailabilityView view) {
        PlannedEvent plannedEvent = new PlannedEvent(PlannedEventId.generate(), "Test Event", fullDayTomorrow);

        view.reserveEvent(plannedEvent);
        PotentialActivity overlappingActivity = new PotentialActivity(Duration.ofHours(2), Priority.STANDARD);
        RequestInterval requestInterval = new RequestInterval(
                new FixedTimeInterval(LocalDateTime.now(), LocalDateTime.now().plusMonths(2))
        );

        assertTrue(view.isAvailableFor(overlappingActivity));

        assertEquals(
                dayAfterTomorrow,
                view.firstAvailableDate(overlappingActivity, requestInterval).get()
        );
    }



}
