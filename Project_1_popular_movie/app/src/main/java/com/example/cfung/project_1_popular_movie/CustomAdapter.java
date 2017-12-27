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

/**
 * Created by cfung on 10/4/17.
 */
//public class CustomAdapter extends ArrayAdapter<MovieModel>{
public class CustomAdapter extends ArrayAdapter<MovieModel>{

    private static final String TAG = "MyActivity";

    String BASE_POSTER_URL= "http://image.tmdb.org/t/p/w185";

    ArrayList<MovieModel> AllMovies = new ArrayList<MovieModel>();



    //TODO 1:  Do i need to override this default constructor?
    public CustomAdapter(Activity context, int resources, ArrayList<MovieModel> movies){
        super(context, resources, movies);
        this.AllMovies = movies;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        Log.v(TAG, "what is getView position"+position);
        MovieModel mMovies = getItem(position);

        /*

        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE)
        inflate(R.layout.movie_item, null);

         */

        if (convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item_flavor, parent, false);
        }

        ImageView imageView = (ImageView) convertView.findViewById(R.id.list_item_icon);
        Picasso.with(getContext()).setLoggingEnabled(true);
        //https://api.themoviedb.org/3/movie/550?api_key=bad34c8d38b0750ab6bef23cb64440ba

        /*String url = "https://api.themoviedb.org/3/movie/popular?api_key=bad34c8d38b0750ab6bef23cb64440ba";
        new MovieQueryTask().execute(url);*/
        //makeServiceCall(url);
        /*try {

            //In order to request popular movies you will want to request data from the /movie/popular and /movie/top_rated endpoints.
            // An API Key is required.

            URL url = new URL("https://api.themoviedb.org/3/movie/popular?api_key=bad34c8d38b0750ab6bef23cb64440ba");
            new MovieQueryTask().execute(url);
            //JSONParser jsonParser = new JSONParser();
            Log.i("URL is..", url.toString());

            //final JSONObject json = jsonParser.makeHttpRequest(url_kbj + "/" + idkbj + "/", "GET", params1);
            //Log.i("jsonda =", String.valueOf(json));
            //final JSONObject data = json.getJSONObject("data");

            //Object obj = new JSONParser().parse(new FileReader("https://api.themoviedb.org/3/movie/550?api_key=bad34c8d38b0750ab6bef23cb64440ba"));

        } catch(MalformedURLException e){
            e.printStackTrace();
        } catch (IOException e){
            e.printStackTrace();
        } */

        // base URL:  http://image.tmdb.org/t/p/
        //For most phones we recommend using “w185”.
        // /9E2y5Q7WlCVNEhP5GiVTjhEhx1o.jpg (poster path)

        /*for (int i=0; i.size();i++){

            Log.v(TAG, "movie in getView.."+movies.get(i).getMovieName());
        }*/

        //crashing since AllMovies is Null
        Log.v(TAG, "mMovies link is..."+mMovies.getMovieLink());
        String moviePostPath1 = "http://image.tmdb.org/t/p/w185/"+mMovies.getMovieLink();
        Log.v(TAG, "what is moviePostPath1.."+moviePostPath1);
        Picasso.with(getContext())
                .load(moviePostPath1)
                .placeholder(R.drawable.placeholder)
                .into(imageView);
        //imageView.setImageResource(R.drawable.ic_launcher);
        /*Picasso.with(context)
                .load("http://i.imgur.com/DvpvklR.png")
                .into(iconView);*/

        TextView movieNameView = (TextView) convertView.findViewById(R.id.list_item_movie_name);
        movieNameView.setText(AllMovies.get(position).getMovieName());
        //movieNameView.setText(movies.getMovieName());

        /*ImageView imageView2 = (ImageView) convertView.findViewById(R.id.list_item_icon2);

        Picasso.with(getContext())
                .load("http://image.tmdb.org/t/p/w185/qMGeFRYrV7yBNQA7chN0rq8BpwX.jpg")
                .placeholder(R.drawable.placeholder)
                .into(imageView2);

        TextView movieNameView2 = (TextView) convertView.findViewById(R.id.list_item_movie_name2);
        //movieNameView2.setText("movie 2");

        ImageView imageView3 = (ImageView) convertView.findViewById(R.id.list_item_icon3);

        Picasso.with(getContext())
                .load("http://image.tmdb.org/t/p/w185/wJB3L5exk6yVE0uS4OetQ6g19J3.jpg")
                .placeholder(R.drawable.placeholder)
                .into(imageView3);

        TextView movieNameView3 = (TextView) convertView.findViewById(R.id.list_item_movie_name3);
        //movieNameView3.setText("movie 3");*/

        // TODO 3:  return rootView or convertView???
        return convertView;
    }
}
