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
    private static final String NAME = "name";
    private static final String MAIN_NAME = "mainName";
    private static final String ALSO_KNOWN_AS = "alsoKnownAs";
    private static final String INGREDIENTS = "ingredients";
    private static final String PLACE_OF_ORIGIN = "placeOfOrigin";
    private static final String DESCRIPTION = "description";
    private static final String IMAGE = "image";

    public static Sandwich parseSandwichJson(String json) throws JSONException{


        JSONObject sandwichJson = new JSONObject(json);
        JSONObject name = sandwichJson.getJSONObject(NAME);
        String mainName = name.getString(MAIN_NAME);
        JSONArray alsoKnownAs = name.getJSONArray(ALSO_KNOWN_AS);
        JSONArray ingredients = sandwichJson.getJSONArray(INGREDIENTS);
        List<String> alsoKnownAsList = new ArrayList<String>();
        List<String> ingredientsList = new ArrayList<String>();
        for (int i=0; i < alsoKnownAs.length(); i++){

            alsoKnownAsList.add(alsoKnownAs.get(i).toString());

        }
        String placeOfOrigin = sandwichJson.getString(PLACE_OF_ORIGIN);
        String description = sandwichJson.getString(DESCRIPTION);
        String image = sandwichJson.getString(IMAGE);

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