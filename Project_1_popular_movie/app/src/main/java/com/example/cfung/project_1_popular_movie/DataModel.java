package com.example.cfung.project_1_popular_movie;

/**
 * Created by cfung on 10/4/17.
 */

public class DataModel {
    String versionName;
    String versionNumber;

    int image; // drawable reference id

    public DataModel(String vName, String vNumber, int image)
    {
        this.versionName = vName;
        this.versionNumber = vNumber;
        this.image = image;
    }
}
