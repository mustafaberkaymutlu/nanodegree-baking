package net.epictimes.nanodegreebaking.data;

import net.epictimes.nanodegreebaking.data.model.recipe.Recipe;
import net.epictimes.nanodegreebaking.di.qualifier.RemoteDataSource;
import net.epictimes.nanodegreebaking.util.SimpleIdlingResource;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import io.reactivex.Flowable;

/**
 * Created by Mustafa Berkay Mutlu on 22.04.18.
 */
public class RecipeRepository implements RecipeDataSource {

    private final Map<String, Recipe> recipeCache = new HashMap<>();

    @RemoteDataSource
    @Inject
    RecipeDataSource remoteDataSource;

    @Inject
    SimpleIdlingResource simpleIdlingResource;

    @Inject
    RecipeRepository() {
    }

    @Override
    public Flowable<List<Recipe>> getRecipes() {
        return remoteDataSource
                .getRecipes()
                .flatMapIterable(recipes -> recipes)
                .doOnNext(this::updateCache)
                .doOnSubscribe(subscription -> simpleIdlingResource.setIdleState(false))
                .doOnComplete(() -> simpleIdlingResource.setIdleState(true))
                .toList()
                .toFlowable();
    }

    @Override
    public Flowable<Recipe> getRecipe(final String recipeId) {
        if (recipeCache.containsKey(recipeId)) {
            return Flowable.just(recipeCache.get(recipeId));
        } else {
            return getRecipes()
                    .flatMapIterable(recipes -> recipes)
                    .filter(recipe -> recipe.getId().equals(recipeId));
        }
    }

    private void updateCache(final Recipe recipe) {
        recipeCache.put(recipe.getId(), recipe);
    }

}
