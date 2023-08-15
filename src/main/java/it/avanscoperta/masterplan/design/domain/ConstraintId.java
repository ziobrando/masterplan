package it.avanscoperta.masterplan.design.domain;

import java.util.UUID;

public record ConstraintId(String id) {

    public static ConstraintId generate() {
        return new ConstraintId(UUID.randomUUID().toString());
    }
}
