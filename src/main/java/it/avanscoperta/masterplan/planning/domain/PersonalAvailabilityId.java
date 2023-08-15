package it.avanscoperta.masterplan.planning.domain;

import java.util.UUID;

public record PersonalAvailabilityId(String id) {

    public static PersonalAvailabilityId generate() {
        return new PersonalAvailabilityId(UUID.randomUUID().toString());
    }
}
