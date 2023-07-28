package it.avanscoperta.masterplan.design.domain;

import java.time.Duration;

public record Moment(
        String label,
        Duration duration
) {
}
