package net.epictimes.nanodegreebaking.features.recipe_detail.step_list;

import com.hannesdorfmann.mosby3.mvp.MvpPresenter;
import com.hannesdorfmann.mosby3.mvp.MvpView;

import net.epictimes.nanodegreebaking.data.model.Step;

import java.util.List;

/**
 Created by Mustafa Berkay Mutlu on 24.04.18.
 */
public interface StepListContract {

    interface View extends MvpView {

        void displaySteps(List<Step> steps);

        void displayStepError();

        void openStepDetail(String stepId);
    }

    interface Presenter extends MvpPresenter<View> {

        void getRecipeSteps(String recipeId);

        void userClickedRecipeStep(final String recipeId, Step step);

    }

}
