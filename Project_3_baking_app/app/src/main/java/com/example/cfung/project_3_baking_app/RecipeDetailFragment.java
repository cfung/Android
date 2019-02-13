package com.example.cfung.project_3_baking_app;

import android.support.v4.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.cfung.project_3_baking_app.widget.UpdateBakingService;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by cfung on 1/7/19.
 */

public class RecipeDetailFragment extends Fragment {

    ArrayList<RecipeModel> recipeModels;
    String name;

    static String RECIPES = "RECIPES";
    static String SELECTED_RECIPES = "SELECTED_RECIPES";
    static String STEPS = "STEPS";
    static String INDEX = "INDEX";
    static String RECIPE_DETAIL = "RECIPE_DETAIL";
    static String RECIPE_STEP_DETAIL = "RECIPE_STEP_DETAIL";

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
            Log.v(TAG, "RecipeDetailFragment savedInstanceState NOT null...: ");
            recipeModels = savedInstanceState.getParcelableArrayList(SELECTED_RECIPES);

        } else {

            Log.v(TAG, "RecipeDetailFragment savedInstanceState is null...: ");

            //Intent recipeIntent = getActivity().getIntent();
            //Bundle bundle = recipeIntent.getExtras();
            //ArrayList recipes;
            //recipes = bundle.getParcelableArrayList(SELECTED_RECIPES);
            //Log.v(TAG, "what is recipe in RecipeDetailFragment - onCreateView: " + recipes);
            recipeModels = getArguments().getParcelableArrayList(SELECTED_RECIPES);
        }

        /*
        Log.v(TAG, "recipe (RecipeDetailFragment) recipeModels is..: " + recipeModels);
        if (recipeModels != null) {
            Log.v(TAG, "first recipe name is ..: " + recipeModels.get(0).getRecipeName());
            name = recipeModels.get(0).getRecipeName();
        }*/
        List<Ingredient> ingredients = recipeModels.get(0).getIngredients();
        Log.v(TAG, "ingrdients...: " + ingredients);
        name = recipeModels.get(0).getRecipeName();


        View rootview = inflater.inflate(R.layout.recipe_detail_fragment_body_part, container, false);

        //ArrayList<Ingredient> ingredients = recipeModels.get(0).getIngredients();

        textView = (TextView)rootview.findViewById(R.id.recipe_detail_text);

        ArrayList<String> recipeIngredientsWidgets = new ArrayList<>();

        for (int x=0; x < ingredients.size(); x++){
            textView.append(ingredients.get(x).getIngredient() + " (" +
                    ingredients.get(x).getQuantity() +
                    " " +
                    ingredients.get(x).getMeasure() + ")" + "\n");

            recipeIngredientsWidgets.add(ingredients.get(x).getIngredient() + " (" +
                    ingredients.get(x).getQuantity() +
                    " " +
                    ingredients.get(x).getMeasure() + ")" + "\n");

        }

        recyclerView = (RecyclerView)rootview.findViewById(R.id.recipe_detail_recycler);
        LinearLayoutManager detailLinearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(detailLinearLayoutManager);

        IngredientsAdapter ingredientsAdapter = new IngredientsAdapter((DetailActivity)getActivity());
        recyclerView.setAdapter(ingredientsAdapter);
        ingredientsAdapter.setIngredientsAdapterData(recipeModels, getContext());

        // update widget
        Log.v(TAG, "calling startBakingService...");
        UpdateBakingService.startBakingService(getContext(), recipeIngredientsWidgets);

        return rootview;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList(SELECTED_RECIPES, recipeModels);
        outState.putString("Title", name);
    }

}
