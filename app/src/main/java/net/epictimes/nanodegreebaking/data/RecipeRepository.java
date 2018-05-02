package net.epictimes.nanodegreebaking.data;

import net.epictimes.nanodegreebaking.data.model.Recipe;
import net.epictimes.nanodegreebaking.di.qualifier.RemoteDataSource;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import io.reactivex.Flowable;

/**
 Created by Mustafa Berkay Mutlu on 22.04.18.
 */
public class RecipeRepository implements RecipeDataSource {

    private final Map<String, Recipe> recipeCache = new HashMap<>();

    @RemoteDataSource
    @Inject
    RecipeDataSource remoteDataSource;

    @Inject
    RecipeRepository() {
    }

    @Override
    public Flowable<List<Recipe>> getRecipes() {
        return remoteDataSource.getRecipes()
                               .flatMapIterable(recipes -> recipes)
                               .doOnNext(this::updateCache)
                               .toList()
                               .toFlowable();
    }

    @Override
    public Flowable<Recipe> getRecipe(final String recipeId) {
        final Recipe item = recipeCache.get(recipeId);

        if (item == null) {
            return Flowable.error(() -> new NullPointerException("Cannot found recipe with id: " + recipeId));
        }

        return Flowable.just(item);
    }

    private void updateCache(final Recipe recipe) {
        recipeCache.put(recipe.getId(), recipe);
    }

}
