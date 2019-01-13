package com.example.cfung.project_3_baking_app.utils;

import com.example.cfung.project_3_baking_app.RecipeModel;

import retrofit2.Call;
import retrofit2.http.GET;

import java.util.ArrayList;

/**
 * Created by cfung on 1/11/19.
 */

public interface IRecipe {
    @GET("baking.json")
    Call<ArrayList<RecipeModel>> getRecipe();
}
