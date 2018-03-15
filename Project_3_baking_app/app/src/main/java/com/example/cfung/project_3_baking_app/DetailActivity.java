package com.example.cfung.project_3_baking_app;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

/**
 * Created by cfung on 3/5/18.
 */

public class DetailActivity extends AppCompatActivity{


    public void onCreate(Bundle savedInstanceState){

        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail);

        // 1. Recipe ingredients
        // 2. Recipe step 1
        // 3. Recipe step 2....
        TextView ingredientsView = (TextView) findViewById(R.id.ingredients);
        TextView stepsView = (TextView) findViewById(R.id.step);

        Intent recipeIntent = getIntent();
        final RecipeModel recipe = recipeIntent.getParcelableExtra("recipe");

        if (recipe != null){

            // TODO:  currently it's using Array's first element
            //ingredientsView.setText(recipe.getIngridients().get(0).toString());
            //stepsView.setText(recipe.getSteps().get(0).toString());
            Log.v("DetailActivity", "what is ingridient.." + recipe.getIngridients());
            Log.v("DetailActivity", "what is step.." + recipe.getSteps());
            Log.v("DetailActivity", "what is name.." + recipe.getRecipeName());

        }

    }
}
