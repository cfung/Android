package com.example.cfung.project_1_popular_movie;

import android.app.Activity;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
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
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

/**
 * Created by cfung on 10/4/17.
 */
//public class CustomAdapter extends ArrayAdapter<MovieModel>{
public class CustomAdapter extends ArrayAdapter<MovieModel>{

    private static final String TAG = "MyActivity";

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

    public class MovieQueryTask extends AsyncTask<URL, Void, String>{

        @Override
        protected String doInBackground(URL... urls) {
            Log.d(TAG, "starting doInBackground...");
            String response = null;
            try {
                URL url = new URL("https://api.themoviedb.org/3/movie/550?api_key=bad34c8d38b0750ab6bef23cb64440ba");
                //URLConnection conn = movieAPI.openConnection();
                HttpsURLConnection httpconn = (HttpsURLConnection) url.openConnection();
                httpconn.setRequestMethod("GET");
                InputStream in = new BufferedInputStream((httpconn.getInputStream()));
                response = convertStreamToString(in);
                Log.d(TAG, "response: "+ response);

                //DefaultHttpClient httpClient = new DefaultHttpClient(new BasicHttpParams());


                JSONObject jsonObject = new JSONObject(response);
                Log.d(TAG, "jsonObject: "+jsonObject);
                String moviesPoster = jsonObject.getString("poster_path");
                Log.d(TAG, "haha: " + moviesPoster);
            } catch (IOException e){
                e.printStackTrace();
            } catch(JSONException e){
                e.printStackTrace();
            }
            return null;
        }

    }

    public CustomAdapter(Activity context, List<MovieModel> androidFlavors){
        super(context, 0, androidFlavors);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){

        MovieModel movies = getItem(position);

        if (convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item_flavor, parent, false);
        }

        ImageView imageView = (ImageView) convertView.findViewById(R.id.list_item_icon);
        Picasso.with(getContext()).setLoggingEnabled(true);
        //https://api.themoviedb.org/3/movie/550?api_key=bad34c8d38b0750ab6bef23cb64440ba
        try {
            URL url = new URL("https://api.themoviedb.org/3/movie/550?api_key=bad34c8d38b0750ab6bef23cb64440ba");
            new MovieQueryTask().execute(url);
            //JSONParser jsonParser = new JSONParser();

            //final JSONObject json = jsonParser.makeHttpRequest(url_kbj + "/" + idkbj + "/", "GET", params1);
            //Log.i("jsonda =", String.valueOf(json));
            //final JSONObject data = json.getJSONObject("data");

            //Object obj = new JSONParser().parse(new FileReader("https://api.themoviedb.org/3/movie/550?api_key=bad34c8d38b0750ab6bef23cb64440ba"));

        } catch(MalformedURLException e){
            e.printStackTrace();
        } catch (IOException e){
            e.printStackTrace();
        }

        Picasso.with(getContext())
                .load("http://image.tmdb.org/t/p/w500/qlGoGQSVMzIjGbpvXzZUOH1FjNu.jpg")
                .placeholder(R.drawable.placeholder)
                .into(imageView);
        //imageView.setImageResource(R.drawable.ic_launcher);
        /*Picasso.with(context)
                .load("http://i.imgur.com/DvpvklR.png")
                .into(iconView);*/

        TextView movieNameView = (TextView) convertView.findViewById(R.id.list_item_movie_name);
        movieNameView.setText("movie 1");
        //movieNameView.setText(movies.getMovieName());

        return convertView;
    }
}
