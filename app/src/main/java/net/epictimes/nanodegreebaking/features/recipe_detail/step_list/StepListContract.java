package net.epictimes.nanodegreebaking.features.recipe_detail.step_list;

import com.hannesdorfmann.mosby3.mvp.MvpPresenter;
import com.hannesdorfmann.mosby3.mvp.MvpView;

import net.epictimes.nanodegreebaking.data.model.Step;

/**
 Created by Mustafa Berkay Mutlu on 24.04.18.
 */
public interface StepListContract {

    interface View extends MvpView {

    }

    interface Presenter extends MvpPresenter<View> {

        void userClickedRecipeStep(Step step);

    }

}
