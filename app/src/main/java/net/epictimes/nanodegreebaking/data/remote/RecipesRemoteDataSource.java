package net.epictimes.nanodegreebaking.data.remote;

import net.epictimes.nanodegreebaking.data.RecipeDataSource;
import net.epictimes.nanodegreebaking.data.model.recipe.Recipe;
import net.epictimes.nanodegreebaking.data.model.recipe.RecipeMapper;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Flowable;
import io.reactivex.schedulers.Schedulers;

/**
 Created by Mustafa Berkay Mutlu on 22.04.18.
 */
class RecipesRemoteDataSource implements RecipeDataSource {

    @Inject
    Services services;

    @Inject
    RecipeMapper recipeMapper;

    @Inject
    RecipesRemoteDataSource() {
    }

    @Override
    public Flowable<List<Recipe>> getRecipes() {
        return services.getRecipes()
                       .flatMapIterable(recipeRaws -> recipeRaws)
                       .map(recipeMapper)
                       .toList()
                       .toFlowable()
                       .subscribeOn(Schedulers.io());
    }

    @Override
    public Flowable<Recipe> getRecipe(final String recipeId) {
        throw new UnsupportedOperationException();
    }
}
