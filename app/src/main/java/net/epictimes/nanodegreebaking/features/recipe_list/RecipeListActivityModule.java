package net.epictimes.nanodegreebaking.features.recipe_list;

import net.epictimes.nanodegreebaking.di.scope.ActivityScoped;

import dagger.Binds;
import dagger.Module;

/**
 Created by Mustafa Berkay Mutlu on 23.04.18.
 */
@Module
public abstract class RecipeListActivityModule {

    @ActivityScoped
    @Binds
    abstract RecipeListContract.Presenter provideRecipeListPresenter(RecipeListPresenter presenter);

}
