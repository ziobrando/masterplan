package it.avanscoperta.masterplan.configuration.domain;

import org.axonframework.test.aggregate.AggregateTestFixture;
import org.axonframework.test.aggregate.FixtureConfiguration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.Duration;

@DisplayName("[Masterplan | Configuration]: BusyPerson Aggregate")
public class BusyPersonTest {

    FixtureConfiguration<BusyPerson> fixture = new AggregateTestFixture<>(BusyPerson.class);
    private UserId userId;
    private String username;
    private RegisterUser registerUser;
    private UserRegistered userRegistered;
    private Duration plannningHorizonDuration;

    @BeforeEach
    void setUp() {
        userId = UserId.generate();
        username = "Test User";
        plannningHorizonDuration = Duration.ofDays(180);
        registerUser = new RegisterUser(userId, username);
        userRegistered = new UserRegistered(userId, username, PlanningHorizon.DEFAULT);

    }

    @Test
    @DisplayName("Can register a User")
    void can_register_a_user() {
        fixture.givenNoPriorActivity()
                .when(registerUser)
                .expectSuccessfulHandlerExecution()
                .expectEvents(userRegistered);
    }


    @Test
    @DisplayName("Can define the planning horizon")
    void can_define_planning_horizon() {

        AdjustPlanningHorizon adjustPlanningHorizon = new AdjustPlanningHorizon(userId, plannningHorizonDuration);
        PlanningHorizonAdjusted planningHorizonAdjusted = new PlanningHorizonAdjusted(userId, plannningHorizonDuration);

        fixture.given(userRegistered)
                .when(adjustPlanningHorizon)
                .expectEvents(planningHorizonAdjusted);
    }
}
