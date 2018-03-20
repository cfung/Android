package com.example.cfung.project_3_baking_app;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by cfung on 3/16/18.
 */

public class IngredientsAdapter extends RecyclerView.Adapter<IngredientsAdapter.MyViewHolder>{

    private ArrayList<String> ingredientList;

    public class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView ingredientText;
        //public TextView getIngredientTextIndex;

        public MyViewHolder(View itemView) {
            super(itemView);
            ingredientText = (TextView) itemView.findViewById(R.id.list_item_ingredient_name);
            //ingredientTextIndex = (TextView) itemView.findViewById(R.id.);

        }

        void bind(int listIndex){

            ingredientText.setText("ingredient " + listIndex + ": " + ingredientList.get(listIndex).toString());
        }
    }

    public IngredientsAdapter(ArrayList<String> ingredientsList) {this.ingredientList = ingredientsList;}

    @Override
    public IngredientsAdapter.MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {

        Log.v("MyActivity", "onCreateViewHolder");
        Context context = viewGroup.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.ingredients, viewGroup, false);
        MyViewHolder viewHolder = new MyViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(IngredientsAdapter.MyViewHolder holder, int position) {
        Log.v("MyActivity", "IngredientsAdapter - onBindViewHolder..");
        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        return ingredientList.size();
    }
}
