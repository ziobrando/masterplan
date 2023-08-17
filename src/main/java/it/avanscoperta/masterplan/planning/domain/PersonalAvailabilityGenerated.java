package it.avanscoperta.masterplan.planning.domain;

import it.avanscoperta.masterplan.configuration.domain.PlanningHorizon;
import it.avanscoperta.masterplan.configuration.domain.UserId;

public record PersonalAvailabilityGenerated(
        PersonalAvailabilityId personalAvailabilityId,
        UserId userId,
        PlanningHorizon planningHorizon) {
}
