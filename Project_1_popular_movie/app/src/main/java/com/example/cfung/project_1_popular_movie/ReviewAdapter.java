package com.example.cfung.project_1_popular_movie;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * Created by cfung on 2/10/18.
 */

public class ReviewAdapter extends RecyclerView.Adapter <ReviewAdapter.MyViewHolder> {

    private ArrayList<String> reviewsList;

    public class MyViewHolder extends RecyclerView.ViewHolder {

        //public TextView reviewsText;
        //public TextView reviewsTextIndex;
        @BindView(R.id.recycler_review) TextView reviewsText;
        @BindView(R.id.recycler_review_index) TextView reviewsTextIndex;

        public MyViewHolder(View itemView) {
            super(itemView);
            //reviewsText = (TextView) itemView.findViewById(R.id.recycler_review);
            //reviewsTextIndex = (TextView) itemView.findViewById(R.id.recycler_review_index);
        }

        void bind(int listIndex) {
            reviewsTextIndex.setText("review " + (String.valueOf(listIndex)) + ":");
            reviewsText.setText(reviewsList.get(listIndex).toString());
        }
    }

    public ReviewAdapter (ArrayList<String> reviewsList){
        this.reviewsList = reviewsList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {

        Context context = viewGroup.getContext();
        int layoutIdForListItem = R.layout.reviews;
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.reviews, viewGroup, false);
        MyViewHolder viewHolder = new MyViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        Log.v("MyActivity", "what is holder: "+holder.reviewsText.getText());
        Log.v("MyActivity", "what is reviewsList <position>" + reviewsList.get(position).toString());
        //Log.v("MyActivity", "what is reviewsText"+holder.reviewsText.toString());
        //String review = reviewsList.get(position);
        //holder.reviewsText.setText(review.toString());
        holder.bind(position);

    }


    @Override
    public int getItemCount() {
        return reviewsList.size();
    }
}
