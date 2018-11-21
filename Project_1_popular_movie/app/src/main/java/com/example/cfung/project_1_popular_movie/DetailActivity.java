package com.example.cfung.project_1_popular_movie;

import android.app.LoaderManager;
import android.content.AsyncTaskLoader;
import android.content.ContentValues;
import android.content.Intent;
import android.content.Loader;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.Movie;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.util.Log;
import android.widget.Toast;

import com.example.cfung.project_1_popular_movie.data.AppDatabase;
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

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.support.v7.widget.RecyclerView.VERTICAL;

/**
 * Created by cfung on 12/27/17.
 * This detail view should show the following info:
 *    -original title (original_title)
 *    -movie poster image thumbnail (poster_path)
 *    -A plot synopsis (called overview in the api)
 *    -user rating (called vote_average in the api)
 *    -release date
 */

public class DetailActivity extends AppCompatActivity implements
        LoaderManager.LoaderCallbacks<ArrayList<String>>{

    private static final String TAG = "MyActivity";
    private String trailerPath = null;
    private ArrayList<String> trailerKeyList = null;
    private String reviewPath = null;
    private String popularity = null;
    private String textLink = null;
    private String textOverview = null;
    private String textRating = null;
    private String textDate = null;
    private String movieID = null;

    @BindView(R.id.detail_image) ImageView imageView;
    @BindView(R.id.detail_name) TextView movieTitle = null;
    @BindView(R.id.movietitletop) TextView movieTitleTop = null;
    @BindView(R.id.detail_overview) TextView movieSynopsis ;
    @BindView(R.id.detail_vote) TextView movieRating;
    @BindView(R.id.detail_date) TextView movieReleaseDate;
    @BindView(R.id.trailers_text) TextView trailerText;

    // Setup layout for Reviews
    @BindView(R.id.recycler_reviews) RecyclerView reviewView;

    // Setup layout for Trailers
    @BindView(R.id.recycler_trailer) RecyclerView trailerView;

    @BindView(R.id.fab) FloatingActionButton fab;

    private static final int TRAILER_LOADER = 1;
    private static final int REVIEW_LOADER = 2;

    //private RecyclerView reviewView;
    private ReviewAdapter recyclerAdapter;
    private RecyclerView.LayoutManager reviewLayoutManager;
    private RecyclerView.LayoutManager trailerLayoutManager;
    //private RecyclerView trailerView;
    private TrailerAdapter trailerAdapter;

    private TextView movieReviews;

    private SQLiteDatabase mDB;
    private AppDatabase mDb;

    ArrayList<String> reviewsList = new ArrayList<String>();
    ArrayList<String> trailersList = new ArrayList<String>();


    @Override
    public Loader<ArrayList<String>> onCreateLoader(final int id, final Bundle bundle) {
        return  new AsyncTaskLoader<ArrayList<String>>(this) {
            @Override
            public ArrayList<String> loadInBackground() {

                ArrayList<String> resultList = new ArrayList<String>();

                if (id == REVIEW_LOADER) {

                    String mID = null;


                    String myStr = "Bundle{";
                    for (String key : bundle.keySet()) {
                        myStr += " " + key + " => " + bundle.get(key) + ";";
                        if (key.equals("id")) {
                            mID = bundle.get(key).toString();
                        }
                    }
                    myStr += " }Bundle";

                    String responseKey = null;
                    String resultKey = null;
                    try {

                        String reviewURL = "https://api.themoviedb.org/3/movie/" + bundle.get("id") + "/reviews?api_key=" + BuildConfig.MY_API_KEY;
                        String resp = NetworkUtils.getResponseFromHttpUrl(new URL(reviewURL));

                        JSONObject results = new JSONObject(resp);

                        JSONArray detailResults = results.getJSONArray("results");
                        for (int i = 0; i < detailResults.length(); i++) {
                            JSONObject jsonobject = detailResults.getJSONObject(i);

                            String movieReview = jsonobject.getString("content");
                            reviewsList.add(movieReview);
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();

                    } catch (MalformedURLException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    //return reviewsList;
                    resultList = reviewsList;

                } else if (id == TRAILER_LOADER) {

                    String response = null;
                    //trailerKeyList = new ArrayList<String>();

                    try {

                        response = NetworkUtils.getResponseFromHttpUrl(new URL(trailerPath));

                        JSONObject results = new JSONObject(response);

                        JSONArray trailerResults = results.getJSONArray("results");
                        for (int i = 0; i < trailerResults.length(); i++) {
                            JSONObject jsonObject = trailerResults.getJSONObject(i);
                            trailersList.add(jsonObject.getString("key"));
                        }

                    } catch (MalformedURLException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (JSONException e) {
                        e.printStackTrace();

                    }
                    //return trailerKeyList;
                    for (String key: trailersList){
                        Log.v(TAG, "trailersList..."+key);
                    }
                    Log.v(TAG, "what is size of trailerKeyList.."+trailersList.size());
                    resultList = trailersList;
                } //end else if
                Log.v(TAG, "What is being returned in resultsList.."+resultList);
                return resultList;
                }; //end loadInBackground

            }; //end AsyncTaskLoader
        } //end onCreateLoader

    @Override
    public void onLoadFinished(Loader<ArrayList<String>> loader, ArrayList<String> strings) {

        if ((strings.size()>0) && (loader.getId()== REVIEW_LOADER)){

            reviewView.setAdapter(new ReviewAdapter(strings));

        } else if((strings.size()>0) && (loader.getId() == TRAILER_LOADER)){

            trailerView.setAdapter(new TrailerAdapter(strings, getApplicationContext()));
        }

    }

    @Override
    public void onLoaderReset(Loader<ArrayList<String>> loader) {

    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail);
        ButterKnife.bind(this);

        recyclerAdapter = new ReviewAdapter(reviewsList);
        reviewLayoutManager = new LinearLayoutManager(getApplicationContext());
        reviewView.setLayoutManager(reviewLayoutManager);
        reviewView.setAdapter(recyclerAdapter);

        trailerAdapter = new TrailerAdapter(trailersList, getApplicationContext());
        trailerLayoutManager = new LinearLayoutManager(getApplicationContext());
        trailerView.setLayoutManager(trailerLayoutManager);
        trailerView.setAdapter(trailerAdapter);
        DividerItemDecoration decoration = new DividerItemDecoration(getApplicationContext(), VERTICAL);
        trailerView.addItemDecoration(decoration);

        Intent movieIntent = getIntent();
        final Bundle movieBundle = movieIntent.getExtras();

        final MovieModel movie = getIntent().getParcelableExtra("movie");

        MovieDBHelper dbHelper = new MovieDBHelper(this);
        mDB = dbHelper.getWritableDatabase();

        mDb = AppDatabase.getsInstance(getApplicationContext());

        String response = null;

        if (movie != null)
        {
            movieTitle.setText(movie.getMovieName());

            movieSynopsis.setText("Synopsis: "+ movie.getOverview());
            movieRating.setText("Rating: "+ movie.getVote_average());
            movieReleaseDate.setText("Release Date: "+ movie.getRelease_date());
            popularity = (String) movieBundle.get("popularity");
            trailerText.setText(getString(R.string.trailers) + ": ");

            SpannableStringBuilder spannable = new SpannableStringBuilder(movie.getMovieName());
            spannable.setSpan(
                    new ForegroundColorSpan(Color.WHITE),
                    0, // start
                    movie.getMovieName().length(), // end
                    Spannable.SPAN_EXCLUSIVE_INCLUSIVE
            );
            spannable.setSpan(
                    new RelativeSizeSpan(3f),
                    0,
                    movie.getMovieName().length(),
                    Spannable.SPAN_EXCLUSIVE_INCLUSIVE
            );
            movieTitleTop.setText(spannable);

            String moviePath = "http://image.tmdb.org/t/p/w185/" + movie.getMovieLink();

            trailerPath = "https://api.themoviedb.org/3/movie/" + movie.getMovieID() +
                    "/videos?api_key=" + BuildConfig.MY_API_KEY;
            reviewPath = "https://api.themoviedb.org/3/movie/" + movie.getMovieID() +
                    "/reviews?api_key=" + BuildConfig.MY_API_KEY;

            Picasso.with(getApplicationContext())
                    .load(moviePath)
                    .placeholder(R.drawable.placeholder)
                    .into(imageView);

            getLoaderManager().initLoader(REVIEW_LOADER, movieBundle, this).forceLoad();

            getLoaderManager().initLoader(TRAILER_LOADER, null, this).forceLoad();

        }

        fab.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                Toast.makeText(DetailActivity.this,
                        "Fab Fav button is clicked!", Toast.LENGTH_SHORT).show();
                addToMovieDB(movie.getMovieName(), movie.getPopularity(), movie.getMovieLink(),
                        movie.getOverview(), movie.getVote_average(), movie.getRelease_date(),
                        movie.getMovieID(), movie.getTrailer());
            }
        });
    }

    /**
     * This method is called when user clicks on the Favorite Star button
     *
     * @param
     */
    public void addToMovieDB(String movieName, String popularity, String poster_path, String overview,
                             String vote_average, String release_date, String id,
                             String trailer){
        Log.v(TAG, "addToMovies: "+movieName);
        ContentValues cv = new ContentValues();
        cv.put(MovieContract.MovieEntry.COLUMN_MOVIE_NAME, movieName);
        cv.put(MovieContract.MovieEntry.COLUMN_MOVIE_POPULARITY, popularity);
        cv.put(MovieContract.MovieEntry.COLUMN_MOVIE_POSTER_PATH, poster_path);
        cv.put(MovieContract.MovieEntry.COLUMN_MOVIE_OVERVIEW, overview);
        cv.put(MovieContract.MovieEntry.COLUMN_MOVIE_VOTE_AVG, vote_average);
        cv.put(MovieContract.MovieEntry.COLUMN_MOVIE_RELEASE_DATE, release_date);
        cv.put(MovieContract.MovieEntry.COLUMN_MOVIE_ID, id);
        //cv.put(MovieContract.MovieEntry.COLUMN_MOVIE_REVIEWS, reviews);
        cv.put(MovieContract.MovieEntry.COLUMN_MOVIE_TRAILER, trailer);
        long dbID = 0;

        Uri uri = getContentResolver().insert(MovieContract.MovieEntry.CONTENT_URI, cv);

        if (uri != null){
            Toast.makeText(getBaseContext(), uri.toString(), Toast.LENGTH_LONG).show();
        }

    }
}
