package net.epictimes.nanodegreebaking.data;

import net.epictimes.nanodegreebaking.data.model.recipe.Recipe;

import java.util.List;

import io.reactivex.Flowable;

/**
 Created by Mustafa Berkay Mutlu on 22.04.18.
 */
public interface RecipeDataSource {

    Flowable<List<Recipe>> getRecipes();

    Flowable<Recipe> getRecipe(final String recipeId);

}
