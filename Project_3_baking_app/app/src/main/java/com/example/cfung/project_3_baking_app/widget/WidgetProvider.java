package com.example.cfung.project_3_baking_app.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.RemoteViews;

import com.example.cfung.project_3_baking_app.DetailActivity;
import com.example.cfung.project_3_baking_app.R;

import java.util.ArrayList;

import static com.example.cfung.project_3_baking_app.widget.UpdateBakingService.FROM_ACTIVITY_INGREDIENTS_LIST;


public class WidgetProvider extends AppWidgetProvider {


    public static String REMOTEVIEW_INGREDIENT_LIST="REMOTEVIEW_INGREDIENT_LIST";
    public static String REMOTEVIEW_BUNDLE="REMOTEVIEW_BUNDLE";
    private static final String TAG = "WidgetProvider";

    static ArrayList<String> ingredientsList = new ArrayList<>();

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager, int appWidgetId) {

        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget_view);

        //call activity when widget is clicked, but resume activity from stack so you do not pass intent.extras afresh
        Intent appIntent = new Intent(context, DetailActivity.class);
        appIntent.addCategory(Intent.ACTION_MAIN);
        appIntent.addCategory(Intent.CATEGORY_LAUNCHER);
        appIntent.addFlags(Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT|Intent.FLAG_ACTIVITY_SINGLE_TOP);
        PendingIntent appPendingIntent = PendingIntent.getActivity(context, 0, appIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        views.setPendingIntentTemplate(R.id.widget_grid_view, appPendingIntent);

        // Set the WidgetService intent to act as the adapter for the GridView
        Intent intent = new Intent(context, WidgetService.class);
        views.setRemoteAdapter(R.id.widget_grid_view, intent);

        appWidgetManager.updateAppWidget(appWidgetId, views);

    }

    public static void updateBakingWidgets(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        Log.v(TAG, "updateBakingWidgets...");
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {

        Log.v(TAG, "inside onUpdate (widgets)");

        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    @Override
    public void onReceive(Context context, Intent intent) {

        super.onReceive(context, intent);
        Log.v(TAG, "inside onReceive (widgets)");
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
        int[] appWidgetIds = appWidgetManager.getAppWidgetIds(new ComponentName(context, WidgetProvider.class));

        final String action = intent.getAction();
        Log.v(TAG, "what is action..: " + action);

        if (action.equals("android.appwidget.action.APPWIDGET_UPDATE2")) {
        //if (action.equals("android.appwidget.action.APPWIDGET_UPDATE")) {
            Log.v(TAG, "action == APPWIDGET_UPDATE2");

            //AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
            /*ComponentName thisWidget = new ComponentName(context.getApplicationContext(), BakingWidgetProvider.class);
            int[] appWidgetIds = appWidgetManager.getAppWidgetIds(thisWidget);

            RemoteViews remoteViews = getRecipesFromGridView(context);

            appWidgetManager.updateAppWidget(appWidgetIds, remoteViews);
            appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetIds, R.id.gvRecipeWidget);
            onUpdate(context, appWidgetManager, appWidgetIds);*/
            /***************************************************************/
            Log.v(TAG, "what is intent..." + intent.getExtras());
            ingredientsList = intent.getExtras().getStringArrayList(FROM_ACTIVITY_INGREDIENTS_LIST);
            appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetIds, R.id.widget_grid_view);
            //Now update all widgets
            Log.v(TAG, "about to update widgets");
            WidgetProvider.updateBakingWidgets(context, appWidgetManager, appWidgetIds);

        }
    }


}
