package it.avanscoperta.masterplan.planning.domain;

import java.util.UUID;

public record PlannedEventId(String id) {
    public static PlannedEventId generate() {
        return new PlannedEventId(UUID.randomUUID().toString());
    }

}
