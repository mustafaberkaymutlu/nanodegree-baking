package net.epictimes.nanodegreebaking.features.recipe_detail.step_list;

import com.hannesdorfmann.mosby3.mvp.MvpBasePresenter;

import net.epictimes.nanodegreebaking.data.model.Step;

import javax.inject.Inject;

/**
 Created by Mustafa Berkay Mutlu on 24.04.18.
 */
public class StepListPresenter extends MvpBasePresenter<StepListContract.View>
        implements StepListContract.Presenter {

    @Inject
    StepListPresenter() {
    }

    @Override
    public void userClickedRecipeStep(final Step step) {
        // TODO
    }
}
