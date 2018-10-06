package com.udacity.sandwichclub.utils;

import android.util.Log;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {

    private static final String TAG = JsonUtils.class.getSimpleName();

    public static Sandwich parseSandwichJson(String json) throws JSONException{
        /*
        private String mainName;
        private List<String> alsoKnownAs = null;
        private String placeOfOrigin;
        private String description;
        private String image;
        private List<String> ingredients = null;
         */


        JSONObject sandwichJson = new JSONObject(json);
        JSONObject name = sandwichJson.getJSONObject("name");
        String mainName = name.getString("mainName");
        JSONArray alsoKnownAs = name.getJSONArray("alsoKnownAs");
        JSONArray ingredients = sandwichJson.getJSONArray("ingredients");
        List<String> alsoKnownAsList = new ArrayList<String>();
        List<String> ingredientsList = new ArrayList<String>();
        for (int i=0; i < alsoKnownAs.length(); i++){

            alsoKnownAsList.add(alsoKnownAs.get(i).toString());

        }
        String placeOfOrigin = sandwichJson.getString("placeOfOrigin");
        String description = sandwichJson.getString("description");
        String image = sandwichJson.getString("image");

        for (int i=0; i < ingredients.length(); i++){

            ingredientsList.add(ingredients.get(i).toString());

        }

        Log.v(TAG, "json name:" + name);
        Log.v(TAG, "placeOfOrigin:" + placeOfOrigin);
        Log.v(TAG, "description:" + description);
        Log.v(TAG, "image:" + image);
        Log.v(TAG, "alsoknownAs:" + alsoKnownAsList);
        Log.v(TAG, "ingredients:" + ingredientsList);
        Sandwich sandwich = new Sandwich(mainName, alsoKnownAsList, placeOfOrigin, description, image, ingredientsList);

        Log.v(TAG, "Json " + json);

        return sandwich;
    }
}