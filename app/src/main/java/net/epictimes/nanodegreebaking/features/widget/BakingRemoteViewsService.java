package net.epictimes.nanodegreebaking.features.widget;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import net.epictimes.nanodegreebaking.R;
import net.epictimes.nanodegreebaking.data.RecipeDataSource;
import net.epictimes.nanodegreebaking.data.model.ingredient.Ingredient;
import net.epictimes.nanodegreebaking.data.model.recipe.Recipe;
import net.epictimes.nanodegreebaking.di.qualifier.Repository;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import dagger.android.AndroidInjection;
import io.reactivex.disposables.Disposable;
import timber.log.Timber;

/**
 * Created by Mustafa Berkay Mutlu on 17.05.18.
 */
public class BakingRemoteViewsService extends RemoteViewsService {
    private static final String KEY_RECIPE_ID = "recipe_id";

    @Repository
    @Inject
    RecipeDataSource repository;

    private String recipeId;

    public static Intent newIntent(@NonNull Context context, @NonNull String recipeId, int appWidgetId) {
        final Intent intent = new Intent(context, BakingRemoteViewsService.class);
        intent.putExtra(KEY_RECIPE_ID, recipeId);
        intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
        intent.setData(Uri.parse(intent.toUri(Intent.URI_INTENT_SCHEME)));
        return intent;
    }

    @Override
    public void onCreate() {
        AndroidInjection.inject(this);
        super.onCreate();
    }

    @Override
    public RemoteViewsFactory onGetViewFactory(final Intent intent) {
        recipeId = intent.getStringExtra(KEY_RECIPE_ID);

        return new MyRemoteViewsFactory();
    }

    private class MyRemoteViewsFactory implements RemoteViewsFactory {
        private final List<Ingredient> ingredientList = new ArrayList<>();
        private Disposable repositoryDisposable;

        @Override
        public void onCreate() {
            // no-op
        }

        @Override
        public void onDataSetChanged() {
            repositoryDisposable = repository.getRecipe(recipeId)
                    .map(Recipe::getIngredients)
                    .subscribe(this::addItems, Timber::e);
        }

        @Override
        public void onDestroy() {
            if (repositoryDisposable!= null && !repositoryDisposable.isDisposed()) {
                repositoryDisposable.dispose();
            }
        }

        @Override
        public int getCount() {
            return ingredientList.size();
        }

        @Override
        public RemoteViews getViewAt(final int position) {
            final Ingredient ingredient = ingredientList.get(position);

            final RemoteViews views = new RemoteViews(getPackageName(), R.layout.item_widget_ingredient);

            views.setTextViewText(R.id.textViewIngredient, getReadableIngredient(ingredient));

            return views;
        }

        @Override
        public RemoteViews getLoadingView() {
            return null;
        }

        @Override
        public int getViewTypeCount() {
            return 1;
        }

        @Override
        public long getItemId(final int position) {
            return position;
        }

        @Override
        public boolean hasStableIds() {
            return false;
        }

        private void addItems(List<Ingredient> ingredients) {
            ingredientList.clear();
            ingredientList.addAll(ingredients);
        }

        private CharSequence getReadableIngredient(Ingredient ingredient) {
            return ingredient.getIngredientName() + ", " + ingredient.getQuantity() + " " + ingredient.getMeasure();
        }
    }
}
