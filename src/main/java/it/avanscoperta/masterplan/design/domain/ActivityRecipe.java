package it.avanscoperta.masterplan.design.domain;

import it.avanscoperta.masterplan.design.steps.AddMoment;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * This is a State-Based Aggregate
 */
@Document(collection = "Activity Recipe")
public class ActivityRecipe {

    @Id
    private final RecipeId recipeId;
    private final String recipeName;
    private final String username;

    private List<PlannedMoment> moments = new ArrayList<>();
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
        this.moments.add(new PlannedMoment(moment, Duration.ZERO));
        this.primary = moment; // First dumb idea,
    }

    public void addMoment(AddMoment addMoment) {
        if (primary == null) primary = addMoment.moment();
        Duration relativeOffset = addMoment.getRelativeOffset(this.primary);
        this.moments.add(new PlannedMoment(addMoment.moment(), relativeOffset));
    }

    public String getRecipeName() {
        return this.recipeName;
    }

    public Duration getOverallDuration() {
        return moments
                .stream()
                .map(PlannedMoment::moment)
                .map(Moment::duration)
                .reduce(Duration.ZERO, Duration::plus);
    }


    public List<PlannedMoment> getMoments() {
        return this.moments;
    }

    public Duration getOffsetBeforeStart() {
        return moments.stream()
                .sorted(Comparator.comparing(PlannedMoment::relativeOffset))
                .findFirst()
                .get()
                .relativeOffset();
    }
}
