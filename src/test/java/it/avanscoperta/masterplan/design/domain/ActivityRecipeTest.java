package it.avanscoperta.masterplan.design.domain;

import it.avanscoperta.masterplan.design.steps.AddMoment;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("[Masterplan | Design]: ActivityRecipe Unit")
public class ActivityRecipeTest {

    private RecipeId recipeId;
    private String username;
    private String recipeName;
    private CreateEmpty createEmpty;
    private ActivityRecipe emptyRecipe;
    private Duration callDuration;
    private Moment call;

    @BeforeEach
    void setUp() {
        recipeId = RecipeId.generate();
        username = "username";
        recipeName = "Solita roba";
        createEmpty = new CreateEmpty(recipeId, recipeName, username);
        emptyRecipe = ActivityRecipe.createEmpty(createEmpty);
        callDuration = Duration.ofMinutes(45);
        call = new Moment("call", callDuration);
    }

    @Test
    @DisplayName("Can create an empty recipe")
    void can_create_an_empty_recipe() {
        assertNotNull(emptyRecipe);
        assertEquals(recipeName, emptyRecipe.getRecipeName());
    }

    @Test
    @DisplayName("Empty recipe have zero duration")
    void empty_recipes_have_zero_duration() {
        assertEquals(Duration.ZERO, emptyRecipe.getOverallDuration());
    }

    @Test
    @DisplayName("Can create from a key moment")
    void can_create_from_a_primary_moment() {
        CreateFromKeyMoment createFromKeyMoment = new CreateFromKeyMoment(recipeId, recipeName, username, call);

        ActivityRecipe singleMomentRecipe = ActivityRecipe.fromKeyMoment(createFromKeyMoment);

        assertNotNull(singleMomentRecipe);
        assertEquals(recipeName, singleMomentRecipe.getRecipeName());
    }

    @Test
    @DisplayName("Single Moment Activities have the moment duration")
    void single_moment_activities_have_the_moment_duration() {
        Duration momentDuration = Duration.ofMinutes(90);
        call = new Moment("call", momentDuration);
        CreateFromKeyMoment createFromKeyMoment = new CreateFromKeyMoment(recipeId, recipeName, username, call);

        ActivityRecipe singleMomentRecipe = ActivityRecipe.fromKeyMoment(createFromKeyMoment);

        assertEquals(momentDuration, singleMomentRecipe.getOverallDuration());
    }

    @Test
    @DisplayName("Can add an extra moment")
    void can_add_an_extra_moment() {
        CreateFromKeyMoment createFromKeyMoment = new CreateFromKeyMoment(recipeId, recipeName, username, call);

        ActivityRecipe singleMomentRecipe = ActivityRecipe.fromKeyMoment(createFromKeyMoment);

        Duration preparationDuration = Duration.ofMinutes(15);
        Moment preparation = new Moment("preparation", preparationDuration);

        singleMomentRecipe.addMoment(new AddMoment(recipeId, preparation, TemporalRelationship.RIGHT_BEFORE));

        assertNotNull(singleMomentRecipe);
        assertEquals(recipeName, singleMomentRecipe.getRecipeName());
    }


    @Test
    @DisplayName("The extra moment increments the duration")
    void the_extra_moment_increments_duration() {
        CreateFromKeyMoment createFromKeyMoment = new CreateFromKeyMoment(recipeId, recipeName, username, call);

        ActivityRecipe singleMomentRecipe = ActivityRecipe.fromKeyMoment(createFromKeyMoment);

        Duration preparationDuration = Duration.ofMinutes(15);
        Moment preparation = new Moment("preparation", preparationDuration);

        singleMomentRecipe.addMoment(new AddMoment(recipeId, preparation, TemporalRelationship.RIGHT_BEFORE));

        assertEquals(callDuration.plus(preparationDuration), singleMomentRecipe.getOverallDuration());

    }


}
