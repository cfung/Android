package com.example.cfung.project_3_baking_app;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.cfung.project_3_baking_app.utils.IRecipe;
import com.example.cfung.project_3_baking_app.utils.RetrofitBuilder;

import java.lang.reflect.Array;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by cfung on 1/7/19.
 */

public class RecipeFragment extends Fragment {

    private static final String TAG = "RecipeFragment";

    public RecipeFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        RecyclerView recyclerView;

        final View rootView = inflater.inflate(R.layout.master_list_fragment, container, false);

        recyclerView = (RecyclerView) rootView.findViewById(R.id.recipe_main_recycler);
        final RecipeAdapter recipeAdapter = new RecipeAdapter((MainActivity)getActivity());
        recyclerView.setAdapter(recipeAdapter);

        //Log.v(TAG, "rootView.getTag: " + rootView.getTag())
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);

        Log.v(TAG, "before Retrieve()..");
        IRecipe iRecipe = RetrofitBuilder.Retrieve();
        Call<ArrayList<RecipeModel>> recipe = iRecipe.getRecipe();
        Log.v(TAG, "recipe from getRecipe()..: " + recipe);


        Log.v(TAG, "onCreateView (before enqueue)...");
        recipe.enqueue(new Callback<ArrayList<RecipeModel>>() {

               @Override
               public void onResponse(Call<ArrayList<RecipeModel>> call, Response<ArrayList<RecipeModel>> response) {

                   Integer statusCode = response.code();

                   Log.v(TAG, "inside onResponse...");
                   Log.v("status code: ", statusCode.toString());

                   ArrayList<RecipeModel> recipes = response.body();

                   Bundle recipesBundle = new Bundle();
                   recipesBundle.putParcelableArrayList("ALL_RECIPES", recipes);

                   Log.v(TAG, "onResponse...");
                   for (int x=0; x < recipes.size(); x++){
                       Log.v(TAG, "recipe (onResponse): "+recipes.get(x).getRecipeName());
                   }

                   recipeAdapter.setRecipeData(recipes, getContext());
               }

               @Override
               public void onFailure(Call<ArrayList<RecipeModel>> call, Throwable t) {
                    Log.v(TAG, "onFailure is called.." + t.toString());
               }
           }
        );

        return rootView;
        //return super.onCreateView(inflater, container, savedInstanceState);
    }
}
