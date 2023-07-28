package it.avanscoperta.masterplan.design.domain;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Component;

@Component
public interface ActivityRecipeRepository extends MongoRepository<ActivityRecipe, RecipeId> {
}
