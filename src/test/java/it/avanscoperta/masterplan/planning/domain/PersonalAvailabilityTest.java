package it.avanscoperta.masterplan.planning.domain;

import it.avanscoperta.masterplan.common.domain.Email;
import it.avanscoperta.masterplan.common.domain.FixedTimeInterval;
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

    FixtureConfiguration<PersonalAvailability> fixture = new AggregateTestFixture(PersonalAvailability.class);
    private PersonalAvailabilityId personalAvailabilityId;
    private UserId userId;
    private GeneratePersonalAvailability generatePersonalAvailability;
    private PersonalAvailabilityGenerated personalAvailabilityGenerated;


    @BeforeEach
    void setUp() {
        personalAvailabilityId = PersonalAvailabilityId.generate();
        userId = UserId.generate();
        generatePersonalAvailability = new GeneratePersonalAvailability(
                personalAvailabilityId, userId
        );
        personalAvailabilityGenerated = new PersonalAvailabilityGenerated(
                personalAvailabilityId, userId
        );
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
        Email userEmail = new Email("user@smartPlanner.com");
        String eventLabel = "Test Event";
        EventParty eventParty = EventParty.of(userEmail);
        LocalDateTime fromTime = LocalDateTime.now().plusDays(1).withHour(9).withMinute(0).truncatedTo(ChronoUnit.MINUTES);
        LocalDateTime toTime = fromTime.plusMinutes(45);
        FixedTimeInterval duration = new FixedTimeInterval(fromTime, toTime);

        RegisterEvent registerEvent = new RegisterEvent(personalAvailabilityId, eventLabel, eventParty, duration);
        EventRegistered eventRegistered = new EventRegistered(personalAvailabilityId, eventLabel, eventParty, duration);

        fixture.given(personalAvailabilityGenerated)
                .when(registerEvent)
                .expectEvents(eventRegistered);
    }

}
