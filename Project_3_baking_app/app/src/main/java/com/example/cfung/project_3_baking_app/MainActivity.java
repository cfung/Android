package com.example.cfung.project_3_baking_app;

import android.app.LoaderManager;
import android.content.AsyncTaskLoader;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import com.example.cfung.project_3_baking_app.utils.NetworkUtils;

public class MainActivity extends AppCompatActivity implements RecipeAdapter.ListItemClickListener,
        LoaderManager.LoaderCallbacks<ArrayList<RecipeModel>>{

    private static final String TAG = "MyActivity";
    private GridView gridView = null;
    private RecyclerView mainView;
    private RecipeAdapter recipeAdapter = null;
    ArrayList<RecipeModel> AllRecipes = null;
    ArrayList<RecipeModel> result = null;

    static String RECIPES = "RECIPES";
    static String SELECTED_RECIPES = "SELECTED_RECIPES";
    static String STEPS = "STEPS";
    static String INDEX = "INDEX";
    static String RECIPE_DETAIL = "RECIPE_DETAIL";
    static String RECIPE_STEP_DETAIL = "RECIPE_STEP_DETAIL";

    @Override
    public Loader<ArrayList<RecipeModel>> onCreateLoader(int i, Bundle bundle){

        return new AsyncTaskLoader<ArrayList<RecipeModel>>(this) {
            @Override
            public ArrayList<RecipeModel> loadInBackground() {

                ArrayList<RecipeModel> resultList = new ArrayList<>();

                /*String mID = null;

                String myStr = "Bundle{";
                for (String key : bundle.keySet()) {
                    myStr += " " + key + " => " + bundle.get(key) + ";";
                    if (key.equals("id")) {
                        mID = bundle.get(key).toString();
                    }
                }

                myStr += " }Bundle";*/

                String responseKey = null;
                String resultKey = null;

                try {
                    //String reviewURL =
                    String response = NetworkUtils.getResponseFromHttpUrl(new URL(NetworkUtils.BAKING_APP_JSON));

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
                            String quantity = json.getString("quantity");
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

                        resultList.add(recipe);

                    }


                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                for (int x = 0; x < resultList.size(); x++){
                    Log.v(TAG, "before returning resultList...: " + resultList.get(x).getRecipeName());
                }
                return resultList;
            }
        }; // end loadInBackground

    }

    @Override
    public void onLoadFinished(Loader<ArrayList<RecipeModel>> loader, ArrayList<RecipeModel> recipeModels) {

        Log.v(TAG, "onLoadFinished is getting called...");
        if (recipeModels.size() > 0) {

            mainView.setAdapter(new RecipeAdapter(recipeModels));
        }

    }

    @Override
    public void onLoaderReset(Loader<ArrayList<RecipeModel>> loader) {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.v(TAG, "onCreate");

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onListItemClick(RecipeModel clickedItemIndex) {
        Log.v(TAG, "onListItemClick()!");
        Bundle recipeBundle = new Bundle();
        ArrayList<RecipeModel> recipeModel = new ArrayList<>();
        recipeModel.add(clickedItemIndex);
        recipeBundle.putParcelableArrayList(SELECTED_RECIPES, recipeModel);
        final Intent recipeIntent = new Intent(this, DetailActivity.class);
        recipeIntent.putExtras(recipeBundle);
        startActivity(recipeIntent);
    }
}
