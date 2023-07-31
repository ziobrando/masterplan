package it.avanscoperta.masterplan.design.steps;

import it.avanscoperta.masterplan.design.domain.Moment;
import it.avanscoperta.masterplan.design.domain.RecipeId;
import it.avanscoperta.masterplan.design.domain.TemporalRelationship;

import java.time.Duration;

public record AddMoment(
        RecipeId recipeId,
        Moment moment,
        TemporalRelationship temporalRelationship) {

    public Duration getRelativeOffset(Moment primary) {
        return temporalRelationship.getRelativeOffsetFrom(moment, primary);
    }
}
