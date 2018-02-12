package com.example.cfung.project_1_popular_movie;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by cfung on 2/10/18.
 */

public class RecyclerAdapter extends RecyclerView.Adapter <RecyclerAdapter.MyViewHolder> {

    private ArrayList<String> reviewsList;

    public class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView reviewsText;

        public MyViewHolder(View itemView) {
            super(itemView);
            reviewsText = (TextView) itemView.findViewById(R.id.recycler_view_layout);
        }
    }

    public RecyclerAdapter (ArrayList<String> reviewsList){
        this.reviewsList = reviewsList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.reviews, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        String review = reviewsList.get(position);
        holder.reviewsText.setText(review.toString());
    }


    @Override
    public int getItemCount() {
        return reviewsList.size();
    }
}
