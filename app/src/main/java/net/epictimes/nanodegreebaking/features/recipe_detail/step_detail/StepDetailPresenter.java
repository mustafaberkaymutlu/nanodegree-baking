package net.epictimes.nanodegreebaking.features.recipe_detail.step_detail;

import com.hannesdorfmann.mosby3.mvp.MvpBasePresenter;

import net.epictimes.nanodegreebaking.data.model.Step;

import javax.inject.Inject;

/**
 Created by Mustafa Berkay Mutlu on 24.04.18.
 */
public class StepDetailPresenter extends MvpBasePresenter<StepDetailContract.View>
        implements StepDetailContract.Presenter {

    @Inject
    StepDetailPresenter() {
    }

    @Override
    public void displayStep(final Step step) {
        // TODO
    }
}
