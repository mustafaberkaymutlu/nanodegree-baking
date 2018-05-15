package net.epictimes.nanodegreebaking.features.recipe_detail.step_list;

import net.epictimes.nanodegreebaking.di.qualifier.IsTablet;
import net.epictimes.nanodegreebaking.di.scope.FragmentScoped;
import net.epictimes.nanodegreebaking.features.recipe_detail.RecipeDetailTabletPresenter;

import dagger.Binds;
import dagger.Lazy;
import dagger.Module;
import dagger.Provides;

/**
 Created by Mustafa Berkay Mutlu on 24.04.18.
 */
@Module
public abstract class StepListFragmentModule {

    @FragmentScoped
    @Provides
    static StepListContract.Presenter provideStepListPresenter(@IsTablet boolean isTablet,
                                                               StepListPresenter presenter,
                                                               Lazy<RecipeDetailTabletPresenter> tabletPresenter) {
        if (isTablet) {
            final RecipeDetailTabletPresenter recipeDetailTabletPresenter = tabletPresenter.get();
            recipeDetailTabletPresenter.setActualListPresenter(presenter);
            return recipeDetailTabletPresenter.getWrapperListPresenter();
        } else {
            return presenter;
        }
    }

    @FragmentScoped
    @Binds
    abstract TypeFactory provideTypeFactory(TypeFactoryForStepList typeFactoryForStepList);

    @FragmentScoped
    @Binds
    abstract Comparator provideComparator(VisitableComparator visitableComparator);

}
