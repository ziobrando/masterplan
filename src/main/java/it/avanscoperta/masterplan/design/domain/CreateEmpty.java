package it.avanscoperta.masterplan.design.domain;

public record CreateEmpty(
        RecipeId recipeId,
        String recipeName,
        String username     // TODO: make it a type.
) {
}
