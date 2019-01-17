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
import java.util.List;

/**
 * Created by cfung on 3/16/18.
 */

public class IngredientsAdapter extends RecyclerView.Adapter<IngredientsAdapter.MyViewHolder>{

    //private ArrayList<Ingredient> ingredientList;
    List<Steps> stepsList;
    private String recipeName;

    final private ListItemClickListener listItemClickListener;

    private static final String TAG = "IngredientsAdapter";

    public interface ListItemClickListener {
        void onListItemClick(List<Steps> steps, int index, String recipeName);
    }

    public IngredientsAdapter(ListItemClickListener listItemClickListener) {

        this.listItemClickListener = listItemClickListener;
        //this.stepsList = stepsList;
        //listItemClickListener = null;
    }

    public void setIngredientsAdapterData(List<RecipeModel> recipeModels, Context context){
        Log.v(TAG, "setIngredientsAdapterData() starting..");
        stepsList = recipeModels.get(0).getSteps();
        recipeName = recipeModels.get(0).getRecipeName();
        notifyDataSetChanged();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {

        Log.v(TAG, "IngredientsAdapter-onCreateViewHolder");
        Context context = viewGroup.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.recipe_detail_cardview_items, viewGroup, false);
        MyViewHolder viewHolder = new MyViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(IngredientsAdapter.MyViewHolder holder, int position) {
        Log.v(TAG, "IngredientsAdapter - onBindViewHolder..");
        Log.v(TAG, "stepsList getID(): " + stepsList.get(position).getId());
        Log.v(TAG, "stepsList getShortDescription: " + stepsList.get(position).getShortDescription());
        holder.tv_shortDescription.setText(stepsList.get(position).getId() + ". " + stepsList.get(position).getShortDescription());
        //holder.bind(position);

    }

    @Override
    public int getItemCount() {

        if (stepsList != null) {
            Log.v(TAG, "getItemCount(): " + stepsList.size());
            return stepsList.size();
        } else {
            return 0;
        }

    }

    class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        //@BindView(R.id.list_item_ingredient_name) TextView ingredientName;
        //@BindView(R.id.recycler_step) TextView stepText;
        //@BindView(R.id.short_description) TextView tv_shortDescription;
        TextView tv_shortDescription;

        public MyViewHolder(View itemView) {
            super(itemView);
            //ButterKnife.bind(this, itemView);
            tv_shortDescription = (TextView) itemView.findViewById(R.id.short_description);

            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View view) {
            int pos = getAdapterPosition();
            listItemClickListener.onListItemClick(stepsList, pos, recipeName);
        }

        /*void bind(int listIndex){

            ingredientName.setText(ingredientList.get(listIndex).getIngredient() +
                    " (" + ingredientList.get(listIndex).getQuantity() + " " +
                    ingredientList.get(listIndex).getMeasure() + ")");
            //ingredientDescription.setText(ingredientList.get(listIndex)());

            //stepText.setText("Step " + (listIndex + 1) + ": " + stepsList.get(listIndex).getShortDescription());
            Log.v("Myactivity",  "bind() -" + ingredientList.get(listIndex).getIngredient());
        }*/
    }

}
