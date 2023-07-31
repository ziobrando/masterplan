package it.avanscoperta.masterplan.design.domain;

import java.time.Duration;

/**
 * A moment in a sequence.
 */
public record PlannedMoment(
        Moment moment,
        Duration relativeOffset) {
}
