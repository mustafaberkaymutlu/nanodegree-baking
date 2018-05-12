package net.epictimes.nanodegreebaking.features.recipe_detail.step_list;

import com.hannesdorfmann.mosby3.mvp.MvpBasePresenter;

import net.epictimes.nanodegreebaking.data.RecipeDataSource;
import net.epictimes.nanodegreebaking.data.model.recipe.Recipe;
import net.epictimes.nanodegreebaking.data.model.step.Step;
import net.epictimes.nanodegreebaking.di.qualifier.IsTablet;
import net.epictimes.nanodegreebaking.di.qualifier.Repository;

import org.apache.commons.collections4.IterableUtils;
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
public class StepListPresenter extends MvpBasePresenter<StepListContract.View> implements StepListContract.Presenter {

    private final CompositeDisposable compositeDisposable = new CompositeDisposable();

    private StepListViewEntity stepListViewEntity;

    private StepItemViewEntity selectedStep;

    @Repository
    @Inject
    RecipeDataSource repository;

    @IsTablet
    @Inject
    boolean isTablet;

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
                                                .flatMap(this::mapToViewEntity)
                                                .doOnNext(this::updateCache)
                                                .subscribe(this::displaySteps, this::getRecipeError);
        compositeDisposable.add(disposable);
    }

    private void updateCache(final StepListViewEntity stepListViewEntity) {
        this.stepListViewEntity = stepListViewEntity;
        this.selectedStep = stepListViewEntity.getStepItemViewEntityList().get(0);
    }

    private void displaySteps(final StepListViewEntity stepListViewEntity) {
        ifViewAttached(view -> view.displaySteps(stepListViewEntity));
    }

    private void getRecipeError(Throwable throwable) {
        Timber.e(throwable);
        ifViewAttached(view -> ifViewAttached(StepListContract.View::displayStepError));
    }

    @Override
    public void userClickedRecipeStep(final String recipeId, final StepItemViewEntity clickedStep) {
        ifViewAttached(view -> {
            if (isTablet) {
                updateClickedListItem(clickedStep);

                view.displaySteps(stepListViewEntity);
            } else {
                view.openStepDetail(clickedStep.getId());
            }
        });
    }

    private void updateClickedListItem(final StepItemViewEntity clickedStep) {
        final StepItemViewEntity updatedPreviousClickedStep = StepItemViewEntity.Builder.aStepItemViewEntity()
                                                                                        .withStepItemViewEntity(selectedStep)
                                                                                        .withIsSelected(false)
                                                                                        .build();

        final StepItemViewEntity updatedNewClickedStep = StepItemViewEntity.Builder.aStepItemViewEntity()
                                                                                   .withStepItemViewEntity(clickedStep)
                                                                                   .withIsSelected(true)
                                                                                   .build();

        this.selectedStep = updatedNewClickedStep;

        final int prevPos = IterableUtils.indexOf(stepListViewEntity.getStepItemViewEntityList(),
                                                  it -> it.getId().equals(updatedPreviousClickedStep.getId()));
        final int newPost = IterableUtils.indexOf(stepListViewEntity.getStepItemViewEntityList(),
                                                  it -> it.getId().equals(updatedNewClickedStep.getId()));

        stepListViewEntity.getStepItemViewEntityList().set(prevPos, updatedPreviousClickedStep);
        stepListViewEntity.getStepItemViewEntityList().set(newPost, updatedNewClickedStep);
    }

    private Publisher<? extends StepListViewEntity> mapToViewEntity(List<Step> steps) {
        final List<StepItemViewEntity> items = new ArrayList<>();

        final Step firstStep = steps.get(0);
        final StepItemViewEntity firstItem = StepItemViewEntity.Builder.aStepItemViewEntity()
                                                                       .withId(firstStep.getId())
                                                                       .withShortDescription(firstStep.getShortDescription())
                                                                       .withIsIntroduction(true)
                                                                       .withIsSelected(isTablet)
                                                                       .build();
        items.add(firstItem);

        for (int i = 1; i < steps.size(); i++) {
            final Step step = steps.get(i);
            final StepItemViewEntity e = StepItemViewEntity.Builder.aStepItemViewEntity()
                                                                   .withId(step.getId())
                                                                   .withShortDescription(step.getShortDescription())
                                                                   .withPosition(i)
                                                                   .build();
            items.add(e);
        }

        return Flowable.just(new StepListViewEntity(items));
    }
}
