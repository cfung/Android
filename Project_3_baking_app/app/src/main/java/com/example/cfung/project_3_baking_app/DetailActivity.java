package com.example.cfung.project_3_baking_app;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
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
    ArrayList<String> ingredientList = new ArrayList<>();

    public void onCreate(Bundle savedInstanceState){

        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail);

        // 1. Recipe ingredients
        // 2. Recipe step 1
        // 3. Recipe step 2....
        //TextView ingredientsView = (TextView) findViewById(R.id.ingredients);
        //TextView stepsView = (TextView) findViewById(R.id.step);
        //TextView idView = (TextView) findViewById(R.id.recpieID);
        ingredientLayoutManager = new LinearLayoutManager(getApplicationContext());
        Log.v("Myactivity", "creating new IngredientsAdapter..");
        ingredientAdapter = new IngredientsAdapter(ingredientList);
        ingredientView = (RecyclerView)findViewById(R.id.recycler_ingredient);
        ingredientView.setLayoutManager(ingredientLayoutManager);
        ingredientView.setAdapter(ingredientAdapter);
        ingredientText = (TextView) findViewById(R.id.list_item_ingredient_name);


        Intent recipeIntent = getIntent();
        final RecipeModel recipe = recipeIntent.getParcelableExtra("recipe");

        if (recipe != null){

            // TODO:  currently it's using Array's first element
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
                ingredientText.setText(recipe.getIngredients().get(i).toString());
            }


        }

    }
}
