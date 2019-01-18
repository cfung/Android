package com.example.cfung.project_3_baking_app;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toolbar;

import butterknife.BindView;
import butterknife.ButterKnife;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by cfung on 3/5/18.
 */

public class DetailActivity extends AppCompatActivity implements IngredientsAdapter.ListItemClickListener, RecipeStepDetailFragment.ListItemClickListener{

    private RecyclerView.LayoutManager ingredientLayoutManager;
    private RecyclerView ingredientView;
    private IngredientsAdapter ingredientAdapter;
    private TextView ingredientText;
    private StepAdapter stepAdapter;
    private TextView stepText;
    private RecyclerView.LayoutManager stepLayoutManager;
    private RecyclerView stepView;
    private ArrayList<Steps> steps;
    private ArrayList<RecipeModel> recipeModels;
    String recipeName;

    static String RECIPES = "RECIPES";
    static String SELECTED_RECIPES = "SELECTED_RECIPES";
    static String STEPS = "STEPS";
    static String INDEX = "INDEX";
    static String RECIPE_DETAIL = "RECIPE_DETAIL";
    static String RECIPE_STEP_DETAIL = "RECIPE_STEP_DETAIL";


    // Track whether to display a two-pane or single-pane UI
    private boolean mTwoPane;

    private static final String TAG = "DetailActivity";

    //@BindView(R.id.list_item_ingredient_name) TextView ingredientName;

    /*public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState){

        View view = inflater.inflate(R.layout.detail, container, false);

        Intent recipeIntent = getIntent();

        return view;
    }*/

    /*private void loadFragment(Fragment fragment){

        FragmentManager fm = getFragmentManager();

        FragmentTransaction fragmentTransaction = fm.beginTransaction();

        //fragmentTransaction.replace(R.id.de);
        fragmentTransaction.commit();
    }*/

    public void onCreate(Bundle savedInstanceState){

        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail);

        Log.v(TAG, "onCreate starting for DetailActivity");


        // TODO: handle savedInstanceState null and not null
        Log.v(TAG, "savedInstanceState: " + savedInstanceState);

        Intent recipeIntent = getIntent();
        Bundle bundle = recipeIntent.getExtras();
        recipeModels = new ArrayList<>();
        recipeModels = bundle.getParcelableArrayList(RECIPES);
        Log.v(TAG, "recipe is..: " + recipeModels);
        recipeName = recipeModels.get(0).getRecipeName();
        Log.v(TAG, "recipeName is: " + recipeName);

        final RecipeDetailFragment recipeDetailFragment = new RecipeDetailFragment();
        recipeDetailFragment.setArguments(bundle);
        android.support.v4.app.FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.fragment_container, recipeDetailFragment)
                .addToBackStack(RECIPE_STEP_DETAIL)
                .commit();


        if (findViewById(R.id.recipe_detail_layout).getTag() != null && findViewById(R.id.recipe_detail_layout).getTag().equals("tablet-land")) {

            final RecipeStepDetailFragment recipeStepDetailFragment = new RecipeStepDetailFragment();
            recipeStepDetailFragment.setArguments(bundle);
            //FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.fragment_container_2, recipeStepDetailFragment)
                    .addToBackStack(STEPS)
                    .commit();
        }

    }

    @Override
    public void onListItemClick(List<Steps> steps, int index, String recipeName) {

        Log.v(TAG, "onListItemClick() starting..");
        final RecipeStepDetailFragment fragment = new RecipeStepDetailFragment();
        android.support.v4.app.FragmentManager fragmentManager = getSupportFragmentManager();

        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList(STEPS, (ArrayList<Steps>) steps);
        bundle.putInt(INDEX, index);
        bundle.putString("Title", recipeName);
        fragment.setArguments(bundle);

        // TODO:  add getTag with "tablet-land", then uncomment code
        //fragmentManager.beginTransaction().replace(R.id.fragment_container, fragment)
        //        .addToBackStack(RECIPE_STEP_DETAIL).commit();
        Object tag = findViewById(R.id.recipe_detail_layout).getTag();
        if (findViewById(R.id.recipe_detail_layout) != null && tag != null) {

            if (tag.equals("tablet-land")) {
                fragmentManager.beginTransaction().replace(R.id.fragment_container_2, fragment)
                        .addToBackStack(RECIPE_STEP_DETAIL).commit();
            }

            //final RecipeStepDetailFragment stepDetailFragment = new RecipeStepDetailFragment();
            //stepDetailFragment.setArguments(bundle);

            // TODO:  replace R.id.master_list_fragment with fragment_container2
        }//close first if
        else {
            fragmentManager.beginTransaction().replace(R.id.fragment_container, fragment)
                    .addToBackStack(RECIPE_STEP_DETAIL).commit();

        }
    }
}
