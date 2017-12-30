package com.example.cfung.project_1_popular_movie;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;
import android.util.Log;

import com.squareup.picasso.Picasso;

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

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail);

        ImageView imageView = (ImageView)findViewById(R.id.detail_image);
        TextView movieTitle = (TextView)findViewById(R.id.detail_name);
        TextView movieSynopsis = (TextView)findViewById(R.id.detail_overview);
        TextView movieRating = (TextView)findViewById(R.id.detail_vote);
        TextView movieReleaseDate = (TextView)findViewById(R.id.detail_date);

        Intent movieIntent = getIntent();
        Bundle movieBundle = movieIntent.getExtras();

        if(movieBundle != null)
        {
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

            Picasso.with(getApplicationContext())
                    .load(moviePath)
                    .placeholder(R.drawable.placeholder)
                    .into(imageView);

        }

    }
}
