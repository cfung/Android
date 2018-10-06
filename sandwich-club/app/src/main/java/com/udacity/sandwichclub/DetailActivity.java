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

    private TextView origin_tv;

    private TextView also_known_as_text;// = findViewById(R.id.also_known_as_text);
    private TextView ingredients_text;// = findViewById(R.id.ingredients_text);
    private TextView place_of_origin_text;// = findViewById(R.id.place_of_origin_text);
    private TextView description_tv;// = findViewById(R.id.description_tv);
    private TextView ingredients_tv;// = findViewById(R.id.ingredients_tv);
    private TextView also_known_tv;// = findViewById(R.id.also_known_tv);
    private TextView description_text;// = findViewById(R.id.description_text);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ImageView ingredientsIv = findViewById(R.id.image_iv);
        origin_tv = (TextView)findViewById(R.id.origin_tv);
        place_of_origin_text = findViewById(R.id.place_of_origin_text);
        also_known_as_text = findViewById(R.id.also_known_as_text);
        ingredients_text = findViewById(R.id.ingredients_text);

        description_tv = findViewById(R.id.description_tv);
        ingredients_tv = findViewById(R.id.ingredients_tv);
        also_known_tv = findViewById(R.id.also_known_tv);
        description_text = findViewById(R.id.description_text);


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

        //origin_tv.setText(R.string.detail_place_of_origin_label);
        place_of_origin_text.setText(sandwich.getPlaceOfOrigin());

        for (int x = 0; x < sandwich.getAlsoKnownAs().size(); x++){
            also_known_as_text.append(sandwich.getAlsoKnownAs().get(x));
        }
        description_tv.setText(sandwich.getDescription());

        description_text.setText(sandwich.getDescription());

        for (int x = 0; x < sandwich.getIngredients().size(); x++){
            ingredients_tv.append(sandwich.getIngredients().get(x));
        }

    }
}