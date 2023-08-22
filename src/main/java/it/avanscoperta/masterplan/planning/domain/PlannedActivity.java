package it.avanscoperta.masterplan.planning.domain;

import it.avanscoperta.masterplan.common.domain.Priority;

import java.time.Duration;

public record PlannedActivity(
        Duration duration,
        Priority priority) {
}

