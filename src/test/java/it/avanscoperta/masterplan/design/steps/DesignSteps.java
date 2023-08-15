package it.avanscoperta.masterplan.design.steps;

import io.cucumber.java.en.*;
import it.avanscoperta.masterplan.common.steps.ScenarioContext;
import it.avanscoperta.masterplan.configuration.domain.UserId;
import it.avanscoperta.masterplan.design.domain.*;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.Duration;
import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DesignSteps {

    @Autowired
    CommandGateway commandGateway;

    @Autowired
    ActivityRecipeRepository activityRecipeRepository;

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
        Moment moment = new Moment(momentName, momentDuration);
        AddMoment addMoment = new AddMoment(recipeId, moment, TemporalRelationship.RIGHT_AFTER);

        commandGateway.send(addMoment);
    }

    @Then("recipe {string} should include {int} \\(moments)")
    public void recipeShouldIncludeMoments(String recipeName, int momentsCount) {
        RecipeId recipeId = context.retrieveRecipeId(recipeName);
        ActivityRecipe recipe = activityRecipeRepository.findById(recipeId).get();
        assertEquals(momentsCount, recipe.getMoments().size());
    }

    @And("time allocated for a {string} should be {int} hour")
    public void timeAllocatedForAShouldBeHour(String recipeName, int hours) {
        checkRecipeDuration(recipeName, hours, 0);
    }

    @When("{string} adds a {int} min \\(moment) called {string} right after {string}")
    public void adds_a_min_moment_called_right_after(String username, Integer minutes, String momentName, String recipeName) {
        RecipeId recipeId = context.retrieveRecipeId(recipeName);
        Duration momentDuration = Duration.ofMinutes(minutes);
        Moment moment = new Moment(momentName, momentDuration);
        AddMoment addMoment = new AddMoment(recipeId, moment, TemporalRelationship.RIGHT_AFTER);

        commandGateway.send(addMoment);
    }
    @When("{string} adds a {int} min \\(moment) called {string} right before {string}")
    public void adds_a_min_moment_called_right_before(String username, Integer minutes, String momentName, String recipeName) {
        RecipeId recipeId = context.retrieveRecipeId(recipeName);
        Duration momentDuration = Duration.ofMinutes(minutes);
        Moment moment = new Moment(momentName, momentDuration);
        AddMoment addMoment = new AddMoment(recipeId, moment, TemporalRelationship.RIGHT_BEFORE);

        commandGateway.send(addMoment);
    }
    @Then("time allocated for a {string} should be {int} hour and {int} minutes")
    public void time_allocated_for_a_should_be_hour_and_minutes(String recipeName, Integer hours, Integer minutes) {
        checkRecipeDuration(recipeName, hours, minutes);
    }

    private void checkRecipeDuration(String recipeName, Integer hours, Integer minutes) {
        RecipeId recipeId = context.retrieveRecipeId(recipeName);
        ActivityRecipe recipe = activityRecipeRepository.findById(recipeId).get();
        Duration expected = Duration.ofHours(hours).plusMinutes(minutes);
        assertEquals(expected, recipe.getOverallDuration());
    }

    @And("footprint for {string} should start {int} minutes before the official start")
    public void footprintShouldStartMinutesBeforeTheOfficialStart(String recipeName, int minutesBeforeStart) {
        RecipeId recipeId = context.retrieveRecipeId(recipeName);
        ActivityRecipe recipe = activityRecipeRepository.findById(recipeId).get();
        Duration expectedOffset = Duration.ZERO.minusMinutes(minutesBeforeStart);
        assertEquals(expectedOffset, recipe.getOffsetBeforeStart());

    }

    @When("{string} sets up a {int} priority constraint for not working on weekends")
    public void setsUpAPriorityConstraintForNotWorkingOnWeekends(String username, int priority) {
        UserId userId = context.retrieveUserId(username);
        AvailabilityConstraint constraint = new AvailabilityConstraint(); // FIXME: just a placeholder
        DefineConstraint defineConstraint = new DefineConstraint(
                userId, "Save the weekends", priority, constraint);


    @And("{string} defined a {string} constraint from {string} to {string}")
    public void definedAConstraintFromTo(String username, String constraintName, String startTime, String endTime) {
        ConstraintId constraintId = ConstraintId.generate();
        UserId userId = UserId.generate();
        LocalTime fromTime = LocalTime.parse(startTime);
        LocalTime toTime = LocalTime.parse(endTime);
        DefineConstraint defineConstraint = new DefineConstraint(constraintId, userId, constraintName, fromTime, toTime, ConstraintType.ONLY_HERE);

        commandGateway.send(defineConstraint);
    }
}
