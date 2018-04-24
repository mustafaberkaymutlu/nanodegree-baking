package net.epictimes.nanodegreebaking.features.recipe_detail.step_detail;

import net.epictimes.nanodegreebaking.di.qualifier.IsTablet;
import net.epictimes.nanodegreebaking.di.scope.FragmentScoped;
import net.epictimes.nanodegreebaking.features.recipe_detail.RecipeDetailTabletPresenter;

import dagger.Lazy;
import dagger.Module;
import dagger.Provides;

/**
 Created by Mustafa Berkay Mutlu on 24.04.18.
 */
@Module
public class StepDetailFragmentModule {

    @FragmentScoped
    @Provides
    StepDetailContract.Presenter provideStepDetailPresenter(@IsTablet boolean isTablet,
                                                            StepDetailPresenter presenter,
                                                            Lazy<RecipeDetailTabletPresenter> tabletPresenter) {
        if (isTablet) {
            final RecipeDetailTabletPresenter recipeDetailTabletPresenter = tabletPresenter.get();
            recipeDetailTabletPresenter.setActualDetailPresenter(presenter);
            return recipeDetailTabletPresenter.getWrapperDetailPresenter();
        } else {
            return presenter;
        }
    }

}
