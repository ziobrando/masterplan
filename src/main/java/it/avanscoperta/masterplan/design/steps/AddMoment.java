package it.avanscoperta.masterplan.design.steps;

import it.avanscoperta.masterplan.design.domain.RecipeId;

import java.time.Duration;

public record AddMoment(
        RecipeId recipeId,
        String momentName,
        Duration momentDuration) {
}
