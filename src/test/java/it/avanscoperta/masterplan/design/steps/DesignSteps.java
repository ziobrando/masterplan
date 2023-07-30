package it.avanscoperta.masterplan.design.steps;

import io.cucumber.java.en.*;
import it.avanscoperta.masterplan.common.steps.ScenarioContext;
import it.avanscoperta.masterplan.design.domain.CreateEmpty;
import it.avanscoperta.masterplan.design.domain.RecipeId;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.Duration;

public class DesignSteps {

    @Autowired
    CommandGateway commandGateway;

    @Autowired
    ScenarioContext context;

    @When("{string} defines a recipe called {string}")
    public void definesARecipeCalled(String username, String recipeName) {
        RecipeId recipeId = RecipeId.generate();
        CreateEmpty createEmptyRecipe = new CreateEmpty(recipeId, recipeName, username);

        commandGateway.send(createEmptyRecipe);

        context.rememberRecipe(recipeName, recipeId);
    }

    @And("{string} adds a {int} min \\(moment) called {string} to {string}")
    public void addsAMinMomentCalledTo(String username, int minutes, String momentName, String recipeName) {
        RecipeId recipeId = context.retrieveRecipeId(recipeName);
        Duration momentDuration = Duration.ofMinutes(minutes);
        AddMoment addMoment = new AddMoment(recipeId, momentName, momentDuration);

        commandGateway.send(addMoment);
    }

    @Then("recipe {string} should include {int} \\(moments)")
    public void recipeShouldIncludeMoments(String arg0, int arg1) {
        throw new io.cucumber.java.PendingException();

    }

    @And("time allocated for a {string} should be {int} hour")
    public void timeAllocatedForAShouldBeHour(String activityName, int allocatedMinutes) {
        // Write code here that turns the phrase above into concrete actions
        throw new io.cucumber.java.PendingException();
    }

    @When("{string} adds a {int} min \\(moment) called {string} right after {string}")
    public void adds_a_min_moment_called_right_after(String string, Integer int1, String string2, String string3) {
        // Write code here that turns the phrase above into concrete actions
        throw new io.cucumber.java.PendingException();
    }
    @When("{string} adds a {int} min \\(moment) called {string} right before {string}")
    public void adds_a_min_moment_called_right_before(String string, Integer int1, String string2, String string3) {
        // Write code here that turns the phrase above into concrete actions
        throw new io.cucumber.java.PendingException();
    }
    @Then("time allocated for a {string} should be {int} hour and {int} minutes")
    public void time_allocated_for_a_should_be_hour_and_minutes(String string, Integer int1, Integer int2) {
        // Write code here that turns the phrase above into concrete actions
        throw new io.cucumber.java.PendingException();
    }

}
