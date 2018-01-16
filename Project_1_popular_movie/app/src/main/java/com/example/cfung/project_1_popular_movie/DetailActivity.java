package com.example.cfung.project_1_popular_movie;

import android.app.LoaderManager;
import android.content.AsyncTaskLoader;
import android.content.ContentValues;
import android.content.Intent;
import android.content.Loader;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Movie;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.util.Log;
import android.widget.Toast;

import com.example.cfung.project_1_popular_movie.data.MovieContract;
import com.example.cfung.project_1_popular_movie.data.MovieDBHelper;
import com.squareup.picasso.Picasso;
import android.net.Uri;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import javax.net.ssl.HttpsURLConnection;

/**
 * Created by cfung on 12/27/17.
 * This detail view should show the following info:
 *    -original title (original_title)
 *    -movie poster image thumbnail (poster_path)
 *    -A plot synopsis (called overview in the api)
 *    -user rating (called vote_average in the api)
 *    -release date
 */

public class DetailActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<ArrayList<String>>{

    private static final String TAG = "MyActivity";
    private String trailerPath = null;
    private String trailerKey = null;
    private String reviewPath = null;

    private static final int TRAILER_LOADER = 1;
    private static final int REVIEW_LOADER = 2;

    private SQLiteDatabase mDB;



    private String convertStreamToString(InputStream is){
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();
        String line;
        try {
            while ((line = reader.readLine()) != null){
                sb.append(line).append('\n');
            }
        } catch (IOException e){
            e.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException e){
                e.printStackTrace();
            }
        }

        return sb.toString();
    }

    public String makeServiceCall(String reqUrl){
        String response = null;

        try{
            URL url = new URL (reqUrl);
            HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            InputStream in = new BufferedInputStream(conn.getInputStream());
            response = convertStreamToString(in);

        } catch (IOException e){
            e.printStackTrace();
        }
        return response;
    }

    @Override
    public Loader<ArrayList<String>> onCreateLoader(int i, final Bundle bundle) {
        return  new AsyncTaskLoader<ArrayList<String>>(this) {
            @Override
            public ArrayList<String> loadInBackground() {
                Log.v(TAG, "inside loadInBackground..");
                String mID = null;
                ArrayList<String> reviewsList = new ArrayList<String>();

                String myStr = "Bundle{";
                for (String key : bundle.keySet()){
                    myStr += " " + key + " => " + bundle.get(key) + ";";
                    if (key.equals("id")){
                        mID = bundle.get(key).toString();
                    }
                }
                myStr += " }Bundle";
                Log.v(TAG, "bundle contains..."+ myStr);
                String responseKey = null;
                String resultKey = null;
                try{
                    Log.v(TAG, "what is bundle.get.." + mID);
                    String reviewURL = "https://api.themoviedb.org/3/movie/" + bundle.get("id") + "/reviews?api_key=bad34c8d38b0750ab6bef23cb64440ba";
                    String resp = makeServiceCall(reviewURL);
                    Log.v(TAG, "what is resp (URL)..."+resp);
                    //Log.v(TAG, "what is url in loadInBAckground-DetailActivity.."+urls[0].toString());

                    JSONObject results = new JSONObject(resp);

                    JSONArray detailResults = results.getJSONArray("results");
                    for (int i=0; i<detailResults.length(); i++){
                        JSONObject jsonobject = detailResults.getJSONObject(i);

                        String movieReview = jsonobject.getString("content");
                        Log.v(TAG, "what is movieReview: "+movieReview);
                        //resultKey = movieKey;
                        reviewsList.add(movieReview);
                        //String resp_movieKey = makeServiceCall("");
                    }

                } catch (JSONException e){
                    e.printStackTrace();
                }


                //Log.v(TAG, "what is resultslist in trailer key.."+resultKey.toString());
                //return resultKey;
                return reviewsList;
            }

            @Override
            public void onStartLoading(){
                if (bundle == null){
                    return;
                }
            }
        };
    }

    @Override
    public void onLoadFinished(Loader<ArrayList<String>> loader, ArrayList<String> strings) {

        TextView movieReviews = (TextView)findViewById(R.id.detail_review);
        Log.v(TAG, "onLoadFinished..."+strings);
        for (String review: strings) {
            movieReviews.setText("Reviews: "+review);
        }

    }

    @Override
    public void onLoaderReset(Loader<ArrayList<String>> loader) {

    }

    /*@Override
    public void onLoadFinished(Loader<MovieModel> loader, MovieModel movieModel) {

    }*/

    /*@Override
    public void onLoaderReset(Loader<MovieModel> loader) {

    }*/

    /*public class DetailQueryTask extends AsyncTask<String, Void, String>{

        @Override
        protected String doInBackground(String... urls){

            String responseKey = null;
            String resultKey = null;
            try{
                String resp = makeServiceCall(urls[0]);
                Log.v(TAG, "what is url in doInBAckground-DetailActivity.."+urls[0].toString());

                JSONObject results = new JSONObject(resp);

                JSONArray detailResults = results.getJSONArray("results");
                for (int i=0; i<detailResults.length(); i++){
                    JSONObject jsonobject = detailResults.getJSONObject(i);
                    // TODO:  1 case is "key", 1 case is "content"
                    String movieKey = jsonobject.getString("key");
                    resultKey = movieKey;
                    //String resp_movieKey = makeServiceCall("");
                }

            } catch (JSONException e){
                e.printStackTrace();
            }


            //Log.v(TAG, "what is resultslist in trailer key.."+resultKey.toString());
            return resultKey;
        }

        @Override
        protected void onPostExecute(String result){
            super.onPostExecute(result);
            Log.v(TAG, "result in onPostExecute..."+result);
            if(result != null){

                trailerKey = result;

            }
        }

    }*/

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail);

        ImageView imageView = (ImageView)findViewById(R.id.detail_image);
        ImageButton trailerBtn = (ImageButton)findViewById(R.id.detail_trailer);
        final TextView movieTitle = (TextView)findViewById(R.id.detail_name);
        TextView movieSynopsis = (TextView)findViewById(R.id.detail_overview);
        TextView movieRating = (TextView)findViewById(R.id.detail_vote);
        TextView movieReleaseDate = (TextView)findViewById(R.id.detail_date);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);

        Intent movieIntent = getIntent();
        final Bundle movieBundle = movieIntent.getExtras();

        MovieDBHelper dbHelper = new MovieDBHelper(this);
        mDB = dbHelper.getWritableDatabase();

        if(movieBundle != null)
        {
            String movieID = (String) movieBundle.get("id");
            String textLink = (String) movieBundle.get("link");
            String textTitle =(String) movieBundle.get("name");
            movieTitle.setText(textTitle);
            String textSynopsis =(String) movieBundle.get("overview");
            movieSynopsis.setText("Synopsis: "+textSynopsis);
            String textRating =(String) movieBundle.get("vote_average");
            movieRating.setText("Rating: "+textRating);
            String textDate =(String) movieBundle.get("release_date");
            movieReleaseDate.setText("Release Date: "+textDate);


            String moviePath = "http://image.tmdb.org/t/p/w185/" + textLink;
            Log.v(TAG, "what is moviePath in detail.."+moviePath);
            Log.v(TAG, "what is movieID in detail.."+movieID);
            trailerPath = "https://api.themoviedb.org/3/movie/" + movieID + "/videos?api_key=bad34c8d38b0750ab6bef23cb64440ba";
            reviewPath = "https://api.themoviedb.org/3/movie/" + movieID + "/reviews?api_key=bad34c8d38b0750ab6bef23cb64440ba";

            Log.v(TAG, "what is reviewPath.."+reviewPath);

            //movieReviews.setText("Reviews: "+ reviewPath);

            Picasso.with(getApplicationContext())
                    .load(moviePath)
                    .placeholder(R.drawable.placeholder)
                    .into(imageView);

            //new DetailQueryTask().execute(trailerPath);
            //new DetailQueryTask().execute(reviewPath);
            getLoaderManager().initLoader(REVIEW_LOADER, movieBundle, this).forceLoad();
            /*
            try{

                URL url = new URL(trailerPath);
                HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
                conn.setRequestMethod("GET");
                InputStream in = new BufferedInputStream(conn.getInputStream());
                String response = convertStreamToString(in);

            }catch(MalformedURLException e){
                e.printStackTrace();
            }catch(IOException e){
                e.printStackTrace();
            }*/

        }



        trailerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String youtubePath = "https://www.youtube.com/watch?v=" + trailerKey;
                Uri uri = Uri.parse(youtubePath);
                uri = Uri.parse("vnd.youtube:" + uri.getQueryParameter("v"));
                Intent movieIntent = new Intent (Intent.ACTION_VIEW, uri);
                startActivity(movieIntent);
                Toast.makeText(DetailActivity.this,
                        "ImageButton is clicked!", Toast.LENGTH_SHORT).show();
                Log.v(TAG, "what is trailerPath: "+trailerPath);
            }
        });

        fab.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                Toast.makeText(DetailActivity.this,
                        "Fab Fav button is clicked!", Toast.LENGTH_SHORT).show();
                addToMovieDB(movieTitle.getText().toString());  

            }
        });
    }

    /**
     * This method is called when user clicks on the Favorite Star button
     *
     * @param
     */
    public long addToMovieDB(String movieName){
        Log.v(TAG, "addToMovies: "+movieName);
        ContentValues cv = new ContentValues();
        cv.put(MovieContract.MovieEntry.COLUMN_FAVORITE_MOVIE, movieName);
        long dbID = 0;

        try {
            mDB.beginTransaction();
            dbID = mDB.insert(MovieContract.MovieEntry.TABLE_NAME, null, cv);
            mDB.setTransactionSuccessful();
        }catch (SQLException e) {
            e.printStackTrace();
        } finally {
            mDB.endTransaction();
        }
        return dbID;

    }
}
