package net.epictimes.nanodegreebaking.data;

import net.epictimes.nanodegreebaking.data.model.Recipe;
import net.epictimes.nanodegreebaking.di.qualifier.RemoteDataSource;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Flowable;
import io.reactivex.schedulers.Schedulers;

/**
 Created by Mustafa Berkay Mutlu on 22.04.18.
 */
public class RecipeRepository implements RecipeDataSource {

    @RemoteDataSource
    @Inject
    RecipeDataSource remoteDataSource;

    @Inject
    RecipeRepository() {
    }

    @Override
    public Flowable<List<Recipe>> getRecipes() {
        return remoteDataSource.getRecipes()
                               .subscribeOn(Schedulers.io());
    }

}
