package it.avanscoperta.masterplan.configuration.domain;

import java.util.UUID;

public record UserId(String id) {

    public static UserId generate() {
        return new UserId(UUID.randomUUID().toString());
    }
}
