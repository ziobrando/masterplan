package it.avanscoperta.masterplan.design.domain;

import java.util.UUID;

public record RecipeId(String id) {
    public static RecipeId generate() {
        return new RecipeId(UUID.randomUUID().toString());
    }
}
