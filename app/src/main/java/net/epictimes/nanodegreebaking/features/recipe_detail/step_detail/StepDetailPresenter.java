package net.epictimes.nanodegreebaking.features.recipe_detail.step_detail;

import com.hannesdorfmann.mosby3.mvp.MvpBasePresenter;

import net.epictimes.nanodegreebaking.data.RecipeDataSource;
import net.epictimes.nanodegreebaking.data.model.recipe.Recipe;
import net.epictimes.nanodegreebaking.data.model.step.Step;
import net.epictimes.nanodegreebaking.di.qualifier.Repository;

import javax.inject.Inject;

import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;

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
    public void getStep(final String recipeId, final String stepId) {
        final Disposable disposable;

        final Flowable<Step> stepFlowable = repository.getRecipe(recipeId)
                                                      .subscribeOn(Schedulers.io())
                                                      .flatMapIterable(Recipe::getSteps);

        if (stepId == null) {
            disposable = stepFlowable
                    .firstElement()
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(this::displayStep, this::displayError);
        } else {
            disposable = stepFlowable
                    .filter(step -> step.getId().equals(stepId))
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(this::displayStep, this::displayError);
        }

        compositeDisposable.add(disposable);
    }

    @Override
    public void resetVideoState() {
        ifViewAttached(StepDetailContract.View::resetVideoState);
    }

    @Override
    public void destroy() {
        super.destroy();

        compositeDisposable.clear();
    }

    private void displayStep(Step step) {
        ifViewAttached(view -> view.displayStepDetail(step));
    }

    private void displayError(Throwable throwable) {
        Timber.e(throwable);
        ifViewAttached(StepDetailContract.View::displayStepError);
    }
}
