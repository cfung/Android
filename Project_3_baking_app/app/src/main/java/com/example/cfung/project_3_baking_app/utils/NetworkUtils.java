package com.example.cfung.project_3_baking_app.utils;

import android.net.Uri;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

/**
 * Created by cfung on 3/1/18.
 */

public class NetworkUtils {

    private static final String TAG = "MyActivity";

    public final static String BAKING_APP_JSON =
            "https://d17h27t6h515a5.cloudfront.net/topher/2017/May/59121517_baking/baking.json";


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
     * Builds the URL used to query Baking App Recipe.
     *
     * @param recipeSearchQuery The keyword that will be queried for.
     * @return The URL to use to query the GitHub.
     */
    public static URL buildUrl(String recipeSearchQuery) {
        Uri builtUri = Uri.parse(BAKING_APP_JSON).buildUpon()
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
