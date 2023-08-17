package it.avanscoperta.masterplan.planning.domain;

import it.avanscoperta.masterplan.common.domain.Email;
import it.avanscoperta.masterplan.common.domain.FixedTimeInterval;
import it.avanscoperta.masterplan.common.domain.Priority;
import it.avanscoperta.masterplan.configuration.domain.PlanningHorizon;
import it.avanscoperta.masterplan.configuration.domain.UserId;
import org.axonframework.test.aggregate.AggregateTestFixture;
import org.axonframework.test.aggregate.FixtureConfiguration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@DisplayName("[Masterplan | Planning]: PersonalAvailability Aggregate")
public class PersonalAvailabilityTest {

    FixtureConfiguration<PersonalAvailability> fixture = new AggregateTestFixture<>(PersonalAvailability.class);
    private PersonalAvailabilityId personalAvailabilityId;
    private UserId userId;
    private GeneratePersonalAvailability generatePersonalAvailability;
    private PersonalAvailabilityGenerated personalAvailabilityGenerated;
    private Email userEmail;
    private String eventLabel;
    private EventParty eventParty;


    @BeforeEach
    void setUp() {
        personalAvailabilityId = PersonalAvailabilityId.generate();
        userId = UserId.generate();
        PlanningHorizon planningHorizon = PlanningHorizon.DEFAULT;
        generatePersonalAvailability = new GeneratePersonalAvailability(
                personalAvailabilityId, userId, planningHorizon
        );
        personalAvailabilityGenerated = new PersonalAvailabilityGenerated(
                personalAvailabilityId, userId, planningHorizon
        );
        userEmail = new Email("user@smartPlanner.com");
        eventLabel = "Test Event";
        eventParty = EventParty.of(userEmail);
    }

    @Test
    @DisplayName("Can create a PersonalAvailability")
    void can_create_a_personal_availability() {

        fixture.givenNoPriorActivity()
                .when(generatePersonalAvailability)
                .expectEvents(personalAvailabilityGenerated);
    }

    @Test
    @DisplayName("Can register an event")
    void can_register_an_event() {

        LocalDateTime fromTime = LocalDateTime.now().plusDays(1).withHour(9).withMinute(0).truncatedTo(ChronoUnit.MINUTES);
        LocalDateTime toTime = fromTime.plusMinutes(45);
        FixedTimeInterval duration = new FixedTimeInterval(fromTime, toTime);
        Priority priority = new Priority("Standard meeting", 50);

        RegisterEvent registerEvent = new RegisterEvent(personalAvailabilityId, eventLabel, priority, eventParty, duration);
        EventRegistered eventRegistered = new EventRegistered(personalAvailabilityId, eventLabel, priority, eventParty, duration);

        fixture.given(personalAvailabilityGenerated)
                .when(registerEvent)
                .expectEvents(eventRegistered);
    }

}
