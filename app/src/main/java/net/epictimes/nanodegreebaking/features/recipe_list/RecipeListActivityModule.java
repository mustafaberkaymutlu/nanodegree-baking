package net.epictimes.nanodegreebaking.features.recipe_list;

import android.appwidget.AppWidgetManager;
import android.os.Bundle;

import net.epictimes.nanodegreebaking.di.qualifier.IsWidgetConfiguration;
import net.epictimes.nanodegreebaking.di.scope.ActivityScoped;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;

/**
 Created by Mustafa Berkay Mutlu on 23.04.18.
 */
@Module
public abstract class RecipeListActivityModule {

    @ActivityScoped
    @Binds
    abstract RecipeListContract.Presenter provideRecipeListPresenter(RecipeListPresenter presenter);

    @ActivityScoped
    @IsWidgetConfiguration
    @Provides
    static boolean provideIsWidgetConfiguration(RecipeListActivity recipeListActivity) {
        final Bundle extras = recipeListActivity.getIntent().getExtras();
        return extras != null && extras.containsKey(AppWidgetManager.EXTRA_APPWIDGET_ID);
    }

}
