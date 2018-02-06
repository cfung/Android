package com.example.cfung.project_1_popular_movie.utils;

import android.net.Uri;
import android.util.Log;

import com.example.cfung.project_1_popular_movie.BuildConfig;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

import javax.net.ssl.HttpsURLConnection;

/**
 * Created by cfung on 2/6/18.
 * Used to communicate with the network
 */

public class NetworkUtils {

    private static final String TAG = "MyActivity";

    public final static String MOVIE_API_POPULAR =
            "https://api.themoviedb.org/3/movie/popular?api_key="+ BuildConfig.MY_API_KEY;
    public final static String MOVIE_API_TOP =
            "https://api.themoviedb.org/3/movie/top_rated?api_key="+BuildConfig.MY_API_KEY;

    /*
     * The sort field. One of stars, forks, or updated.
     * Default: results are sorted by best match if no field is specified.
     */
    final static String PARAM_SORT = "sort";
    final static String sortBy = "stars";

    public static String convertStreamToString(InputStream is){
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

    public static String makeServiceCall(String reqUrl){
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

    /**
     * This method returns the entire result from the HTTP response.
     *
     * @param url The URL to fetch the HTTP response from.
     * @return The contents of the HTTP response.
     * @throws IOException Related to network and stream reading
     */
    public static String getResponseFromHttpUrl(URL url) throws IOException, MalformedURLException {

        String response = null;

        HttpURLConnection httpconn = (HttpURLConnection) url.openConnection();

        httpconn.setRequestMethod("GET");
        InputStream in = new BufferedInputStream((httpconn.getInputStream()));
        response = convertStreamToString(in);

        Log.v(TAG, "json response: "+ response);
        return response;
    }

    /**
     * Builds the URL used to query GitHub.
     *
     * @param githubSearchQuery The keyword that will be queried for.
     * @return The URL to use to query the GitHub.
     */
    public static URL buildUrl(String githubSearchQuery) {
        Uri builtUri = Uri.parse(MOVIE_API_POPULAR).buildUpon()
                .build();

        URL url = null;
        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        return url;
    }



}
