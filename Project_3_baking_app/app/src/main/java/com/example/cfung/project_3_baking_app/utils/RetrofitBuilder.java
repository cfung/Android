package com.example.cfung.project_3_baking_app.utils;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by cfung on 1/11/19.
 */

public class RetrofitBuilder {

    private static final String TAG = "RetrofitBuilder";

    static IRecipe iRecipe;

    public static IRecipe Retrieve(){

        Log.v(TAG, "inside Retreve()");
        Gson gson = new GsonBuilder().create();

        OkHttpClient.Builder httpClientBuilder = new OkHttpClient.Builder();

        iRecipe = new Retrofit.Builder()
                .baseUrl("https://d17h27t6h515a5.cloudfront.net/topher/2017/May/59121517_baking/")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .callFactory(httpClientBuilder.build())
                .build().create(IRecipe.class);
        Log.v(TAG, "iRecipe status.." + iRecipe.toString());
        return iRecipe;
    }


}
