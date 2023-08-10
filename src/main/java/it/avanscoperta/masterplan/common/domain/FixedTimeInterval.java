package it.avanscoperta.masterplan.common.domain;

import java.time.LocalDateTime;

/**
 * TODO: maybe this class is redundant.
 * FIXME: LocalDateTime is a lazy shortcut.
 * @param fromTime  the beginning of the interval
 * @param toTime    the end of the interval
 */
public record FixedTimeInterval(LocalDateTime fromTime, LocalDateTime toTime) {
}
