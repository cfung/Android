package com.example.cfung.project_3_baking_app;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import static android.content.Intent.getIntent;

/**
 * Created by cfung on 1/7/19.
 */

public class RecipeDetailFragment extends Fragment {

    ArrayList<RecipeModel> recipeModels;
    String name;

    private static final String TAG = "RecipeDetailFragment";

    public RecipeDetailFragment(){}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        Log.v(TAG, "RecipeDetailFragment onCreateView is starting");

        RecyclerView recyclerView;
        TextView textView;

        recipeModels = new ArrayList<>();

        // add handle for case where savedInstanceState is null
        if (savedInstanceState != null){
            Log.v(TAG, "RecipeDetailFragment onCreateView (savedInstanceState)...: ");
            recipeModels = savedInstanceState.getParcelableArrayList("recipe");

        } else {

            //Intent recipeIntent = getActivity().getIntent().getExtras().getString("image")
            Intent recipeIntent = getActivity().getIntent();
            Bundle bundle = recipeIntent.getExtras();
            ArrayList recipes;
            recipes = bundle.getParcelableArrayList("recipe");
            Log.v(TAG, "what is recipe in RecipeDetailFragment - onCreateView: " + recipes);
        }


        Log.v(TAG, "recipe (RecipeDetailFragment) recipeModels is..: " + recipeModels);

        View rootview = inflater.inflate(R.layout.recipe_detail_fragment_body_part, container, false);

        return rootview;
    }

}
