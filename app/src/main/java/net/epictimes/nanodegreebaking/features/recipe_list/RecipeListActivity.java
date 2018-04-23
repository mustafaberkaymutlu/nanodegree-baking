package net.epictimes.nanodegreebaking.features.recipe_list;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import net.epictimes.nanodegreebaking.R;
import net.epictimes.nanodegreebaking.data.model.Recipe;
import net.epictimes.nanodegreebaking.features.BaseActivity;

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
        setContentView(R.layout.activity_main);

        final RecyclerView recyclerViewRecipes = findViewById(R.id.recyclerViewRecipes);

        recyclerViewAdapter = new RecipeRecyclerViewAdapter();

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
}
