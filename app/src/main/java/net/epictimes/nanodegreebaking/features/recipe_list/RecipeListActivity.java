package net.epictimes.nanodegreebaking.features.recipe_list;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import net.epictimes.nanodegreebaking.R;
import net.epictimes.nanodegreebaking.data.model.recipe.Recipe;
import net.epictimes.nanodegreebaking.features.BaseActivity;
import net.epictimes.nanodegreebaking.features.recipe_detail.RecipeDetailActivity;

import java.util.List;

import javax.inject.Inject;

import dagger.android.AndroidInjection;

public class RecipeListActivity extends BaseActivity<RecipeListContract.View, RecipeListContract.Presenter>
        implements RecipeListContract.View {

    @Inject
    RecipeListContract.Presenter recipePresenter;

    private RecipeRecyclerViewAdapter recyclerViewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AndroidInjection.inject(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_list);

        final RecyclerView recyclerViewRecipes = findViewById(R.id.recyclerViewRecipes);

        recyclerViewAdapter = new RecipeRecyclerViewAdapter();

        recyclerViewAdapter.setRecipeClickListener(recipe -> presenter.userClickedRecipe(recipe));

        recyclerViewRecipes.setHasFixedSize(true);
        recyclerViewRecipes.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewRecipes.setAdapter(recyclerViewAdapter);

        presenter.getRecipes();
    }

    @NonNull
    @Override
    public RecipeListContract.Presenter createPresenter() {
        return recipePresenter;
    }

    @Override
    public void displayRecipes(final List<Recipe> recipes) {
        recyclerViewAdapter.addAll(recipes);
    }

    @Override
    public void displayRecipesError() {
        Toast.makeText(this, R.string.error_recipes, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void goToRecipeDetail(final Recipe recipe) {
        final Intent detailIntent = RecipeDetailActivity.newIntent(this, recipe.getId());
        startActivity(detailIntent);
    }
}
