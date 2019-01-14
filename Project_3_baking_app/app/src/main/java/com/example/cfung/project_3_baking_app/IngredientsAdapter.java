package com.example.cfung.project_3_baking_app;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;

import java.util.ArrayList;

/**
 * Created by cfung on 3/16/18.
 */

public class IngredientsAdapter extends RecyclerView.Adapter<IngredientsAdapter.MyViewHolder>{

    private ArrayList<Ingredient> ingredientList;
    //private ArrayList<Steps> stepsList;


    public class MyViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.list_item_ingredient_name) TextView ingredientName;
        //@BindView(R.id.recycler_step) TextView stepText;

        public MyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

        }

        void bind(int listIndex){

            ingredientName.setText(ingredientList.get(listIndex).getIngredient() +
                    " (" + ingredientList.get(listIndex).getQuantity() + " " +
                    ingredientList.get(listIndex).getMeasure() + ")");
            //ingredientDescription.setText(ingredientList.get(listIndex)());

            //stepText.setText("Step " + (listIndex + 1) + ": " + stepsList.get(listIndex).getShortDescription());
            Log.v("Myactivity",  "bind() -" + ingredientList.get(listIndex).getIngredient());
        }
    }

    public IngredientsAdapter(ArrayList<Ingredient> ingredientsList, ArrayList<Steps> stepsList) {

        this.ingredientList = ingredientsList;
        //this.stepsList = stepsList;
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
