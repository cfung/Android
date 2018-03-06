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
    private ArrayList<Ingredient> ingredients;
    private ArrayList<Steps> steps;
    private int servings;
    private String image;

    public RecipeModel(int id, String name, ArrayList<Ingredient> ingredients, ArrayList<Steps> steps,
                       int servings, String image){

        this.id = id;
        this.name = name;
        this.ingredients = ingredients;
        this.steps = steps;
        this.servings = servings;
        this.image = image;

    }

    /*
    public class Ingredients{

        private int quantity;
        private String measure;
        private String ingredient;

        public Ingredients(int quantity, String measure, String ingredient){
            this.quantity = quantity;
            this.measure = measure;
            this.ingredient = ingredient;
        }
        public int getQuantity(){return this.quantity;}
        public String getMeasure(){return this.measure;}
        public String getIngredient(){return this.ingredient;}
    }

    public class Steps {

        private int id;
        private String shortDescription;
        private String description;
        private String videoURL;
        private String thumbnailURL;

        public Steps(int id, String shortDescription, String description, String videoURL,
                     String thumbnailURL){

            this.id = id;
            this.shortDescription = shortDescription;
            this.description = description;
            this.videoURL = videoURL;
            this.thumbnailURL = thumbnailURL;

        }

        public int getId(){return this.id;}
        public String getShortDescription(){return this.shortDescription;}
        public String getDescription(){return this.description;}
        public String getVideoURL(){return this.videoURL;}
        public String getThumbnailURL(){return this.thumbnailURL;}

    }*/

    protected RecipeModel(Parcel in) {
        id = in.readInt();
        name = in.readString();
        servings = in.readInt();
        image = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(name);
        dest.writeInt(servings);
        dest.writeString(image);
    }

    @Override
    public int describeContents() {
        return 0;
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
    public ArrayList<Ingredient> getIngridients(){ return this.ingredients;}
    public ArrayList<Steps> getSteps(){ return this.steps;}
    public int getServings(){ return this.servings;}
    public String getImage(){ return this.image;}


}
