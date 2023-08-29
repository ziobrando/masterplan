package it.avanscoperta.masterplan.planning.domain;

import it.avanscoperta.masterplan.common.domain.Slot;

public interface PlanningConstraint {
    boolean isSatisfiedBy(Slot potentialSlot);
}
