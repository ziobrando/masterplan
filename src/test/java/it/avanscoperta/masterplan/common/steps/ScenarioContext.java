package it.avanscoperta.masterplan.common.steps;

import it.avanscoperta.masterplan.configuration.domain.UserId;
import it.avanscoperta.masterplan.design.domain.RecipeId;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class ScenarioContext {

    Map<String, Object> things = new HashMap<>();
    public void rememberRecipe(String recipeName, RecipeId recipeId) {
        things.put(recipeName, recipeId);
    }

    public RecipeId retrieveRecipeId(String recipeName) {
        return (RecipeId) things.get(recipeName);
    }

    public void rememberUser(String username, UserId userId) {
        things.put(username, userId);
    }

    public UserId retrieveUserId(String username) {
        return (UserId) things.get(username);
    }
}
