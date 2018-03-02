package com.example.cfung.project_3_baking_app;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
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
                    ArrayList<String> ingredientsList = new ArrayList<>();
                    JSONArray ingredients = jsonobject.getJSONArray("ingredients");

                    for (int x = 0; x < ingredients.length(); x++){
                        JSONObject json = ingredients.getJSONObject(x);
                        ingredientsList.add(json.getString("quantity"));
                        ingredientsList.add(json.getString("measure"));
                        ingredientsList.add(json.getString("ingredient"));
                    }

                    ArrayList<String> stepsList = new ArrayList<>();
                    JSONArray steps = jsonobject.getJSONArray("steps");

                    for (int y = 0; y < steps.length(); y++){

                        JSONObject jsonStep = steps.getJSONObject(y);
                        int stepId = jsonStep.getInt("id");
                        /*if(stepId instanceof Integer){
                            Log.v(TAG, "stepID is an integer!!");
                        }else{
                            Log.v(TAG, "stepID is NOT an integer!");
                        }*/
                        stepsList.add(Integer.toString(stepId));
                        stepsList.add(jsonStep.getString("shortDescription"));
                        stepsList.add(jsonStep.getString("description"));
                        stepsList.add(jsonStep.getString("videoURL"));
                        stepsList.add(jsonStep.getString("thumbnailURL"));

                    }

                    //String overview = jsonobject.getString("overview");
                    //String vote_average = jsonobject.getString("vote_average");
                    //String release_date = jsonobject.getString("release_date");

                    ArrayList<String> mReview = new ArrayList<String>();
                    String trailer = null;

                    // completed:  add recipe recipeArray
                    RecipeModel movie = new RecipeModel(id, name, ingredientsList, stepsList, servings, image);
                    //resultslist.add(movie);

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

        GridView gridView = (GridView) findViewById(R.id.recipe_grid);

        AllRecipes = new ArrayList<RecipeModel>();
        recipeAdapter = new RecipeAdapter(MainActivity.this, 0, AllRecipes);
        gridView.setAdapter(recipeAdapter);
        new RecipeQueryTask().execute();
    }
}
