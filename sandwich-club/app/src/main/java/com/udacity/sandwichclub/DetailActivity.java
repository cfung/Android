package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

import org.json.JSONException;

import java.io.IOException;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;
    private static final String TAG = DetailActivity.class.getSimpleName();

    TextView origin_tv = findViewById(R.id.origin_tv);
    TextView also_known_as_text = findViewById(R.id.also_known_as_text);
    TextView ingredients_text = findViewById(R.id.ingredients_text);
    TextView place_of_origin_text = findViewById(R.id.place_of_origin_text);
    TextView description_tv = findViewById(R.id.description_tv);
    TextView ingredients_tv = findViewById(R.id.ingredients_tv);
    TextView also_known_tv = findViewById(R.id.also_known_tv);
    TextView description_text = findViewById(R.id.description_text);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ImageView ingredientsIv = findViewById(R.id.image_iv);


        Intent intent = getIntent();
        if (intent == null) {
            closeOnError();
        }

        int position = intent.getIntExtra(EXTRA_POSITION, DEFAULT_POSITION);
        if (position == DEFAULT_POSITION) {
            // EXTRA_POSITION not found in intent
            closeOnError();
            return;
        }

        String[] sandwiches = getResources().getStringArray(R.array.sandwich_details);
        String json = sandwiches[position];
        try{

            Sandwich sandwich = JsonUtils.parseSandwichJson(json);

            if (sandwich == null) {
                // Sandwich data unavailable
                closeOnError();
                return;
            }
            populateUI(sandwich);
            Picasso.with(this)
                    .load(sandwich.getImage())
                    .into(ingredientsIv);
            Log.v(TAG, "getMainName: " + sandwich.getMainName());
            setTitle(sandwich.getMainName());

        }catch (Exception e){
            e.printStackTrace();
        }

    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI(Sandwich sandwich) {

        origin_tv.setText(sandwich.getPlaceOfOrigin());
        //also_known_as_text.setText(sandwich.getAlsoKnownAs());
        //ingredients_text.setText(sandwich.getIngredients());
        place_of_origin_text.setText(sandwich.getPlaceOfOrigin());
        description_tv.setText(sandwich.getDescription());
        //ingredients_tv.setText(sandwich.getIngredients());
        //also_known_tv.setText(sandwich.getAlsoKnownAs());
        description_text.setText(sandwich.getDescription());

    }
}
