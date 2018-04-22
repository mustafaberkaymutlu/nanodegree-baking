package net.epictimes.nanodegreebaking.data.remote;

import net.epictimes.nanodegreebaking.data.RecipeDataSource;
import net.epictimes.nanodegreebaking.data.model.Recipe;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Flowable;

/**
 Created by Mustafa Berkay Mutlu on 22.04.18.
 */
class RecipesRemoteDataSource implements RecipeDataSource {

    @Inject
    Services services;

    @Inject
    RecipesRemoteDataSource() {
    }

    @Override
    public Flowable<List<Recipe>> getRecipes() {
        return services.getRecipes();
    }
}
