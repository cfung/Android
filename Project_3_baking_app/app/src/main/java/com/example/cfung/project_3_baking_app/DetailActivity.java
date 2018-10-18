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

        // 1. Recipe ingredients
        // 2. Recipe step 1
        // 3. Recipe step 2....
        //TextView ingredientsView = (TextView) findViewById(R.id.ingredients);
        //TextView stepsView = (TextView) findViewById(R.id.step);
        //TextView idView = (TextView) findViewById(R.id.recpieID);



        Intent recipeIntent = getIntent();
        final RecipeModel recipe = recipeIntent.getParcelableExtra("recipe");

        if (recipe != null){

            Log.v("Myactivity", "creating new IngredientsAdapter..in DetailActivity");
            ingredientAdapter = new IngredientsAdapter(recipe.getIngredients(), recipe.getSteps());
            ingredientLayoutManager = new LinearLayoutManager(getApplicationContext());

            ingredientView = (RecyclerView)findViewById(R.id.recycler_ingredient);
            ingredientView.setLayoutManager(ingredientLayoutManager);
            ingredientView.setAdapter(ingredientAdapter);

            stepView = (RecyclerView)findViewById(R.id.recycler_step);
            stepAdapter = new StepAdapter(recipe.getSteps());
            stepLayoutManager = new LinearLayoutManager(getApplicationContext());
            stepView.setLayoutManager(stepLayoutManager);
            stepView.setAdapter(stepAdapter);

            TextView textView = (TextView) findViewById(R.id.list_item_ingredient_name);
            textView.setText("haha");
            //stepView = (RecyclerView)findViewById(R.id.)
            //ingredientText = (TextView) findViewById(R.id.list_item_ingredient_name);
            //ingredientsView.setText(recipe.getIngridients().get(0).toString());
            //stepsView.setText(recipe.getSteps().get(0).toString());
            Log.v("DetailActivity", "what is ingredient.." + recipe.getIngredients());
            Log.v("DetailActivity", "what is step.." + recipe.getSteps());
            Log.v("DetailActivity", "what is name.." + recipe.getRecipeName());
            Log.v("DetailActivity", "what is id.." + recipe.getid());

            //ingredientsView.setText(recipe.getIngredients().get(0).getIngredient());
            //stepsView.setText(recipe.getSteps().get(0).getShortDescription());
            //idView.setText("ID: " + Integer.toString(recipe.getid()));
            for (int i=0; i < recipe.getIngredients().size(); i++){
                Log.v("Myactivity", "size..." + recipe.getIngredients().get(i).toString());
                //ingredientText.setText(recipe.getIngredients().get(i).toString());
            }


        }

    }
}
