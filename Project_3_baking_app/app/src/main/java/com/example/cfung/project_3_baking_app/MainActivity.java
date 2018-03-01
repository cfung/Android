package com.example.cfung.project_3_baking_app;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
    public class MovieQueryTask extends AsyncTask<String, Void, ArrayList<RecipeModel>> {

        @Override
        protected ArrayList<RecipeModel> doInBackground(String... urls) {

            String response = null;
            ArrayList<RecipeModel> resultslist = new ArrayList<RecipeModel>();

            try {

                URL url = new URL(urls[0]);

                response = NetworkUtils.getResponseFromHttpUrl(url);

                JSONObject results = new JSONObject(response);

                JSONArray movieResults = results.getJSONArray("results");
                for (int i=0; i<movieResults.length(); i++){
                    //ArrayList<String> movieArray = new ArrayList<String>();
                    JSONObject jsonobject = movieResults.getJSONObject(i);
                    String id = jsonobject.getString("id");
                    String poster_path = jsonobject.getString("poster_path");
                    String title = jsonobject.getString("original_title");
                    String popularity = jsonobject.getString("popularity");
                    String overview = jsonobject.getString("overview");
                    String vote_average = jsonobject.getString("vote_average");
                    String release_date = jsonobject.getString("release_date");

                    ArrayList<String> mReview = new ArrayList<String>();
                    String trailer = null;

                    // completed:  add recipe recipeArray
                    //RecipeModel movie = new RecipeModel(title, popularity, poster_path, overview, vote_average, release_date, id, mReview, trailer );
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
                    recipeAdapter.add(result.get(i));
                }
            }
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
