package com.example.cfung.project_3_baking_app;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

import com.example.cfung.project_3_baking_app.utils.NetworkUtils;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MyActivity";
    private GridView gridView = null;
    private RecipeAdapter recipeAdapter = null;
    ArrayList<RecipeModel> AllRecipes = null;
    ArrayList<RecipeModel> result = null;

    // AsyncTask to perform network operation in a separate thread than mainUI thread
    public class RecipeQueryTask extends AsyncTask<String, Void, ArrayList<RecipeModel>> {

        @Override
        protected ArrayList<RecipeModel> doInBackground(String... urls) {

            String response = null;
            ArrayList<RecipeModel> resultslist = new ArrayList<RecipeModel>();

            try {

                URL url = new URL(NetworkUtils.BAKING_APP_JSON);

                response = NetworkUtils.getResponseFromHttpUrl(url);

                JSONArray recipeResults = new JSONArray(response);

                //JSONArray recipeResults = results.getJSONArray("");
                Log.v(TAG, "what is size of recipeResutls..." + recipeResults.length());
                for (int i = 0; i < recipeResults.length(); i++){
                    //ArrayList<String> movieArray = new ArrayList<String>();
                    JSONObject jsonobject = recipeResults.getJSONObject(i);
                    int id = jsonobject.getInt("id");
                    String name = jsonobject.getString("name");
                    int servings = jsonobject.getInt("servings");
                    String image = jsonobject.getString("image");
                    ArrayList<Ingredient> ingredientsList = new ArrayList<>();
                    JSONArray ingredients = jsonobject.getJSONArray("ingredients");

                    for (int x = 0; x < ingredients.length(); x++){
                        JSONObject json = ingredients.getJSONObject(x);
                        int quantity = json.getInt("quantity");
                        String measure = json.getString("measure");
                        String ingredientStr = json.getString("ingredient");

                        Ingredient ingredient = new Ingredient(quantity, measure, ingredientStr);
                        ingredientsList.add(ingredient);

                    }

                    ArrayList<Steps> stepsList = new ArrayList<>();
                    JSONArray steps = jsonobject.getJSONArray("steps");

                    for (int y = 0; y < steps.length(); y++){

                        JSONObject jsonStep = steps.getJSONObject(y);

                        //ArrayList<RecipeModel.Steps> step = new ArrayList<>();

                        int stepId = jsonStep.getInt("id");
                        //stepsList.add(Integer.toString(stepId));
                        String shortDesc = jsonStep.getString("shortDescription");
                        String desc = (jsonStep.getString("description"));
                        String videoURL = jsonStep.getString("videoURL");
                        String thumbnailURL = jsonStep.getString("thumbnailURL");
                        Steps step = new Steps(stepId, shortDesc, desc, videoURL, thumbnailURL);
                        stepsList.add(step);

                    }

                    ArrayList<String> mReview = new ArrayList<String>();
                    String trailer = null;

                    // completed:  add recipe recipeArray
                    RecipeModel recipe = new RecipeModel(id, name, ingredientsList, stepsList, servings, image);

                    resultslist.add(recipe);

                }

            } catch (IOException e){
                e.printStackTrace();
            } catch(JSONException e){
                e.printStackTrace();
            }

            return resultslist;
        }

        @Override
        protected void onPostExecute(ArrayList<RecipeModel> result){
            super.onPostExecute(result);

            Log.v(TAG, "what is onPostExecute result: " + result);

            if(result != null){

                recipeAdapter.clear();
                for (int i=0; i<result.size();i++){
                    Log.v(TAG, "what is result..." + result.get(i));
                    recipeAdapter.add(result.get(i));
                }
            }
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.v(TAG, "onCreate");
        GridView gridView = (GridView) findViewById(R.id.recipe_grid);

        AllRecipes = new ArrayList<RecipeModel>();
        recipeAdapter = new RecipeAdapter(MainActivity.this, 0, AllRecipes);
        gridView.setAdapter(recipeAdapter);
        new RecipeQueryTask().execute();


        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

                String selectedItem = adapterView.getItemAtPosition(position).toString();

                Class detailActivity = DetailActivity.class;

                Context context = MainActivity.this;
                Intent startDetailActivityIntent = new Intent(context, detailActivity);
                startDetailActivityIntent.putExtra("recipe", AllRecipes.get(position));
                startDetailActivityIntent.putExtra("id", AllRecipes.get(position).getid());
                startDetailActivityIntent.putExtra("name", AllRecipes.get(position).getRecipeName());
                startDetailActivityIntent.putExtra("ingridients", AllRecipes.get(position).getIngredients());
                startDetailActivityIntent.putExtra("steps", AllRecipes.get(position).getSteps());
                startDetailActivityIntent.putExtra("servings", AllRecipes.get(position).getServings());
                startDetailActivityIntent.putExtra("image", AllRecipes.get(position).getImage());
                startActivity(startDetailActivityIntent);
            }
        });

    }



}
