package net.epictimes.nanodegreebaking.features.recipe_detail.step_detail;

import com.hannesdorfmann.mosby3.mvp.MvpBasePresenter;

import net.epictimes.nanodegreebaking.data.RecipeDataSource;
import net.epictimes.nanodegreebaking.data.model.Recipe;
import net.epictimes.nanodegreebaking.data.model.Step;
import net.epictimes.nanodegreebaking.di.qualifier.Repository;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 Created by Mustafa Berkay Mutlu on 24.04.18.
 */
public class StepDetailPresenter extends MvpBasePresenter<StepDetailContract.View>
        implements StepDetailContract.Presenter {

    private final CompositeDisposable compositeDisposable = new CompositeDisposable();

    @Repository
    @Inject
    RecipeDataSource repository;

    @Inject
    StepDetailPresenter() {
    }

    @Override
    public void displayStep(final String recipeId, final String stepId) {
        final Disposable disposable = repository.getRecipe(recipeId)
                                                .subscribeOn(Schedulers.io())
                                                .flatMapIterable((Function<Recipe, Iterable<Step>>) Recipe::getSteps)
                                                .filter(step -> step.getId().equals(stepId))
                                                .observeOn(AndroidSchedulers.mainThread())
                                                .subscribe(step -> ifViewAttached(view -> view.displayStepDetail(step)),
                                                           throwable -> ifViewAttached(StepDetailContract.View::displayStepError));
        compositeDisposable.add(disposable);
    }

    @Override
    public void destroy() {
        super.destroy();

        compositeDisposable.clear();
    }
}
