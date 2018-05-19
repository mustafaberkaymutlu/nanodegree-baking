package net.epictimes.nanodegreebaking.features.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.widget.RemoteViews;

import net.epictimes.nanodegreebaking.R;
import net.epictimes.nanodegreebaking.di.qualifier.WidgetSharedPrefs;
import net.epictimes.nanodegreebaking.features.recipe_list.RecipeListActivity;

import javax.inject.Inject;

import dagger.android.AndroidInjection;

public class BakingWidgetProvider extends AppWidgetProvider {

    @WidgetSharedPrefs
    @Inject
    SharedPreferences sharedPreferences;

    @Override
    public void onReceive(Context context, Intent intent) {
        AndroidInjection.inject(this, context);
        super.onReceive(context, intent);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    @Override
    public void onDeleted(Context context, int[] appWidgetIds) {
        super.onDeleted(context, appWidgetIds);

        for (int appWidgetId : appWidgetIds) {
            sharedPreferences.edit()
                    .remove(WidgetSharedPrefConstants.RECIPE_ID + appWidgetId)
                    .remove(WidgetSharedPrefConstants.RECIPE_NAME + appWidgetId)
                    .apply();
        }
    }

    void updateAppWidget(Context context,
                         AppWidgetManager appWidgetManager,
                         int appWidgetId) {
        final RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget_baking);

        final String recipeId = sharedPreferences.getString(WidgetSharedPrefConstants.RECIPE_ID + appWidgetId, null);
        final String recipeName = sharedPreferences.getString(WidgetSharedPrefConstants.RECIPE_NAME + appWidgetId, null);

        final Intent recipeListIntent = RecipeListActivity.newIntent(context);
        final PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, recipeListIntent, 0);

        views.setOnClickPendingIntent(R.id.textViewTitle, pendingIntent);
        views.setTextViewText(R.id.textViewTitle, recipeName);

        final Intent bakingRemoteViewsServiceIntent = BakingRemoteViewsService.newIntent(context, recipeId, appWidgetId);
        views.setRemoteAdapter(R.id.listViewIngredients, bakingRemoteViewsServiceIntent);

        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    /**
     * Helper method for testing widget updates
     */
    public static void updateAllBakingWidgets(@NonNull Context context) {
        final Intent intent = new Intent(context, BakingWidgetProvider.class);
        intent.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
        final int[] ids = AppWidgetManager.getInstance(context)
                .getAppWidgetIds(new ComponentName(context, BakingWidgetProvider.class));
        intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, ids);
        context.sendBroadcast(intent);
    }
}

