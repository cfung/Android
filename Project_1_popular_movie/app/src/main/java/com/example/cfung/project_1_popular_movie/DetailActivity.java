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
import com.example.cfung.project_1_popular_movie.utils.NetworkUtils;
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
    private String popularity = null;
    private String textLink = null;
    private String textOverview = null;
    private String textRating = null;
    private String textDate = null;
    private String movieID = null;

    private static final int TRAILER_LOADER = 1;
    private static final int REVIEW_LOADER = 2;

    private SQLiteDatabase mDB;

    ArrayList<String> reviewsList = new ArrayList<String>();

    @Override
    public Loader<ArrayList<String>> onCreateLoader(int i, final Bundle bundle) {
        return  new AsyncTaskLoader<ArrayList<String>>(this) {
            @Override
            public ArrayList<String> loadInBackground() {
                Log.v(TAG, "inside loadInBackground..");
                String mID = null;


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
                    String resp = NetworkUtils.getResponseFromHttpUrl(new URL(reviewURL));
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

                } catch (MalformedURLException e){
                    e.printStackTrace();
                } catch (IOException e){
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

        MovieModel movie = getIntent().getParcelableExtra("movie");

        MovieDBHelper dbHelper = new MovieDBHelper(this);
        mDB = dbHelper.getWritableDatabase();

        //if(movieBundle != null)
        if (movie != null)
        {
            //movieID = (String) movieBundle.get("id");
            //textLink = (String) movieBundle.get("link");
            //String textTitle =(String) movieBundle.get("name");
            //movieTitle.setText(textTitle);
            movieTitle.setText(movie.getMovieName());
            //textOverview =(String) movieBundle.get("overview");
            movieSynopsis.setText("Synopsis: "+ movie.getOverview());
            //textRating =(String) movieBundle.get("vote_average");
            movieRating.setText("Rating: "+ movie.getVote_average());
            //textDate =(String) movieBundle.get("release_date");
            movieReleaseDate.setText("Release Date: "+ movie.getRelease_date());
            popularity = (String) movieBundle.get("popularity");


            String moviePath = "http://image.tmdb.org/t/p/w185/" + movie.getMovieLink();
            Log.v(TAG, "what is moviePath in detail.."+moviePath);
            Log.v(TAG, "what is movieID in detail.."+ movie.getMovieID());
            trailerPath = "https://api.themoviedb.org/3/movie/" + movie.getMovieID() + "/videos?api_key=bad34c8d38b0750ab6bef23cb64440ba";
            reviewPath = "https://api.themoviedb.org/3/movie/" + movie.getMovieID() + "/reviews?api_key=bad34c8d38b0750ab6bef23cb64440ba";

            Log.v(TAG, "what is reviewPath.."+reviewPath);

            Picasso.with(getApplicationContext())
                    .load(moviePath)
                    .placeholder(R.drawable.placeholder)
                    .into(imageView);

            getLoaderManager().initLoader(REVIEW_LOADER, movieBundle, this).forceLoad();

        }

        trailerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String youtubePath = "https://www.youtube.com/watch?v=" + trailerKey;
                Log.v(TAG, "what is youtube path.." + youtubePath);
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
                //addToMovieDB(movieTitle.getText().toString(), popularity, textLink, textOverview,
                //        textRating, textDate, movieID, reviewsList, trailerPath);
                ContentValues contentValues = new ContentValues();
                // Put the task description and selected mPriority into the ContentValues
                contentValues.put(MovieContract.MovieEntry.COLUMN_MOVIE_NAME, movieTitle.getText().toString());
                contentValues.put(MovieContract.MovieEntry.COLUMN_MOVIE_POPULARITY, popularity);
                contentValues.put(MovieContract.MovieEntry.COLUMN_MOVIE_POSTER_PATH, textLink);
                contentValues.put(MovieContract.MovieEntry.COLUMN_MOVIE_OVERVIEW, textOverview);
                contentValues.put(MovieContract.MovieEntry.COLUMN_MOVIE_VOTE_AVG, textRating);
                contentValues.put(MovieContract.MovieEntry.COLUMN_MOVIE_RELEASE_DATE, textRating);
                contentValues.put(MovieContract.MovieEntry.COLUMN_MOVIE_ID, movieID);
                //contentValues.put(MovieContract.MovieEntry.COLUMN_MOVIE_REVIEWS, reviewsList);
                contentValues.put(MovieContract.MovieEntry.COLUMN_MOVIE_TRAILER, trailerPath);


                // Insert the content values via a ContentResolver
                Log.v(TAG, "what is CONTENT_URI.." + MovieContract.MovieEntry.CONTENT_URI);
                Uri uri = getContentResolver().insert(MovieContract.MovieEntry.CONTENT_URI, contentValues);

            }
        });
    }

    /**
     * This method is called when user clicks on the Favorite Star button
     *
     * @param
     */
    // MovieModel movie = new MovieModel(title, popularity,
    // poster_path, overview, vote_average, release_date, id, mReview, trailer );
    public long addToMovieDB(String movieName, String popularity, String poster_path, String overview,
                             String vote_average, String release_date, String id,
                             ArrayList<String> reviews, String trailer){
        Log.v(TAG, "addToMovies: "+movieName);
        ContentValues cv = new ContentValues();
        cv.put(MovieContract.MovieEntry.COLUMN_MOVIE_NAME, movieName);
        cv.put(MovieContract.MovieEntry.COLUMN_MOVIE_POPULARITY, popularity);
        cv.put(MovieContract.MovieEntry.COLUMN_MOVIE_POSTER_PATH, poster_path);
        cv.put(MovieContract.MovieEntry.COLUMN_MOVIE_OVERVIEW, overview);
        cv.put(MovieContract.MovieEntry.COLUMN_MOVIE_VOTE_AVG, vote_average);
        cv.put(MovieContract.MovieEntry.COLUMN_MOVIE_RELEASE_DATE, release_date);
        cv.put(MovieContract.MovieEntry.COLUMN_MOVIE_ID, id);
        cv.put(MovieContract.MovieEntry.COLUMN_MOVIE_REVIEWS, reviews.get(0));
        cv.put(MovieContract.MovieEntry.COLUMN_MOVIE_TRAILER, trailer);
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
