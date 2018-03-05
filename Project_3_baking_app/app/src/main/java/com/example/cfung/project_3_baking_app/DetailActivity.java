package com.example.cfung.project_3_baking_app;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by cfung on 3/5/18.
 */

public class DetailActivity extends AppCompatActivity{


    public void onCreate(Bundle savedInstanceState){

        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail);

        Intent recipeIntent = getIntent();
        final RecipeModel recipe = recipeIntent.getParcelableExtra("recipe");

        if (recipe != null){



        }

    }
}
