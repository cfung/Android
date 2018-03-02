package com.example.cfung.project_3_baking_app;

import java.util.ArrayList;

/**
 * Created by cfung on 3/1/18.
 */

public class RecipeModel {

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

    public String getRecipeName(){ return this.name;}
}
