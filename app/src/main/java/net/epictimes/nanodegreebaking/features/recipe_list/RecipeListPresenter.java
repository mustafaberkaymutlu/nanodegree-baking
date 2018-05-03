package net.epictimes.nanodegreebaking.features.recipe_list;

import com.hannesdorfmann.mosby3.mvp.MvpBasePresenter;

import net.epictimes.nanodegreebaking.data.RecipeDataSource;
import net.epictimes.nanodegreebaking.data.model.recipe.Recipe;
import net.epictimes.nanodegreebaking.di.qualifier.Repository;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import timber.log.Timber;

/**
 Created by Mustafa Berkay Mutlu on 23.04.18.
 */
class RecipeListPresenter extends MvpBasePresenter<RecipeListContract.View>
        implements RecipeListContract.Presenter {

    @Repository
    @Inject
    RecipeDataSource recipeRepository;

    private final CompositeDisposable compositeDisposable = new CompositeDisposable();

    @Inject
    RecipeListPresenter() {
    }

    @Override
    public void getRecipes() {
        final Disposable disposable = recipeRepository.getRecipes()
                                                      .observeOn(AndroidSchedulers.mainThread())
                                                      .subscribe(this::getRecipesSuccess, this::getRecipesError);
        compositeDisposable.add(disposable);
    }

    @Override
    public void userClickedRecipe(final Recipe recipe) {
        ifViewAttached(view -> view.goToRecipeDetail(recipe));
    }

    @Override
    public void destroy() {
        super.destroy();

        compositeDisposable.clear();
    }

    private void getRecipesSuccess(List<Recipe> recipes) {
        ifViewAttached(view -> view.displayRecipes(recipes));
    }

    private void getRecipesError(Throwable throwable) {
        Timber.e(throwable);
        ifViewAttached(RecipeListContract.View::displayRecipesError);
    }
}
