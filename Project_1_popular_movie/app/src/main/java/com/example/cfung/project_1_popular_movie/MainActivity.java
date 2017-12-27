package com.example.cfung.project_1_popular_movie;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.GridView;

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

import javax.net.ssl.HttpsURLConnection;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MyActivity";
    final static String MOVIE_API_URL = "https://api.themoviedb.org/3/movie/popular?api_key=bad34c8d38b0750ab6bef23cb64440ba";
    private GridView gridView = null;
    private CustomAdapter movieAdapter = null;
    ArrayList<MovieModel> AllMovies = null;

    public String makeServiceCall(String reqUrl){
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

    }

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

    public class MovieQueryTask extends AsyncTask<String, Void, ArrayList<MovieModel>> {

        @Override
        protected ArrayList<MovieModel> doInBackground(String... urls) {
            Log.v(TAG, "starting doInBackground...");
            String response = null;
            ArrayList<MovieModel> resultslist = new ArrayList<MovieModel>();
            //String[] resultslist = null;

            try {
                URL url = new URL(urls[0]);
                //URLConnection conn = movieAPI.openConnection();
                HttpsURLConnection httpconn = (HttpsURLConnection) url.openConnection();
                httpconn.setRequestMethod("GET");
                InputStream in = new BufferedInputStream((httpconn.getInputStream()));
                response = convertStreamToString(in);
                Log.v(TAG, "json response: "+ response);

                //DefaultHttpClient httpClient = new DefaultHttpClient(new BasicHttpParams());
                JSONObject results = new JSONObject(response);

                Log.v(TAG, "results response: " + results);

                JSONArray movieResults = results.getJSONArray("results");
                for (int i=0; i<movieResults.length(); i++){
                    //ArrayList<String> movieArray = new ArrayList<String>();
                    JSONObject jsonobject = movieResults.getJSONObject(i);
                    String poster_path = jsonobject.getString("poster_path");
                    Log.v(TAG, "movieResults poster_path...:" + poster_path);
                    String title = jsonobject.getString("original_title");
                    String popularity = jsonobject.getString("popularity");
                    // completed:  add movie to movieArray
                    MovieModel movie = new MovieModel(title, popularity, poster_path);
                    resultslist.add(movie);
                    //resultslist.add(movieArray);

                }
                //JSONObject jsonObject = new JSONObject(response);
                //Log.d(TAG, "jsonObject: "+jsonObject);
                //String moviesPoster = jsonObject.getString("poster_path");
                //Log.d(TAG, "haha: " + moviesPoster);
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
            /*Log.v(TAG, "first result in onPostExecute "+ result);
            if (result!=null) {
                for (int i=0; i<result.size(); i++) {
                    Log.v(TAG, "i is..." + String.valueOf(i));
                    Log.v(TAG,"result in onPostExecute is: "+result.get(i).getMovieName());
                    //MovieModel movie = new MovieModel(title, popularity, poster);
                    MovieModel movie = new MovieModel(result.get(i).getMovieName(), result.get(i).getPopularity(), result.get(i).getMovieLink());
                    AllMovies.add(movie);
                    Log.v(TAG, "end of onPostExecute.."+AllMovies.get(i).getMovieName());
                }
            }*/
            if(result != null){
                //mPosterMoviePaths =  MovieDataParser.getMoviePosterPaths(mMovieJsonStr);
                //AllMovies = result;
                movieAdapter.clear();
                for (MovieModel movie:result){
                    Log.v(TAG, "what is result in onPostExecute..:"+movie.getMovieName());
                    movieAdapter.add(movie);
                }
            }

            /*
            mForecastAdapter.clear();
            for(String dayForecastStr : result) {
                mForecastAdapter.add(dayForecastStr);
            }*/

            //movieAdapter = new CustomAdapter(MainActivity.this, 0, AllMovies);
            //Log.v(TAG, "AllMovies...in onPostExecute.."+AllMovies.get(0).getMovieName());
            //gridView.setAdapter(movieAdapter);

        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);
        setContentView(R.layout.main);

        //View rootView = inflater.inflate(R.layout.activity_main, container, false);

        //String url = "https://api.themoviedb.org/3/movie/popular?api_key=bad34c8d38b0750ab6bef23cb64440ba";
        //new MovieQueryTask().execute(url);

        /*Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/


        GridView gridView = (GridView) findViewById(R.id.movie_grid);
        AllMovies = new ArrayList<MovieModel>();
        // TODO 2:  What should resources be set to??
        movieAdapter = new CustomAdapter(MainActivity.this, 0, AllMovies);
        gridView.setAdapter(movieAdapter);
        // completed 4:  call asynctask here
        new MovieQueryTask().execute(MOVIE_API_URL);
        //makeServiceCall(MOVIE_API_URL);
        //return rootView;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
