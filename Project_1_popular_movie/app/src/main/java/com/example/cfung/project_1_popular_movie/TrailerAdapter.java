package com.example.cfung.project_1_popular_movie;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * Created by cfung on 2/12/18.
 */

public class TrailerAdapter extends RecyclerView.Adapter <TrailerAdapter.MyViewHolder>{

    private static final String TAG = "TrailerAdapter";

    private ArrayList<String> trailerList;
    private Context context;
    @BindView(R.id.recycler_trailer_btn) ImageButton trailerImageBtn;

    public TrailerAdapter (ArrayList<String> trailersList, Context context){

        this.trailerList = trailersList;
        this.context = context;

    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        //public ImageButton trailerImageBtn;
        //@BindView(R.id.recycler_trailer_btn) ImageButton trailerImageBtn;

        public MyViewHolder(View itemView){

            super(itemView);
            //trailerImageBtn = (ImageButton) itemView.findViewById(R.id.recycler_trailer_btn);

        }

        void bind(final int listIndex){

            Log.v("TrailerAdapter", "inside bind() - what is trailerList: "+trailerList.get(listIndex).toString());
            trailerImageBtn.setOnClickListener(new View.OnClickListener(){


                @Override
                public void onClick(View view) {
                    String youtubePath = "https://www.youtube.com/watch?v=" + trailerList.get(listIndex).toString();
                    Log.v(TAG, "what is youtube path.." + youtubePath);
                    Uri uri = Uri.parse(youtubePath);
                    uri = Uri.parse("vnd.youtube:" + uri.getQueryParameter("v"));
                    Intent movieIntent = new Intent (Intent.ACTION_VIEW, uri);
                    context.startActivity(movieIntent);
                    //Toast.makeText(DetailActivity.this,
                    //        "ImageButton is clicked!", Toast.LENGTH_SHORT).show();
                    //Log.v(TAG, "what is trailerPath: "+trailerPath);
                }
            });
        }
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
