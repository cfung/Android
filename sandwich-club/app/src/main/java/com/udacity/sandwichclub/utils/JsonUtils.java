package com.udacity.sandwichclub.utils;

import android.util.Log;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONException;
import org.json.JSONObject;

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
        List<String> alsoKnownAs = null;
        /*for (int i=0; i < name.getJSONArray("alsoKnownAs").length(); i++){
            alsoKnownAs.add(name.getJSONArray("alsoKnownAs")[i]);

        }*/
        String placeOfOrigin = sandwichJson.getString("placeOfOrigin");
        String description = sandwichJson.getString("description");
        String image = sandwichJson.getString("image");


        Log.v(TAG, "json name:" + name);
        Log.v(TAG, "placeOfOrigin:" + placeOfOrigin);
        Log.v(TAG, "description:" + description);
        Log.v(TAG, "image:" + image);
        Sandwich sandwich = null;

        Log.v(TAG, "Json " + json);

        return sandwich;
    }
}
