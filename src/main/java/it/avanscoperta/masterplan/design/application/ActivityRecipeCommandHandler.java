package it.avanscoperta.masterplan.design.application;

import it.avanscoperta.masterplan.design.domain.ActivityRecipe;
import it.avanscoperta.masterplan.design.domain.ActivityRecipeRepository;
import it.avanscoperta.masterplan.design.domain.CreateEmpty;
import it.avanscoperta.masterplan.design.steps.AddMoment;
import org.axonframework.commandhandling.CommandHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class ActivityRecipeCommandHandler {
    final static Logger logger = LoggerFactory.getLogger(ActivityRecipeCommandHandler.class);

    final ActivityRecipeRepository repository;

    public ActivityRecipeCommandHandler(ActivityRecipeRepository repository) {
        this.repository = repository;
    }

    @CommandHandler
    public void createEmptyRecipe(CreateEmpty command) {
        ActivityRecipe recipe = ActivityRecipe.createEmpty(command);
        logger.info("Created an empty recipe called " + recipe.getRecipeName());
        repository.save(recipe);
        logger.info("Saved an empty recipe called " + recipe.getRecipeName());
    }

    @CommandHandler
    public void addMomentToRecipe(AddMoment command) {
        repository.findById(command.recipeId()).ifPresentOrElse(
                (recipe) -> {
                    recipe.addMoment(command);
                    repository.save(recipe);
                    logger.info("Added moment " + command.moment() + " to recipe:" + command.recipeId());
                },
                () -> {
                    logger.error("Couldn't find recipe with recipeId: " + command.recipeId());
                    throw new RuntimeException("Can't find it");
                }
        );

    }

}
