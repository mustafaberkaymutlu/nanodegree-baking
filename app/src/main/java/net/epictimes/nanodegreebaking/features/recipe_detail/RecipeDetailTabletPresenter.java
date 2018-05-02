package net.epictimes.nanodegreebaking.features.recipe_detail;

import android.support.annotation.NonNull;

import com.hannesdorfmann.mosby3.mvp.MvpBasePresenter;

import net.epictimes.nanodegreebaking.data.model.Step;
import net.epictimes.nanodegreebaking.features.recipe_detail.step_detail.StepDetailContract;
import net.epictimes.nanodegreebaking.features.recipe_detail.step_list.StepListContract;

/**
 Created by Mustafa Berkay Mutlu on 24.04.18.
 */
public class RecipeDetailTabletPresenter {

    private StepListContract.Presenter actualListPresenter;
    private StepDetailContract.Presenter actualDetailPresenter;

    private final StepListContract.Presenter wrapperListPresenter = new ListPresenter();
    private final StepDetailContract.Presenter wrapperDetailPresenter = new DetailPresenter();

    private class ListPresenter extends MvpBasePresenter<StepListContract.View> implements StepListContract.Presenter {

        @Override
        public void getRecipeSteps(final String recipeId) {
            actualListPresenter.getRecipeSteps(recipeId);
        }

        @Override
        public void userClickedRecipeStep(final String recipeId, final Step step) {
            actualDetailPresenter.displayStep(recipeId, step.getId());
        }

        @Override
        public void attachView(@NonNull final StepListContract.View view) {
            super.attachView(view);

            actualListPresenter.attachView(view);
        }

        @Override
        public void detachView() {
            super.detachView();

            actualListPresenter.detachView();
        }

        @Override
        public void destroy() {
            super.destroy();

            actualListPresenter.destroy();
        }
    }

    private class DetailPresenter extends MvpBasePresenter<StepDetailContract.View> implements StepDetailContract.Presenter {

        @Override
        public void displayStep(final String recipeId, final String stepId) {
            actualDetailPresenter.displayStep(recipeId, stepId);
        }

        @Override
        public void attachView(@NonNull final StepDetailContract.View view) {
            super.attachView(view);

            actualDetailPresenter.attachView(view);
        }

        @Override
        public void detachView() {
            super.detachView();

            actualDetailPresenter.detachView();
        }

        @Override
        public void destroy() {
            super.destroy();

            actualDetailPresenter.destroy();
        }
    }

    public StepListContract.Presenter getWrapperListPresenter() {
        return wrapperListPresenter;
    }

    public StepDetailContract.Presenter getWrapperDetailPresenter() {
        return wrapperDetailPresenter;
    }

    public void setActualListPresenter(final StepListContract.Presenter actualListPresenter) {
        this.actualListPresenter = actualListPresenter;
    }

    public void setActualDetailPresenter(final StepDetailContract.Presenter actualDetailPresenter) {
        this.actualDetailPresenter = actualDetailPresenter;
    }
}
