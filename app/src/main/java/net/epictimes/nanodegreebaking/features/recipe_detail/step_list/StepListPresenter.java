package net.epictimes.nanodegreebaking.features.recipe_detail.step_list;

import com.hannesdorfmann.mosby3.mvp.MvpBasePresenter;
import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import net.epictimes.nanodegreebaking.data.RecipeDataSource;
import net.epictimes.nanodegreebaking.data.model.recipe.Recipe;
import net.epictimes.nanodegreebaking.data.model.step.Step;
import net.epictimes.nanodegreebaking.di.qualifier.IsTablet;
import net.epictimes.nanodegreebaking.di.qualifier.Repository;
import org.apache.commons.collections4.IterableUtils;
import org.reactivestreams.Publisher;
import timber.log.Timber;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

/**
 Created by Mustafa Berkay Mutlu on 24.04.18.
 */
public class StepListPresenter extends MvpBasePresenter<StepListContract.View> implements StepListContract.Presenter {

    static final int INVALID_SELECTED_ITEM_POSITION = -1;
    static final int INITIAL_SELECTED_ITEM_POSITION = 1;

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
    public void getRecipeSteps(final String recipeId, final int initialSelectedStepPosition) {
        final Disposable disposable = repository
                .getRecipe(recipeId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .flatMap(recipe -> mapToViewEntity(recipe, initialSelectedStepPosition))
                .doOnNext(this::updateCache)
                .subscribe(this::displaySteps, this::getRecipeError);
        compositeDisposable.add(disposable);
    }

    private void updateCache(final StepListViewEntity stepListViewEntity) {
        this.stepListViewEntity = stepListViewEntity;
        this.selectedStep = (StepItemViewEntity) stepListViewEntity.getSelectedItem();
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
        final StepItemViewEntity updatedPreviousClickedStep = StepItemViewEntity.Builder
                .aStepItemViewEntity()
                .withStepItemViewEntity(selectedStep)
                .withIsSelected(false)
                .build();

        final StepItemViewEntity updatedNewClickedStep = StepItemViewEntity.Builder
                .aStepItemViewEntity()
                .withStepItemViewEntity(clickedStep)
                .withIsSelected(true)
                .build();

        final List<Visitable> stepItemViewEntityList = stepListViewEntity.getItems();

        final int prevPos = IterableUtils.indexOf(stepItemViewEntityList,
                it -> it instanceof StepItemViewEntity
                        && ((StepItemViewEntity) it).getId().equals(updatedPreviousClickedStep.getId()));
        final int newPos = IterableUtils.indexOf(stepItemViewEntityList,
                it -> it instanceof StepItemViewEntity
                        && ((StepItemViewEntity) it).getId().equals(updatedNewClickedStep.getId()));

        stepItemViewEntityList.set(prevPos, updatedPreviousClickedStep);
        stepItemViewEntityList.set(newPos, updatedNewClickedStep);

        this.selectedStep = updatedNewClickedStep;
        this.stepListViewEntity = new StepListViewEntity(stepItemViewEntityList, newPos);
    }

    private Publisher<? extends StepListViewEntity> mapToViewEntity(final Recipe recipe,
                                                                    final int initialSelectedStepPosition) {
        final int selectedStepPosition = getSelectedPosition(initialSelectedStepPosition);
        final List<Visitable> items = new ArrayList<>();

        items.add(new IngredientsViewEntity(recipe.getIngredients()));

        final List<Step> steps = recipe.getSteps();
        for (int i = 0; i < steps.size(); i++) {
            final Step step = steps.get(i);
            final StepItemViewEntity e = StepItemViewEntity.Builder
                    .aStepItemViewEntity()
                    .withId(step.getId())
                    .withShortDescription(step.getShortDescription())
                    .withPosition(i)
                    .build();
            items.add(e);
        }

        final StepItemViewEntity firstItem = StepItemViewEntity.Builder
                .aStepItemViewEntity()
                .withStepItemViewEntity((StepItemViewEntity) items.get(1))
                .withIsIntroduction(true)
                .build();
        items.set(1, firstItem);

        final StepItemViewEntity selectedItem = StepItemViewEntity.Builder
                .aStepItemViewEntity()
                .withStepItemViewEntity((StepItemViewEntity) items.get(selectedStepPosition))
                .withIsSelected(isTablet)
                .build();
        items.set(selectedStepPosition, selectedItem);

        return Flowable.just(new StepListViewEntity(items, selectedStepPosition));
    }

    private int getSelectedPosition(int initialSelectedStepPosition) {
        if (initialSelectedStepPosition != INVALID_SELECTED_ITEM_POSITION) {
            return initialSelectedStepPosition;
        }

        return INITIAL_SELECTED_ITEM_POSITION;
    }
}
