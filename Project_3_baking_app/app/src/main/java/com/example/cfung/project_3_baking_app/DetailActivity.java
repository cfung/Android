package com.example.cfung.project_3_baking_app;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
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
import butterknife.BindView;
import butterknife.ButterKnife;

import java.util.ArrayList;

/**
 * Created by cfung on 3/5/18.
 */

public class DetailActivity extends AppCompatActivity{

    private RecyclerView.LayoutManager ingredientLayoutManager;
    private RecyclerView ingredientView;
    private IngredientsAdapter ingredientAdapter;
    private TextView ingredientText;
    private StepAdapter stepAdapter;
    private TextView stepText;
    private RecyclerView.LayoutManager stepLayoutManager;
    private RecyclerView stepView;
    private ArrayList<Steps> steps;

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

    private void loadFragment(Fragment fragment){

        FragmentManager fm = getFragmentManager();

        FragmentTransaction fragmentTransaction = fm.beginTransaction();

        //fragmentTransaction.replace(R.id.de);
        fragmentTransaction.commit();
    }

    public void onCreate(Bundle savedInstanceState){

        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail);

        Log.v(TAG, "onCreate starting for DetailActivity");

        // 1. Recipe ingredients
        // 2. Recipe step 1
        // 3. Recipe step 2....
        //TextView ingredientsView = (TextView) findViewById(R.id.ingredients);
        //TextView stepsView = (TextView) findViewById(R.id.step);
        //TextView idView = (TextView) findViewById(R.id.recpieID);

        //ingredientName.setText("testing");

        Intent recipeIntent = getIntent();
        Bundle bundle = recipeIntent.getExtras();
        ArrayList recipes;
        recipes = bundle.getParcelableArrayList("recipe");
        Log.v(TAG, "recipe is..: " + recipes);

        RecipeDetailFragment recipeDetailFragment = new RecipeDetailFragment();
        recipeDetailFragment.setArguments(bundle);
        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.fragment_container, recipeDetailFragment)
                .addToBackStack("recipe_stack")
                .commit();

        if (recipes != null){

            Log.v("Myactivity", "creating new IngredientsAdapter..in DetailActivity");
            //ingredientAdapter = new IngredientsAdapter(recipes.getIngredients(), recipes.getSteps());
            ingredientLayoutManager = new LinearLayoutManager(getApplicationContext());

            //ingredientView = (RecyclerView)findViewById(R.id.recycler_ingredient);
            //ingredientView.setLayoutManager(ingredientLayoutManager);
            //ingredientView.setAdapter(ingredientAdapter);

            //stepView = (RecyclerView)findViewById(R.id.recycler_step);
            //stepAdapter = new StepAdapter(recipes.getSteps());
            stepLayoutManager = new LinearLayoutManager(getApplicationContext());
            //stepView.setLayoutManager(stepLayoutManager);
            //stepView.setAdapter(stepAdapter);

            //TextView textView = (TextView) findViewById(R.id.list_item_ingredient_name);
            //textView.setText("haha");
            //stepView = (RecyclerView)findViewById(R.id.)
            //ingredientText = (TextView) findViewById(R.id.list_item_ingredient_name);
            //ingredientsView.setText(recipe.getIngridients().get(0).toString());
            //stepsView.setText(recipe.getSteps().get(0).toString());
            //Log.v("DetailActivity", "what is ingredient.." + recipes.getIngredients());
            //steps = recipes.getSteps();
            /*for (int x = 0; x < steps.size(); x++){
                Log.v("DetailActivity", "what is step.." + steps.get(x).getShortDescription());
            }*/

            //Log.v("DetailActivity", "what is name.." + recipes.getRecipeName());
            //Log.v("DetailActivity", "what is id.." + recipes.getid());

            //ingredientsView.setText(recipe.getIngredients().get(0).getIngredient());
            //stepsView.setText(recipe.getSteps().get(0).getShortDescription());
            //idView.setText("ID: " + Integer.toString(recipe.getid()));
            //for (int i=0; i < recipes.getIngredients().size(); i++){
            //    Log.v("Myactivity", "size..." + recipes.getIngredients().get(i).toString());
                //ingredientText.setText(recipe.getIngredients().get(i).toString());
            //}


        }

    }
}
