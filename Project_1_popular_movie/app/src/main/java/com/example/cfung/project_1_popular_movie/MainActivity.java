package com.example.cfung.project_1_popular_movie;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Movie;
import android.os.AsyncTask;
import android.os.Bundle;
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

import android.widget.AdapterView.OnItemClickListener;

import com.example.cfung.project_1_popular_movie.data.MovieDBHelper;
import com.example.cfung.project_1_popular_movie.utils.NetworkUtils;
import com.facebook.stetho.Stetho;

import javax.net.ssl.HttpsURLConnection;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MyActivity";
    //final static String MOVIE_API_POPULAR = "https://api.themoviedb.org/3/movie/popular?api_key="+BuildConfig.MY_API_KEY;
    //final static String MOVIE_API_TOP = "https://api.themoviedb.org/3/movie/top_rated?api_key="+BuildConfig.MY_API_KEY;
    private GridView gridView = null;
    private CustomAdapter movieAdapter = null;
    ArrayList<MovieModel> AllMovies = null;
    ArrayList<MovieModel> result = null;



    /*public String makeServiceCall(String reqUrl){
        Log.v(TAG, "starting makeServiceCall..");
        String response = null;
        try{
            Log.v(TAG, "in makeServiceCall - try..");
            URL url = new URL (reqUrl);
            HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            InputStream in = new BufferedInputStream(conn.getInputStream());
            Log.v("resp in makeService: ", in.toString());
            response = convertStreamToString(in);

        } catch (IOException e){
            e.printStackTrace();
        }
        Log.v(TAG, "response in makeSeriviceCall.."+response);
        return response;

    }*/

    /*private String convertStreamToString(InputStream is){
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
    }*/

    // AsyncTask to perform network operation in a separate thread than mainUI thread
    public class MovieQueryTask extends AsyncTask<String, Void, ArrayList<MovieModel>> {

        @Override
        protected ArrayList<MovieModel> doInBackground(String... urls) {
            Log.v(TAG, "starting doInBackground...");
            String response = null;
            ArrayList<MovieModel> resultslist = new ArrayList<MovieModel>();

            try {
                Log.v(TAG, "what is URL in doInBackground.."+urls[0].toString());
                URL url = new URL(urls[0]);
                /*HttpsURLConnection httpconn = (HttpsURLConnection) url.openConnection();
                httpconn.setRequestMethod("GET");
                InputStream in = new BufferedInputStream((httpconn.getInputStream()));
                response = convertStreamToString(in);*/
                response = NetworkUtils.getResponseFromHttpUrl(url);

                JSONObject results = new JSONObject(response);

                Log.v(TAG, "results response: " + results);

                JSONArray movieResults = results.getJSONArray("results");
                for (int i=0; i<movieResults.length(); i++){
                    //ArrayList<String> movieArray = new ArrayList<String>();
                    JSONObject jsonobject = movieResults.getJSONObject(i);
                    String id = jsonobject.getString("id");
                    String poster_path = jsonobject.getString("poster_path");
                    Log.v(TAG, "movieResults poster_path...:" + poster_path);
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
            Log.v(TAG, "returning results in doInbackground.."+resultslist);
            return resultslist;
        }

        @Override
        protected void onPostExecute(ArrayList<MovieModel> result){
            super.onPostExecute(result);
            Log.v(TAG, "what is result in onPostExecute.." +result);
            //ArrayList<MovieModel> AllMovies = new ArrayList<MovieModel>();

            if(result != null){

                movieAdapter.clear();
                for (int i=0; i<result.size();i++){
                    Log.v(TAG, "i is..."+i);
                    Log.v(TAG, "what is result in onPostExecute..:"+result.get(i).getMovieName());
                    movieAdapter.add(result.get(i));
                }
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        Log.v(TAG, "inside onCreate");

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (toolbar == null){
            throw new Error("canot find toolbar..");
        }

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(false);

        GridView gridView = (GridView) findViewById(R.id.movie_grid);
        AllMovies = new ArrayList<MovieModel>();
        // TODO 2:  What should resources be set to??
        movieAdapter = new CustomAdapter(MainActivity.this, 0, AllMovies);
        gridView.setAdapter(movieAdapter);
        // completed 4:  call asynctask here
        new MovieQueryTask().execute(NetworkUtils.MOVIE_API_POPULAR);

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
                Toast.makeText(context, message, Toast.LENGTH_LONG).show();

                Class detailActivity = DetailActivity.class;

                Intent startDetailActivityIntent = new Intent(context, detailActivity);
                startDetailActivityIntent.putExtra("name", AllMovies.get(position).getMovieName());
                startDetailActivityIntent.putExtra("link", AllMovies.get(position).getMovieLink());
                startDetailActivityIntent.putExtra("overview", AllMovies.get(position).getOverview());
                startDetailActivityIntent.putExtra("vote_average", AllMovies.get(position).getVote_average());
                startDetailActivityIntent.putExtra("release_date", AllMovies.get(position).getRelease_date());
                startDetailActivityIntent.putExtra("id", AllMovies.get(position).getMovieID());
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
                result = dbHelper.getFavoriteMoviesFromDB();

                if(result != null){

                    movieAdapter.clear();
                    for (int i=0; i<result.size();i++){
                        movieAdapter.add(result.get(i));
                    }
                }

                return true;

            default:
                return super.onOptionsItemSelected(item);
        }

    }
}
