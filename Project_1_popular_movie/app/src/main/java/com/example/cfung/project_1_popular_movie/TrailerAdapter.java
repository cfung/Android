package com.example.cfung.project_1_popular_movie;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.ArrayList;

/**
 * Created by cfung on 2/12/18.
 */

public class TrailerAdapter extends RecyclerView.Adapter <TrailerAdapter.MyViewHolder>{


    private ArrayList<String> trailerList;

    public class MyViewHolder extends RecyclerView.ViewHolder{

        public ImageView trailerImage;

        public MyViewHolder(View itemView){

            super(itemView);
            trailerImage = (ImageView) itemView.findViewById(R.id.recycler_trailer);
        }

        void bind(int listIndex){

        }
    }


    public TrailerAdapter (ArrayList<String> trailersList){
        this.trailerList = trailersList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {

        Context context = viewGroup.getContext();
        int layoutIdForListItem = R.layout.trailers;
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.trailers , viewGroup, false);
        MyViewHolder viewHolder = new MyViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(TrailerAdapter.MyViewHolder holder, int position) {

        holder.bind(position);

    }

    @Override
    public int getItemCount() {
        return trailerList.size();
    }
}
