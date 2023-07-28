package it.avanscoperta.masterplan.design.domain;

public record CreateFromKeyMoment(
        RecipeId recipeId,
        String recipeName,
        String username,
        Moment moment
) {
}
