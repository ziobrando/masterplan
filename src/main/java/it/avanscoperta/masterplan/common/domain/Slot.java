package it.avanscoperta.masterplan.common.domain;

import it.avanscoperta.masterplan.planning.domain.PlannedActivity;

import java.time.Duration;
import java.time.LocalDateTime;

public record Slot(LocalDateTime from, LocalDateTime to) {

    public boolean hasRoomFor(PlannedActivity plannedActivity) {
        return this.toDuration().compareTo(plannedActivity.duration()) >= 0;
    }

    private Duration toDuration() {
        return Duration.between(from(), to());
    }
}
