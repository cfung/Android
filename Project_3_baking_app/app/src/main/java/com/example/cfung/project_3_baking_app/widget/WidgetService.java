package com.example.cfung.project_3_baking_app.widget;

import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.example.cfung.project_3_baking_app.R;

import java.util.List;

/**
 * Created by cfung on 1/21/19.
 */

public class WidgetService extends RemoteViewsService{

    List<String> ingredientsList;

    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new RemoteViewsFactory(this.getApplicationContext(), intent);
    }

    class RemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory {

        Context context = null;

        public RemoteViewsFactory(Context applicationContext, Intent intent) {
            context = applicationContext;
        }


        @Override
        public void onCreate() {

        }

        @Override
        public void onDataSetChanged() {

        }

        @Override
        public void onDestroy() {

        }

        @Override
        public int getCount() {
            return ingredientsList.size();
        }

        @Override
        public RemoteViews getViewAt(int pos) {

            RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget_view_item);

            views.setTextViewText(R.id.widget_textview, ingredientsList.get(pos));

            Intent intent = new Intent();
            views.setOnClickFillInIntent(R.id.widget_textview, intent);

            return views;
        }

        @Override
        public RemoteViews getLoadingView() {
            return null;
        }

        @Override
        public int getViewTypeCount() {
            return 0;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public boolean hasStableIds() {
            return false;
        }
    }
}
