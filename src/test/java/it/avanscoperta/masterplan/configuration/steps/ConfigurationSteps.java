package it.avanscoperta.masterplan.configuration.steps;

import io.cucumber.java.en.*;
import it.avanscoperta.masterplan.common.steps.ScenarioContext;
import it.avanscoperta.masterplan.configuration.domain.RegisterUser;
import it.avanscoperta.masterplan.configuration.domain.UserId;
import it.avanscoperta.masterplan.configuration.query.BusyPersonRepository;
import it.avanscoperta.masterplan.configuration.query.BusyPersonView;
import it.avanscoperta.masterplan.planning.query.PersonalAvailabilityRepository;
import it.avanscoperta.masterplan.planning.query.PersonalAvailabilityView;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;

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
        BusyPersonView busyPersonView = busyPersonRepository.findById(userId).get();
        assertEquals(username, busyPersonView.username());
    }

    @Then("{string} should have an availability calendar")
    public void should_have_an_availability_calendar(String username) {
        UserId userId = context.retrieveUserId(username);
        PersonalAvailabilityView personalAvailabilityView = personalAvailabilityRepository
                .findOne(Example.of(new PersonalAvailabilityView().withUserId(userId))).get();
        assertNotNull(personalAvailabilityView);
    }





}
