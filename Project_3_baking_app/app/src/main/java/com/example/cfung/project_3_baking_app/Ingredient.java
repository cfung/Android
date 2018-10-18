package com.example.cfung.project_3_baking_app;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by cfung on 3/5/18.
 */

public class Ingredient implements Parcelable{

    private int quantity;
    private String measure;
    private String ingredient;

    public Ingredient(int quantity, String measure, String ingredient){
        this.quantity = quantity;
        this.measure = measure;
        this.ingredient = ingredient;
    }

    protected Ingredient(Parcel in) {
        quantity = in.readInt();
        measure = in.readString();
        ingredient = in.readString();
    }

    public static final Creator<Ingredient> CREATOR = new Creator<Ingredient>() {
        @Override
        public Ingredient createFromParcel(Parcel in) {
            return new Ingredient(in);
        }

        @Override
        public Ingredient[] newArray(int size) {
            return new Ingredient[size];
        }
    };

    public int getQuantity(){return this.quantity;}
    public String getMeasure(){return this.measure;}
    public String getIngredient(){return this.ingredient;}


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(quantity);
        parcel.writeString(measure);
        parcel.writeString(ingredient);
    }
}
