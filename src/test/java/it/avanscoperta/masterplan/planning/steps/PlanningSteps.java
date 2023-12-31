package it.avanscoperta.masterplan.planning.steps;

import io.cucumber.java.en.*;
import it.avanscoperta.masterplan.common.domain.FixedTimeInterval;
import it.avanscoperta.masterplan.common.domain.Priority;
import it.avanscoperta.masterplan.common.steps.ScenarioContext;
import it.avanscoperta.masterplan.configuration.domain.UserId;
import it.avanscoperta.masterplan.planning.domain.PotentialActivity;
import it.avanscoperta.masterplan.planning.query.PersonalAvailabilityRepository;
import it.avanscoperta.masterplan.planning.query.PersonalAvailabilityView;
import it.avanscoperta.masterplan.planning.query.RequestInterval;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.Duration;
import java.time.LocalDateTime;

import static org.awaitility.Awaitility.await;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class PlanningSteps {

    @Autowired
    ScenarioContext context;

    @Autowired
    PersonalAvailabilityRepository personalAvailabilityRepository;

    @Then("{string} should be available for a meeting next {string}")
    public void should_be_available_for_a_meeting_next(String username, String weekday) {
        UserId userId = context.retrieveUserId(username);

        await()
                .until(
                        () -> personalAvailabilityRepository.findByUserId(userId).isPresent()
                );
        PersonalAvailabilityView personalAvailabilityView = personalAvailabilityRepository.findByUserId(userId).get();
        RequestInterval requestInterval = new RequestInterval(new FixedTimeInterval(
                LocalDateTime.now(), LocalDateTime.now().plusMonths(3)
        ));
        PotentialActivity meeting = new PotentialActivity(Duration.ofMinutes(90), Priority.STANDARD);
        assertTrue(personalAvailabilityView.isAvailableFor(meeting, requestInterval));
    }

    @Given("{string} is completely busy next week")
    public void is_completely_busy_next_week(String username) {

        UserId userId = context.retrieveUserId(username);



        // Write code here that turns the phrase above into concrete actions
        throw new io.cucumber.java.PendingException();
    }
    @Given("{string} defined a recipe for Commercial Call")
    public void defined_a_recipe_for_commercial_call(String string) {
        // Write code here that turns the phrase above into concrete actions
        throw new io.cucumber.java.PendingException();
    }
    @When("an opportunity pops up for a Commercial Call")
    public void an_opportunity_pops_up_for_a_commercial_call() {
        // Write code here that turns the phrase above into concrete actions
        throw new io.cucumber.java.PendingException();
    }
    @Then("the first available date should be during the following Monday morning")
    public void the_first_available_date_should_be_during_the_following_monday_morning() {
        // Write code here that turns the phrase above into concrete actions
        throw new io.cucumber.java.PendingException();
    }

}
