package it.avanscoperta.masterplan.common.domain;

/**
 * A simple type to hold Durations expressed in days.
 * @param days
 */
public record DaysDuration(int days) {
    public static DaysDuration of(int days) {
        return new DaysDuration(days);
    }

    public DaysDuration {
        if (days < 0) throw new IllegalArgumentException("No negative durations allowed");
    }
}
