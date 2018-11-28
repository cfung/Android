package com.example.cfung.project_1_popular_movie;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Movie;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import android.widget.AdapterView.OnItemClickListener;

import com.example.cfung.project_1_popular_movie.data.AppDatabase;
import com.example.cfung.project_1_popular_movie.data.MovieDBHelper;
import com.example.cfung.project_1_popular_movie.data.MovieDao;
import com.example.cfung.project_1_popular_movie.utils.NetworkUtils;
import com.facebook.stetho.Stetho;
import butterknife.BindView;
import butterknife.ButterKnife;

import javax.annotation.Nullable;
import javax.net.ssl.HttpsURLConnection;



public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MyActivity";
    private AppDatabase mDb;
    private CustomAdapter movieAdapter = null;
    ArrayList<MovieModel> AllMovies = null;
    //List<MovieModel> result = null;
    LiveData<List<MovieModel>> result = null;
    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.movie_grid) GridView gridView;

    // AsyncTask to perform network operation in a separate thread than mainUI thread
    public class MovieQueryTask extends AsyncTask<String, Void, ArrayList<MovieModel>> {

        @Override
        protected ArrayList<MovieModel> doInBackground(String... urls) {

            String response = null;
            ArrayList<MovieModel> resultslist = new ArrayList<MovieModel>();

            try {

                URL url = new URL(urls[0]);

                response = NetworkUtils.getResponseFromHttpUrl(url);

                JSONObject results = new JSONObject(response);

                JSONArray movieResults = results.getJSONArray("results");
                for (int i=0; i<movieResults.length(); i++){
                    //ArrayList<String> movieArray = new ArrayList<String>();
                    JSONObject jsonobject = movieResults.getJSONObject(i);
                    String id = jsonobject.getString("id");
                    String poster_path = jsonobject.getString("poster_path");
                    String title = jsonobject.getString("original_title");
                    String popularity = jsonobject.getString("popularity");
                    String overview = jsonobject.getString("overview");
                    String vote_average = jsonobject.getString("vote_average");
                    String release_date = jsonobject.getString("release_date");

                    ArrayList<String> mReview = new ArrayList<String>();
                    String trailer = null;

                    // completed:  add movie to movieArray
                    MovieModel movie = new MovieModel(title, popularity, poster_path, overview, vote_average, release_date, id, mReview, trailer );
                    resultslist.add(movie);

                }

            } catch (IOException e){
                e.printStackTrace();
            } catch(JSONException e){
                e.printStackTrace();
            }

            return resultslist;
        }

        @Override
        protected void onPostExecute(ArrayList<MovieModel> result){
            super.onPostExecute(result);

            if(result != null){

                movieAdapter.clear();
                for (int i=0; i<result.size();i++){
                    movieAdapter.add(result.get(i));
                }
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        ButterKnife.bind(this);

        //Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (toolbar == null){
            throw new Error("cannot find toolbar..");
        }

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(false);

        mDb = AppDatabase.getsInstance(getApplicationContext());

        if ((savedInstanceState ==null) || (!savedInstanceState.containsKey(getString(R.string.movie_key))) ){

            AllMovies = new ArrayList<MovieModel>();

            movieAdapter = new CustomAdapter(MainActivity.this, 0, AllMovies);
            gridView.setAdapter(movieAdapter);
            // completed 4:  call asynctask here
            new MovieQueryTask().execute(NetworkUtils.MOVIE_API_POPULAR);
        }
        // recovering the instance state
        // if (savedInstanceState != null)
        else {

            AllMovies = savedInstanceState.getParcelableArrayList(getString(R.string.movie_key));
            movieAdapter = new CustomAdapter(MainActivity.this, 0, AllMovies);
            gridView.setAdapter(movieAdapter);
        }

        //enable Facebook Stetho debugger
        Stetho.InitializerBuilder initializerBuilder = Stetho.newInitializerBuilder(this);
        initializerBuilder.enableWebKitInspector(Stetho.defaultInspectorModulesProvider(this));
        Stetho.Initializer initializer = initializerBuilder.build();
        Stetho.initialize(initializer);

        gridView.setOnItemClickListener(new OnItemClickListener(){

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id){

                Context context = MainActivity.this;
                String message = AllMovies.get(position).getMovieName()+"Image clicked!\n";
                //Toast.makeText(context, message, Toast.LENGTH_LONG).show();

                Class detailActivity = DetailActivity.class;

                Intent startDetailActivityIntent = new Intent(context, detailActivity);
                startDetailActivityIntent.putExtra("movie", AllMovies.get(position));

                startDetailActivityIntent.putExtra("name", AllMovies.get(position).getMovieName());
                startDetailActivityIntent.putExtra("link", AllMovies.get(position).getMovieLink());
                startDetailActivityIntent.putExtra("overview", AllMovies.get(position).getOverview());
                startDetailActivityIntent.putExtra("vote_average", AllMovies.get(position).getVote_average());
                startDetailActivityIntent.putExtra("release_date", AllMovies.get(position).getRelease_date());
                startDetailActivityIntent.putExtra("id", AllMovies.get(position).getId());
                startDetailActivityIntent.putExtra("popularity", AllMovies.get(position).getPopularity());
                startActivity(startDetailActivityIntent);

            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        Log.v(TAG,"inside onCreateOptionsMenu..");
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        switch (id) {
            case R.id.action_popular:
                Log.v(TAG, "onOptionsItemSelected - popular");
                new MovieQueryTask().execute(NetworkUtils.MOVIE_API_POPULAR);
                return true;

            case R.id.action_toprated:
                Log.v(TAG, "onOptionsItemSelected - top rated");
                new MovieQueryTask().execute(NetworkUtils.MOVIE_API_TOP);
                return true;

            case R.id.action_favorite:
                Log.v(TAG, "onOptionsItemSelected - favorite");
                MovieDBHelper dbHelper = new MovieDBHelper(this);
                //Cursor cursor = dbHelper.getFavoriteMoviesFromDB();
                //result = dbHelper.getFavoriteMoviesFromDB();

                result = mDb.movieDao().getFavoriteMoviesFromDB();
                AddMovieViewModelFactory factory = new AddMovieViewModelFactory(mDb);
                final AddMovieViewModel viewModel = ViewModelProviders.of(this, factory).get(AddMovieViewModel.class);
                final MovieDao movieDao = (MovieDao) AppDatabase.getsInstance(getApplicationContext()).movieDao();
                viewModel.getMovies().observe(this, new Observer<List<MovieModel>>() {

                    @Override
                    public void onChanged(@Nullable List<MovieModel> movieModels) {

                        movieAdapter.setMovies(movieModels);
                    } // close onChanged
                }); // close observer

                /*if(result != null){

                    movieAdapter.clear();
                    for (int i=0; i < result.size();i++){
                        movieAdapter.add(result.get(i));
                    }
                }*/

                return true;

            default:
                return super.onOptionsItemSelected(item);
        }

    }

    public void onSaveInstanceState(Bundle outState){

        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList("movie", AllMovies);
    }

    @Override
    protected void onDestroy() {
        AppDatabase.destroyInstance();
        Log.v(TAG, "onDestroy is called...");
        super.onDestroy();
    }
}
