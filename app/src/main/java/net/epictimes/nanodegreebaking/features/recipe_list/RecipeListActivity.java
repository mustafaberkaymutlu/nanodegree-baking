package net.epictimes.nanodegreebaking.features.recipe_list;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RemoteViews;
import android.widget.TextView;
import android.widget.Toast;

import net.epictimes.nanodegreebaking.R;
import net.epictimes.nanodegreebaking.data.model.recipe.Recipe;
import net.epictimes.nanodegreebaking.di.qualifier.IsTablet;
import net.epictimes.nanodegreebaking.di.qualifier.IsWidgetConfiguration;
import net.epictimes.nanodegreebaking.di.qualifier.WidgetSharedPrefs;
import net.epictimes.nanodegreebaking.features.BaseActivity;
import net.epictimes.nanodegreebaking.features.recipe_detail.RecipeDetailActivity;
import net.epictimes.nanodegreebaking.features.widget.BakingRemoteViewsService;
import net.epictimes.nanodegreebaking.features.widget.WidgetSharedPrefConstants;

import java.util.List;

import javax.inject.Inject;

import dagger.android.AndroidInjection;

public class RecipeListActivity extends BaseActivity<RecipeListContract.View, RecipeListContract.Presenter>
        implements RecipeListContract.View {

    private static final int TABLET_SPAN_COUNT = 3;

    @Inject
    RecipeListContract.Presenter recipePresenter;

    @WidgetSharedPrefs
    @Inject
    SharedPreferences sharedPreferences;

    @IsTablet
    @Inject
    boolean isTablet;

    @IsWidgetConfiguration
    @Inject
    boolean isWidgetConfiguration;

    private int appWidgetId;

    private RecipeRecyclerViewAdapter recyclerViewAdapter;

    private TextView textViewWidgetTip;
    private RecyclerView recyclerViewRecipes;

    public static Intent newIntent(@NonNull Context context) {
        return new Intent(context, RecipeListActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AndroidInjection.inject(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_list);

        final Bundle extras = getIntent().getExtras();
        if (extras != null) {
            appWidgetId = extras.getInt(AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID);
        }

        textViewWidgetTip = findViewById(R.id.textViewWidgetTip);
        recyclerViewRecipes = findViewById(R.id.recyclerViewRecipes);

        configureRecyclerView();

        if (isWidgetConfiguration) {
            configureWidgetLayout();
        }

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
        final Intent detailIntent = RecipeDetailActivity.newIntent(this, recipe.getId(), recipe.getName());
        startActivity(detailIntent);
    }

    @Override
    public void selectRecipeForWidget(final Recipe recipe) {
        final AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this);

        final RemoteViews views = new RemoteViews(getPackageName(), R.layout.widget_baking);

        sharedPreferences.edit()
                .putString(WidgetSharedPrefConstants.RECIPE_ID + appWidgetId, recipe.getId())
                .putString(WidgetSharedPrefConstants.RECIPE_NAME + appWidgetId, recipe.getName())
                .apply();

        final Intent remoteViewsServiceIntent = BakingRemoteViewsService.newIntent(this, recipe.getId(), appWidgetId);
        views.setRemoteAdapter(R.id.listViewIngredients, remoteViewsServiceIntent);

        views.setTextViewText(R.id.textViewTitle, recipe.getName());

        appWidgetManager.updateAppWidget(appWidgetId, views);

        endWidgetConfiguration(RESULT_OK);
    }

    @Override
    public void endWidgetConfigurationWithError() {
        endWidgetConfiguration(RESULT_CANCELED);
    }

    private void configureRecyclerView() {
        recyclerViewAdapter = new RecipeRecyclerViewAdapter();
        recyclerViewAdapter.setRecipeClickListener(recipe -> presenter.userClickedRecipe(recipe));

        recyclerViewRecipes.setHasFixedSize(true);
        recyclerViewRecipes.setLayoutManager(getLayout());
        recyclerViewRecipes.setAdapter(recyclerViewAdapter);
    }

    private void configureWidgetLayout() {
        textViewWidgetTip.setVisibility(View.VISIBLE);
    }

    @NonNull
    private LinearLayoutManager getLayout() {
        if (isTablet) {
            return new GridLayoutManager(this, TABLET_SPAN_COUNT);
        } else {
            return new LinearLayoutManager(this);
        }
    }

    private void endWidgetConfiguration(final int resultCode) {
        final Intent resultValue = new Intent();
        resultValue.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
        setResult(resultCode, resultValue);
        finish();
    }
}
