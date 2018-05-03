package net.epictimes.nanodegreebaking.features.recipe_detail.step_list;

import com.hannesdorfmann.mosby3.mvp.MvpBasePresenter;

import net.epictimes.nanodegreebaking.data.RecipeDataSource;
import net.epictimes.nanodegreebaking.data.model.recipe.Recipe;
import net.epictimes.nanodegreebaking.data.model.step.Step;
import net.epictimes.nanodegreebaking.di.qualifier.Repository;

import org.reactivestreams.Publisher;

import java.util.ArrayList;
import java.util.List;

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
                                                .map(Recipe::getSteps)
                                                .flatMap(StepListPresenter::mapToViewEntity)
                                                .subscribe(this::getRecipeSuccess, this::getRecipeError);
        compositeDisposable.add(disposable);
    }

    private void getRecipeSuccess(final List<StepListItemViewEntity> stepListItemViewEntities) {
        ifViewAttached(view -> view.displaySteps(stepListItemViewEntities));
    }

    private void getRecipeError(Throwable throwable) {
        Timber.e(throwable);
        ifViewAttached(view -> ifViewAttached(StepListContract.View::displayStepError));
    }

    @Override
    public void userClickedRecipeStep(final String recipeId, final String stepId) {
        ifViewAttached(view -> view.openStepDetail(stepId));
    }

    private static Publisher<? extends List<StepListItemViewEntity>> mapToViewEntity(List<Step> steps) {
        final List<StepListItemViewEntity> items = new ArrayList<>();
        for (int i = 0; i < steps.size(); i++) {
            final Step step = steps.get(i);
            items.add(new StepListItemViewEntity(step.getId(), step.getShortDescription(), i));
        }

        return Flowable.just(items);
    }
}
