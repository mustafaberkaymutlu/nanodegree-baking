package net.epictimes.nanodegreebaking.features.recipe_detail;

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
        public void userClickedRecipeStep(final Step step) {
            actualListPresenter.userClickedRecipeStep(step);
            actualDetailPresenter.displayStep(step);
        }
    }

    private class DetailPresenter extends MvpBasePresenter<StepDetailContract.View> implements StepDetailContract.Presenter {

        @Override
        public void displayStep(final Step step) {
            actualDetailPresenter.displayStep(step);
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
