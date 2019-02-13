package com.example.cfung.project_3_baking_app.widget;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by cfung on 1/21/19.
 */

public class UpdateBakingService extends IntentService {

    public static String FROM_ACTIVITY_INGREDIENTS_LIST ="FROM_ACTIVITY_INGREDIENTS_LIST";
    private static final String TAG = "UpdateBakingService";

    public UpdateBakingService() {
        super("UpdateBakingService");
    }

    public static void startBakingService(Context context, ArrayList<String> fromActivityIngredientsList) {
        Intent intent = new Intent(context, UpdateBakingService.class);
        intent.putExtra(FROM_ACTIVITY_INGREDIENTS_LIST,fromActivityIngredientsList);
        Log.v(TAG, "first ingrident (fromActivityIngrdientsList) is..." + fromActivityIngredientsList.get(0).toString());
        context.startService(intent);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Log.v(TAG, "calling onHandleIntent");
        if (intent != null) {
            ArrayList<String> fromActivityIngredientsList = intent.getExtras().getStringArrayList(FROM_ACTIVITY_INGREDIENTS_LIST);
            handleActionUpdateBakingWidgets(fromActivityIngredientsList);

        }
    }



    private void handleActionUpdateBakingWidgets(ArrayList<String> fromActivityIngredientsList) {
        Log.v(TAG, "calling handleActionUpdateBakingWidgets");
        Intent intent = new Intent("android.appwidget.action.APPWIDGET_UPDATE2");
        intent.setAction("android.appwidget.action.APPWIDGET_UPDATE2");
        intent.putExtra(FROM_ACTIVITY_INGREDIENTS_LIST,fromActivityIngredientsList);
        Log.v(TAG, "sending broadcast");
        sendBroadcast(intent);
    }
}
