package it.avanscoperta.masterplan.design.steps;

import it.avanscoperta.masterplan.design.domain.Moment;
import it.avanscoperta.masterplan.design.domain.RecipeId;
import it.avanscoperta.masterplan.design.domain.TemporalRelationship;

public record AddMoment(
        RecipeId recipeId,
        Moment moment,
        TemporalRelationship temporalRelationship) {

}
