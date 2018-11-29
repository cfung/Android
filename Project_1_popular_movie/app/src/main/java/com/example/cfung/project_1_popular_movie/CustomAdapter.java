package com.example.cfung.project_1_popular_movie;

import android.app.Activity;
import android.media.Image;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONArray;
import org.json.simple.parser.JSONParser;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * CustomAdapter which holds an ImageView and TextView
 */
//public class CustomAdapter extends ArrayAdapter<MovieModel>{
public class CustomAdapter extends ArrayAdapter<MovieModel>{

    private static final String TAG = "MyActivity";

    String BASE_POSTER_URL= "http://image.tmdb.org/t/p/w185";

    private List<MovieModel> mMovies;

    ArrayList<MovieModel> AllMovies = new ArrayList<MovieModel>();
    @BindView(R.id.list_item_icon) ImageView imageView;
    @BindView(R.id.list_item_movie_name) TextView movieNameView;

    //completed 1: override this default constructor?
    public CustomAdapter(Activity context, int resources, ArrayList<MovieModel> movies){
        super(context, resources, movies);
        this.AllMovies = movies;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){

        MovieModel mMovies = getItem(position);

        if (convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item_flavor, parent, false);
        }
        ButterKnife.bind(this, convertView);

        //ImageView imageView = (ImageView) convertView.findViewById(R.id.list_item_icon);


        Picasso.with(getContext()).setLoggingEnabled(true);

        String moviePostPath1 = "http://image.tmdb.org/t/p/w185/"+mMovies.getMovieLink();
        Picasso.with(getContext())
                .load(moviePostPath1)
                .placeholder(R.drawable.placeholder)
                .into(imageView);

        //TextView movieNameView = (TextView) convertView.findViewById(R.id.list_item_movie_name);
        movieNameView.setText(AllMovies.get(position).getMovieName());

        return convertView;
    }
    
}
