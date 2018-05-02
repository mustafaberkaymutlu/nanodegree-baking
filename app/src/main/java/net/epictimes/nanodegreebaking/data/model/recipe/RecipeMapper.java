package net.epictimes.nanodegreebaking.data.model.recipe;

import net.epictimes.nanodegreebaking.data.model.ingredient.Ingredient;
import net.epictimes.nanodegreebaking.data.model.ingredient.IngredientMapper;
import net.epictimes.nanodegreebaking.data.model.ingredient.IngredientRaw;
import net.epictimes.nanodegreebaking.data.model.step.Step;
import net.epictimes.nanodegreebaking.data.model.step.StepMapper;
import net.epictimes.nanodegreebaking.data.model.step.StepRaw;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.functions.Function;

/**
 Created by Mustafa Berkay Mutlu on 02.05.18.
 */
public class RecipeMapper implements Function<RecipeRaw, Recipe> {

    private final StepMapper stepMapper;

    private final IngredientMapper ingredientMapper;

    @Inject
    public RecipeMapper(final StepMapper stepMapper, final IngredientMapper ingredientMapper) {
        this.stepMapper = stepMapper;
        this.ingredientMapper = ingredientMapper;
    }

    @Override
    public Recipe apply(final RecipeRaw recipeRaw) throws Exception {
        validateFields(recipeRaw);

        return Recipe.RecipeBuilder.aRecipe()
                                   .withId(recipeRaw.getId())
                                   .withName(recipeRaw.getName())
                                   .withSteps(mapSteps(recipeRaw.getSteps()))
                                   .withImage(recipeRaw.getImage())
                                   .withIngredients(mapIngredients(recipeRaw.getIngredients()))
                                   .withServings(recipeRaw.getServings())
                                   .build();
    }

    private List<Step> mapSteps(final List<StepRaw> stepRaws) throws Exception {
        final List<Step> steps = new ArrayList<>();

        for (final StepRaw stepRaw : stepRaws) {
            steps.add(stepMapper.apply(stepRaw));
        }

        return steps;
    }

    private List<Ingredient> mapIngredients(final List<IngredientRaw> ingredientRaws) throws Exception {
        final List<Ingredient> ingredients = new ArrayList<>();

        for (final IngredientRaw ingredientRaw : ingredientRaws) {
            ingredients.add(ingredientMapper.apply(ingredientRaw));
        }

        return ingredients;
    }

    private void validateFields(final RecipeRaw recipeRaw) {
        // TODO validate fields
    }
}
