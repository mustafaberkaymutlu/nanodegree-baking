package net.epictimes.nanodegreebaking.features.recipe_detail.step_list;

import com.hannesdorfmann.mosby3.mvp.MvpBasePresenter;

import net.epictimes.nanodegreebaking.data.RecipeDataSource;
import net.epictimes.nanodegreebaking.data.model.recipe.Recipe;
import net.epictimes.nanodegreebaking.di.qualifier.Repository;

import javax.inject.Inject;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 Created by Mustafa Berkay Mutlu on 24.04.18.
 */
public class StepListPresenter extends MvpBasePresenter<StepListContract.View>
        implements StepListContract.Presenter {

    private final CompositeDisposable compositeDisposable = new CompositeDisposable();

    @Repository
    @Inject
    RecipeDataSource repository;

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
                                                .flatMapIterable(Recipe::getSteps)
                                                // TODO increase position
                                                .map(step -> new StepListItemViewEntity(step.getId(), step.getShortDescription(), 0))
                                                .toList()
                                                .toFlowable()
                                                .subscribe(stepListItemViewEntities -> ifViewAttached(view -> view.displaySteps(stepListItemViewEntities)),
                                                               throwable -> ifViewAttached(view -> ifViewAttached(StepListContract.View::displayStepError)));
        compositeDisposable.add(disposable);
    }

    @Override
    public void userClickedRecipeStep(final String recipeId, final String stepId) {
        ifViewAttached(view -> view.openStepDetail(stepId));
    }
}
