package com.example.cfung.project_1_popular_movie.data;

import android.arch.persistence.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by cfung on 11/21/18.
 */

public class ReviewsTypeConverter {

    @TypeConverter
    public static String fromArrayList(ArrayList<String> list) {
        Gson gson = new Gson();
        String json = gson.toJson(list);
        return json;
    }

    @TypeConverter
    public static ArrayList<String> fromString(String value) {
        Type listType = new TypeToken<ArrayList<String>>() {}.getType();
        return new Gson().fromJson(value, listType);
    }
    /*
    public static String fromStringSet(List<String> strings) {

        if (strings==null) {
            return(null);
        }

        StringWriter result=new StringWriter();
        JsonWriter json=new JsonWriter(result);

        try {
            json.beginArray();

            for (String s : strings) {
                json.value(s);
            }

            json.endArray();
            json.close();
        }
        catch (IOException e) {
            Log.e(TAG, "Exception creating JSON", e);
        }

        return(result.toString());

         {
            if (data == null) {
                return Collections.emptyList();
            }

            Type listType = new TypeToken<List<String>>() {}.getType();

            return gson.fromJson(data, listType);
        }

        //@TypeConverter
        public static String someObjectListToString(List<String> someObjects) {
            return gson.toJson(someObjects);
        }
    }*/

}


