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

        // 1. Recipe ingredients
        // 2. Recipe step 1
        // 3. Recipe step 2....
        //TextView ingredientsView = (TextView) findViewById(R.id.ingredients);
        //TextView stepsView = (TextView) findViewById(R.id.step);
        //TextView idView = (TextView) findViewById(R.id.recpieID);

        //ingredientName.setText("testing");

        Log.v(TAG, "savedInstanceState: " + savedInstanceState);

        Intent recipeIntent = getIntent();
        Bundle bundle = recipeIntent.getExtras();
        recipeModels = new ArrayList<>();
        recipeModels = bundle.getParcelableArrayList("recipe");
        Log.v(TAG, "recipe is..: " + recipeModels);
        recipeName = recipeModels.get(0).getRecipeName();
        Log.v(TAG, "recipeName is: " + recipeName);

        final RecipeDetailFragment recipeDetailFragment = new RecipeDetailFragment();
        recipeDetailFragment.setArguments(bundle);
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.fragment_container, recipeDetailFragment)
                .addToBackStack("recipe_stack")
                .commit();



        if (recipeModels != null){

            Log.v(TAG, "recipes NOT null");

            Log.v(TAG, "creating new IngredientsAdapter..in DetailActivity");
            //ingredientAdapter = new IngredientsAdapter(recipes.getIngredients(), recipes.getSteps());
            //ingredientLayoutManager = new LinearLayoutManager(getApplicationContext());

            //ingredientView = (RecyclerView)findViewById(R.id.recycler_ingredient);
            //ingredientView.setLayoutManager(ingredientLayoutManager);
            //ingredientView.setAdapter(ingredientAdapter);

            //stepView = (RecyclerView)findViewById(R.id.recycler_step);
            //stepAdapter = new StepAdapter(recipes.getSteps());
            //stepLayoutManager = new LinearLayoutManager(getApplicationContext());
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

    @Override
    public void onListItemClick(List<Steps> steps, int index, String recipeName) {

        Log.v(TAG, "onListItemClick() starting..");
        final RecipeStepDetailFragment fragment = new RecipeStepDetailFragment();
        android.support.v4.app.FragmentManager fragmentManager = getSupportFragmentManager();

        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList("steps", (ArrayList<Steps>) steps);
        bundle.putInt("index", index);
        bundle.putString("Title", recipeName);
        fragment.setArguments(bundle);



        // TODO:  add getTag with "tablet-land", then uncomment code
        fragmentManager.beginTransaction().replace(R.id.fragment_container, fragment)
                .addToBackStack("step_detail").commit();
        /*if (findViewById(R.id.recipe_detail_layout) != null && findViewById(R.id.recipe_detail_layout).getTag().equals("tablet-land")) {

            final RecipeStepDetailFragment stepDetailFragment = new RecipeStepDetailFragment();
            stepDetailFragment.setArguments(bundle);

            // TODO:  replace R.id.master_list_fragment with fragment_container2
            fragmentManager.beginTransaction().replace(R.id.master_list_fragment, fragment)
                    .addToBackStack("step_detail").commit();
        } else {
            fragmentManager.beginTransaction().replace(R.id.fragment_container, fragment)
                    .addToBackStack("step_detail").commit();
        }*/
    }
}
