package net.epictimes.nanodegreebaking.features.recipe_list;

import com.hannesdorfmann.mosby3.mvp.MvpBasePresenter;

import net.epictimes.nanodegreebaking.data.RecipeDataSource;
import net.epictimes.nanodegreebaking.data.model.recipe.Recipe;
import net.epictimes.nanodegreebaking.di.qualifier.Repository;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

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
                                                      .subscribe(recipes -> ifViewAttached(view -> view.displayRecipes(recipes)),
                                                                 throwable -> ifViewAttached(RecipeListContract.View::displayRecipesError));
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
}
