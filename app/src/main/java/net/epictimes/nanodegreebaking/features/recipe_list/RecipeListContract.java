package net.epictimes.nanodegreebaking.features.recipe_list;

import com.hannesdorfmann.mosby3.mvp.MvpPresenter;
import com.hannesdorfmann.mosby3.mvp.MvpView;

import net.epictimes.nanodegreebaking.data.model.Recipe;

import java.util.List;

/**
 Created by Mustafa Berkay Mutlu on 23.04.18.
 */
public interface RecipeListContract {

    interface View extends MvpView {

        void displayRecipes(List<Recipe> recipes);

        void goToRecipeDetail(Recipe recipe);

    }

    interface Presenter extends MvpPresenter<View> {

        void getRecipes();

        void userClickedRecipe(Recipe recipe);

    }

}
