package com.example.cfung.project_1_popular_movie;

import android.content.Intent;
import android.graphics.Movie;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.util.Log;
import android.widget.Toast;
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

public class DetailActivity extends AppCompatActivity {

    private static final String TAG = "MyActivity";
    private String trailerPath = null;
    private String trailerKey = null;
    private String reviewPath = null;

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

    public class DetailQueryTask extends AsyncTask<String, Void, String>{

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
                    String movieKey = jsonobject.getString("key");
                    resultKey = movieKey;
                    //String resp_movieKey = makeServiceCall("");
                }

            } catch (JSONException e){
                e.printStackTrace();
            }


            Log.v(TAG, "what is resultslist in trailer key.."+resultKey.toString());
            return resultKey;
        }

        @Override
        protected void onPostExecute(String result){
            super.onPostExecute(result);

            if(result != null){

                trailerKey = result;

            }
        }

    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail);

        ImageView imageView = (ImageView)findViewById(R.id.detail_image);
        ImageButton trailerBtn = (ImageButton)findViewById(R.id.detail_trailer);
        TextView movieTitle = (TextView)findViewById(R.id.detail_name);
        TextView movieSynopsis = (TextView)findViewById(R.id.detail_overview);
        TextView movieRating = (TextView)findViewById(R.id.detail_vote);
        TextView movieReleaseDate = (TextView)findViewById(R.id.detail_date);
        TextView movieReviews = (TextView)findViewById(R.id.detail_review);

        Intent movieIntent = getIntent();
        Bundle movieBundle = movieIntent.getExtras();

        if(movieBundle != null)
        {
            String movieID = (String) movieBundle.get("id");
            String textLink = (String) movieBundle.get("link");
            String textTitle =(String) movieBundle.get("name");
            movieTitle.setText("Title: "+textTitle);
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

            movieReviews.setText("Reviews: "+ reviewPath);

            Picasso.with(getApplicationContext())
                    .load(moviePath)
                    .placeholder(R.drawable.placeholder)
                    .into(imageView);

            new DetailQueryTask().execute(trailerPath);

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

    }
}
