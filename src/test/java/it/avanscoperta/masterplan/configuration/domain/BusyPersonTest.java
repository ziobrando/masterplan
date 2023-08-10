package it.avanscoperta.masterplan.configuration.domain;

import org.axonframework.test.aggregate.AggregateTestFixture;
import org.axonframework.test.aggregate.FixtureConfiguration;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("[Masterplan | Configuration]: BusyPerson Aggregate")
public class BusyPersonTest {

    FixtureConfiguration<BusyPerson> fixture = new AggregateTestFixture<>(BusyPerson.class);


    @Test
    @DisplayName("Can register a User")
    void can_register_a_user() {

        UserId userId = UserId.generate();
        String username = "Test User";
        RegisterUser registerUser = new RegisterUser(userId, username);
        UserRegistered userRegistered = new UserRegistered(userId, username);

        fixture.givenNoPriorActivity()
                .when(registerUser)
                .expectEvents(userRegistered);
    }
}
