package com.example.cfung.project_3_baking_app;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by cfung on 3/15/18.
 */

public class StepAdapter extends RecyclerView.Adapter <StepAdapter.MyViewHolder>{

    private ArrayList<String> stepsList;

    @Override
    public StepAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(StepAdapter.MyViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return stepsList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        public TextView stepsText;

        public MyViewHolder(View itemView){

            super(itemView);
            //stepsText = (Textview) itemView.findViewById(R.id.recycler_review);
        }

        void bind(int listIndex){
            stepsText.setText("step " + String.valueOf(listIndex) + ":"  );
            //
        }
    }

    public StepAdapter (ArrayList<String> stepsList) { this.stepsList = stepsList;}


}
