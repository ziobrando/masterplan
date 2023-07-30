package it.avanscoperta.masterplan.design.domain;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

/**
 * This is a State-Based Aggregate
 */
public class ActivityRecipe {

    private final RecipeId recipeId;
    private final String recipeName;
    private final String username;

    private List<Moment> moments = new ArrayList<>();
    private Moment primary;

    private ActivityRecipe(RecipeId recipeId, String recipeName, String username) {
        this.recipeId = recipeId;
        this.recipeName = recipeName;
        this.username = username;
    }

    public static ActivityRecipe createEmpty(CreateEmpty command) {
        return new ActivityRecipe(
                command.recipeId(),
                command.recipeName(),
                command.username()
        );

    }

    public static ActivityRecipe fromKeyMoment(CreateFromKeyMoment command) {
        ActivityRecipe recipe = new ActivityRecipe(
                command.recipeId(),
                command.recipeName(),
                command.username()
        );
        recipe.addPrimaryMoment(command.moment());
        return recipe;
    }

    private void addPrimaryMoment(Moment moment) {
        this.moments.add(moment);
        this.primary = moment; // First dumb idea,
    }

    public void addMoment(Moment moment, TemporalRelationship temporalRelationship) {
        // TODO: fix the relative offset here.
        this.moments.add(moment);
    }

    public String getRecipeName() {
        return this.recipeName;
    }

    public Duration getOverallDuration() {
        return moments
                .stream()
                .map(Moment::duration)
                .reduce(Duration.ZERO, Duration::plus);
    }


}
