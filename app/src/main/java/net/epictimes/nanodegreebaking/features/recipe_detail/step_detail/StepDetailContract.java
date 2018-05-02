package net.epictimes.nanodegreebaking.features.recipe_detail.step_detail;

import com.hannesdorfmann.mosby3.mvp.MvpPresenter;
import com.hannesdorfmann.mosby3.mvp.MvpView;

import net.epictimes.nanodegreebaking.data.model.step.Step;
import net.epictimes.nanodegreebaking.data.model.step.StepRaw;

/**
 Created by Mustafa Berkay Mutlu on 24.04.18.
 */
public interface StepDetailContract {

    interface View extends MvpView {

        void displayStepDetail(Step step);

        void displayStepError();

    }

    interface Presenter extends MvpPresenter<View> {

        void displayStep(final String recipeId, final String stepId);

    }

}
