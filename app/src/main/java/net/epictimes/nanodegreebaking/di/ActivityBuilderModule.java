package net.epictimes.nanodegreebaking.di;

import net.epictimes.nanodegreebaking.di.scope.ActivityScoped;
import net.epictimes.nanodegreebaking.features.recipe_list.RecipeListActivity;
import net.epictimes.nanodegreebaking.features.recipe_list.RecipeListActivityModule;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

/**
 Created by Mustafa Berkay Mutlu on 22.04.18.
 */
@Module
abstract class ActivityBuilderModule {

    @ActivityScoped
    @ContributesAndroidInjector(modules = RecipeListActivityModule.class)
    abstract RecipeListActivity contributeMainActivityInjector();

}
