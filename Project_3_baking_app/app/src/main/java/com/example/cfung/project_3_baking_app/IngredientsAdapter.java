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

    private ArrayList<Ingredient> ingredientList;
    private ArrayList<Steps> stepsList;

    public class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView ingredientText;
        public TextView stepText;
        //public TextView getIngredientTextIndex;

        public MyViewHolder(View itemView) {
            super(itemView);
            ingredientText = (TextView) itemView.findViewById(R.id.list_item_ingredient_name);
            stepText = (TextView) itemView.findViewById(R.id.list_item_step_name);
            //ingredientTextIndex = (TextView) itemView.findViewById(R.id.);
            /*for (int x = 0; x < ingredientList.size(); x++){
                ingredientText.setText("ingredient " + (x + 1)  + ":: " + ingredientList.get(x).getIngredient());
            }*/

        }

        void bind(int listIndex){

            ingredientText.setText("ingredient " + (listIndex + 1)  + ": " + ingredientList.get(listIndex).getIngredient());
            stepText.setText("Step " + (listIndex + 1) + ": " + stepsList.get(listIndex).getShortDescription());
            Log.v("Myactivity",  "bind() -" + ingredientList.get(listIndex).getIngredient());
        }
    }

    public IngredientsAdapter(ArrayList<Ingredient> ingredientsList, ArrayList<Steps> stepsList) {

        this.ingredientList = ingredientsList;
        this.stepsList = stepsList;
    }

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
