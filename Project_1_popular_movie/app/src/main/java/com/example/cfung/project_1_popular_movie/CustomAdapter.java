package com.example.cfung.project_1_popular_movie;

import android.app.Activity;
import android.content.Context;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.squareup.picasso.Picasso;


import java.util.List;

/**
 * Created by cfung on 10/4/17.
 */
//public class CustomAdapter extends ArrayAdapter<MovieModel>{
public class CustomAdapter extends ArrayAdapter<MovieModel>{

    public CustomAdapter(Activity context, List<MovieModel> androidFlavors){
        super(context, 0, androidFlavors);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){

        MovieModel movies = getItem(position);

        if (convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item_flavor, parent, false);
        }

        ImageView imageView = (ImageView) convertView.findViewById(R.id.list_item_icon);
        Picasso.with(getContext()).setLoggingEnabled(true);
        Picasso.with(getContext())
                .load("http://image.tmdb.org/t/p/w185/nBNZadXqJSdt05SHLqgT0HuC5Gm.jpg")
                .placeholder(R.drawable.placeholder)
                .into(imageView);
        //imageView.setImageResource(R.drawable.ic_launcher);
        /*Picasso.with(context)
                .load("http://i.imgur.com/DvpvklR.png")
                .into(iconView);*/

        TextView versionNameView = (TextView) convertView.findViewById(R.id.list_item_version_name);
        versionNameView.setText(movies.getMovieLink());

        return convertView;
    }
}
