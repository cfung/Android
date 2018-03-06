package com.example.cfung.project_3_baking_app;

import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by cfung on 3/1/18.
 */

public class RecipeAdapter extends ArrayAdapter<RecipeModel> {

    private static final String TAG = "MyActivity";

    String BASE_POSTER_URL= "http://image.tmdb.org/t/p/w185";

    ArrayList<RecipeModel> AllRecipes = new ArrayList<RecipeModel>();

    //completed 1: override this default constructor?
    public RecipeAdapter(Activity context, int resources, ArrayList<RecipeModel> recipes){
        super(context, resources,  recipes);
        this.AllRecipes =  recipes;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){

        RecipeModel mRecipe = getItem(position);

        if (convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item_recipe, parent, false);
        }

        /*
        ImageView imageView = (ImageView) convertView.findViewById(R.id.list_item_icon);
        Picasso.with(getContext()).setLoggingEnabled(true);

        String moviePostPath1 = "http://image.tmdb.org/t/p/w185/"+mMovies.getMovieLink();
        Picasso.with(getContext())
                .load(moviePostPath1)
                .placeholder(R.drawable.placeholder)
                .into(imageView);

        TextView movieNameView = (TextView) convertView.findViewById(R.id.list_item_movie_name);
        movieNameView.setText(AllMovies.get(position).getMovieName());
        */

        TextView recipeNameView = (TextView) convertView.findViewById(R.id.list_item_recipe_name);
        Log.v(TAG, "what is getRecipeName: " + AllRecipes.get(position).getRecipeName());
        recipeNameView.setText(AllRecipes.get(position).getRecipeName());

        return convertView;
    }


}
