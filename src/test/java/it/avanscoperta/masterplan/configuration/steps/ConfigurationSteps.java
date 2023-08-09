package it.avanscoperta.masterplan.configuration.steps;


import io.cucumber.java.PendingException;
import io.cucumber.java.en.*;
import it.avanscoperta.masterplan.configuration.domain.PersonalCalendarRepository;
import org.springframework.beans.factory.annotation.Autowired;

public class ConfigurationSteps {

    @Autowired
    PersonalCalendarRepository personalCalendarRepository;

    @Then("{string} should have a personal calendar")
    public void shouldHaveAPersonalCalendar() {



        throw new PendingException("Should check a read model here.");


    }
}
