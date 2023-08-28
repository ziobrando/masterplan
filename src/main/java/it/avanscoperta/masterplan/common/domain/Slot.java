package it.avanscoperta.masterplan.common.domain;

import it.avanscoperta.masterplan.planning.domain.PlannedActivity;

import java.time.Duration;
import java.time.LocalDateTime;

public record Slot(FixedTimeInterval timeInterval) {

    /**
     * Utility constructor.
     * @param from the beginning of the interval.
     * @param to the end of the interval.
     */
    public Slot(LocalDateTime from, LocalDateTime to) {
        this(new FixedTimeInterval(from, to));
    }

    public boolean hasRoomFor(PlannedActivity plannedActivity) {
        return this.toDuration().compareTo(plannedActivity.duration()) >= 0;
    }

    private Duration toDuration() {
        return Duration.between(timeInterval.fromTime(), timeInterval.toTime());
    }
}
