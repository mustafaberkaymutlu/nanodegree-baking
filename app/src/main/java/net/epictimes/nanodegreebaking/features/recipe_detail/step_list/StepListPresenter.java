package net.epictimes.nanodegreebaking.features.recipe_detail.step_list;

import com.hannesdorfmann.mosby3.mvp.MvpBasePresenter;

import net.epictimes.nanodegreebaking.data.RecipeDataSource;
import net.epictimes.nanodegreebaking.data.model.Step;
import net.epictimes.nanodegreebaking.di.qualifier.Repository;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 Created by Mustafa Berkay Mutlu on 24.04.18.
 */
public class StepListPresenter extends MvpBasePresenter<StepListContract.View>
        implements StepListContract.Presenter {

    private final CompositeDisposable compositeDisposable = new CompositeDisposable();

    @Repository
    @Inject
    RecipeDataSource repository;

    @Inject
    StepListPresenter() {
    }

    @Override
    public void destroy() {
        super.destroy();

        compositeDisposable.clear();
    }

    @Override
    public void getRecipeSteps(final String recipeId) {
        final Disposable disposable = repository.getRecipe(recipeId)
                                                .subscribeOn(Schedulers.io())
                                                .observeOn(AndroidSchedulers.mainThread())
                                                .subscribe(recipe -> ifViewAttached(view -> view.displaySteps(recipe.getSteps())),
                                                           throwable -> ifViewAttached(view -> ifViewAttached(StepListContract.View::displayStepError)));
        compositeDisposable.add(disposable);
    }

    @Override
    public void userClickedRecipeStep(final String recipeId, final Step step) {
        ifViewAttached(view -> view.openStepDetail(step.getId()));
    }
}
