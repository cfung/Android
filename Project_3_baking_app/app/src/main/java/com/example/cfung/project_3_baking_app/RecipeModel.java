package com.example.cfung.project_3_baking_app;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * Created by cfung on 3/1/18.
 */

public class RecipeModel implements Parcelable{

    private int id;
    private String name;
    private ArrayList<String> ingredients;
    private ArrayList<String> steps;
    private int servings;
    private String image;

    public RecipeModel(int id, String name, ArrayList<String> ingredients, ArrayList<String> steps,
                       int servings, String image){

        this.id = id;
        this.name = name;
        this.ingredients = ingredients;
        this.steps = steps;
        this.servings = servings;
        this.image = image;

    }

    protected RecipeModel(Parcel in) {
        id = in.readInt();
        name = in.readString();
        ingredients = in.createStringArrayList();
        steps = in.createStringArrayList();
        servings = in.readInt();
        image = in.readString();
    }

    public static final Creator<RecipeModel> CREATOR = new Creator<RecipeModel>() {
        @Override
        public RecipeModel createFromParcel(Parcel in) {
            return new RecipeModel(in);
        }

        @Override
        public RecipeModel[] newArray(int size) {
            return new RecipeModel[size];
        }
    };

    public int getid(){ return this.id;}
    public String getRecipeName(){ return this.name;}
    public ArrayList<String> getIngridients(){ return this.ingredients;}
    public ArrayList<String> getSteps(){ return this.steps;}
    public int getServings(){ return this.servings;}
    public String getImage(){ return this.image;}

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeString(name);
        parcel.writeStringList(ingredients);
        parcel.writeStringList(steps);
        parcel.writeInt(servings);
        parcel.writeString(image);


    }
}
