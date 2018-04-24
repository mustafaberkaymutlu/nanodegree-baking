package net.epictimes.nanodegreebaking.features.recipe_detail;

import net.epictimes.nanodegreebaking.di.scope.ActivityScoped;
import net.epictimes.nanodegreebaking.di.scope.FragmentScoped;
import net.epictimes.nanodegreebaking.features.recipe_detail.step_detail.StepDetailFragment;
import net.epictimes.nanodegreebaking.features.recipe_detail.step_detail.StepDetailFragmentModule;
import net.epictimes.nanodegreebaking.features.recipe_detail.step_list.StepListFragment;
import net.epictimes.nanodegreebaking.features.recipe_detail.step_list.StepListFragmentModule;

import dagger.Module;
import dagger.Provides;
import dagger.android.ContributesAndroidInjector;

/**
 Created by Mustafa Berkay Mutlu on 24.04.18.
 */
@Module
public abstract class RecipeDetailActivityModule {

    @FragmentScoped
    @ContributesAndroidInjector(modules = StepDetailFragmentModule.class)
    abstract StepDetailFragment contributeStepDetailFragmentInjector();

    @FragmentScoped
    @ContributesAndroidInjector(modules = StepListFragmentModule.class)
    abstract StepListFragment contributeStepListFragmentInjector();

    @ActivityScoped
    @Provides
    static RecipeDetailTabletPresenter provideRecipeDetailTabletPresenter() {
        return new RecipeDetailTabletPresenter();
    }

}
