package com.example.cfung.project_3_baking_app;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;

import java.util.ArrayList;

/**
 * Created by cfung on 3/15/18.
 */

public class StepAdapter extends RecyclerView.Adapter <StepAdapter.MyViewHolder>{

    private ArrayList<Steps> stepList;
    //@BindView(R.id.list_item_ingredient_description) TextView ingredientSteps;

    @Override
    public StepAdapter.MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {

        Context context = viewGroup.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.ingredients, viewGroup, false);
        MyViewHolder viewHolder = new MyViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(StepAdapter.MyViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return stepList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.list_item_ingredient_description) TextView ingredientDescription;

        public TextView stepText;

        public MyViewHolder(View itemView){

            super(itemView);
            ButterKnife.bind(this, itemView);
            //stepsText = (Textview) itemView.findViewById(R.id.recycler_review);
        }

        void bind(int listIndex){

            ingredientDescription.setText("Step " + listIndex + ": " + stepList.get(listIndex).getShortDescription());
            //
        }
    }

    public StepAdapter (ArrayList<Steps> stepsList) { this.stepList = stepsList;}


}
