package it.avanscoperta.masterplan.common.domain;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * TODO: maybe this class is redundant.
 * FIXME: LocalDateTime is a lazy shortcut.
 * @param fromTime  the beginning of the interval
 * @param toTime    the end of the interval
 */
public record FixedTimeInterval(LocalDateTime fromTime, LocalDateTime toTime) {

    public boolean includes(LocalDate day) {
        return fromTime.isBefore(day.atStartOfDay()) && toTime.isAfter(day.plusDays(1).atStartOfDay());
    }

    /**
     * Returns true if the Interval, or a part of it happens during the given <code>day</code>
     * @param day the <code>LocalDate</code> to check.
     * @return  true if the Interval, or a part of it happens during the given <code>day</code>
     */
    public boolean spansOver(LocalDate day) {
        return fromTime.isAfter(day.atStartOfDay()) &&
        fromTime.isBefore(day.plusDays(1).atStartOfDay());

        // FIXME: finish after testing
    }

    public boolean startsBeforeThan(FixedTimeInterval other) {
        return this.fromTime.isBefore(other.fromTime);
    }
}
