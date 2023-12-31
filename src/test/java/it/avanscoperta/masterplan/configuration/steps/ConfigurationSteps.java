package it.avanscoperta.masterplan.configuration.steps;

import io.cucumber.java.en.*;
import it.avanscoperta.masterplan.common.domain.Priority;
import it.avanscoperta.masterplan.common.steps.ScenarioContext;
import it.avanscoperta.masterplan.configuration.domain.RegisterUser;
import it.avanscoperta.masterplan.configuration.domain.UserId;
import it.avanscoperta.masterplan.configuration.query.BusyPersonRepository;
import it.avanscoperta.masterplan.configuration.query.BusyPersonView;
import it.avanscoperta.masterplan.design.domain.AvailabilityConstraint;
import it.avanscoperta.masterplan.design.domain.DefineConstraint;
import it.avanscoperta.masterplan.planning.query.PersonalAvailabilityRepository;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.beans.factory.annotation.Autowired;

import static org.awaitility.Awaitility.await;
import static org.junit.jupiter.api.Assertions.*;


public class ConfigurationSteps {

    @Autowired
    CommandGateway commandGateway;

    @Autowired
    BusyPersonRepository busyPersonRepository;

    @Autowired
    PersonalAvailabilityRepository personalAvailabilityRepository;

    @Autowired
    ScenarioContext context;

    @When("user {string} signs up")
    public void user_signs_up(String username) {
        UserId userId = UserId.generate();
        context.rememberUser(username, userId);
        RegisterUser registerUser = new RegisterUser(userId,username);

        commandGateway.send(registerUser);
    }

    @Then("{string} should be a busy person")
    public void should_be_a_busy_person(String username) {

        UserId userId = context.retrieveUserId(username);

        await()
                .until(
                        () ->
                                busyPersonRepository.findById(userId).isPresent()
                );

        BusyPersonView busyPersonView = busyPersonRepository.findById(userId).get();
        assertEquals(username, busyPersonView.getUsername());
    }

    @Then("{string} should have an availability calendar")
    public void should_have_an_availability_calendar(String username) {
        UserId userId = context.retrieveUserId(username);

        await()
                .until(
                        () -> personalAvailabilityRepository.findByUserId(userId).isPresent()
                        /*
                () -> personalAvailabilityRepository.findOne(
                        Example.of(new PersonalAvailabilityView().withUserId(userId)))
                        .isPresent()

                         */
        );
    }

    @And("no calendar has been associated with {string}")
    public void noCalendarHasBeenAssociatedWith(String username) {
        UserId userId = context.retrieveUserId(username);
        await().until(
                () -> busyPersonRepository.findById(userId).isPresent()
        );

        BusyPersonView busyPersonView = busyPersonRepository.findById(userId).get();
        assertEquals(0, busyPersonView.externalCalendars().size());
    }

    @Given("{string} defined a {string} constraint from {string} to {string}")
    public void defined_a_constraint_from_to(String username, String constraintLabel, String firstDay, String lastDay) {
        // Write code here that turns the phrase above into concrete actions
        UserId userId = context.retrieveUserId(username);
        Priority priority = Priority.STANDARD;

        AvailabilityConstraint constraint = new AvailabilityConstraint();

        DefineConstraint defineConstraint = new DefineConstraint(userId, constraintLabel, priority, constraint);

        throw new io.cucumber.java.PendingException();
    }

}
