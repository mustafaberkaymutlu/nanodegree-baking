package net.epictimes.nanodegreebaking.di;

import net.epictimes.nanodegreebaking.features.recipe_list.MainActivity;
import net.epictimes.nanodegreebaking.di.scope.ActivityScoped;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

/**
 Created by Mustafa Berkay Mutlu on 22.04.18.
 */
@Module
abstract class ActivityBuilderModule {

    @ActivityScoped
    @ContributesAndroidInjector
    abstract MainActivity contributeMainActivityInjector();

}
