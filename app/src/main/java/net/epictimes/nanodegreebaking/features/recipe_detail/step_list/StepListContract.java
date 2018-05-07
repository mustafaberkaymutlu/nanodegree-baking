package net.epictimes.nanodegreebaking.features.recipe_detail.step_list;

import com.hannesdorfmann.mosby3.mvp.MvpPresenter;
import com.hannesdorfmann.mosby3.mvp.MvpView;

/**
 Created by Mustafa Berkay Mutlu on 24.04.18.
 */
public interface StepListContract {

    interface View extends MvpView {

        void displaySteps(StepListViewEntity stepListViewEntity);

        void displayStepError();

        void openStepDetail(String stepId);
    }

    interface Presenter extends MvpPresenter<View> {

        void getRecipeSteps(String recipeId);

        void userClickedRecipeStep(final String recipeId, StepItemViewEntity clickedStep);

    }

}
