package com.example.cfung.project_3_baking_app;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by cfung on 3/5/18.
 */

public class Steps implements Parcelable {

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

    public Steps(){}

    protected Steps(Parcel in) {
        id = in.readInt();
        shortDescription = in.readString();
        description = in.readString();
        videoURL = in.readString();
        thumbnailURL = in.readString();
    }

    public int getId(){return this.id;}
    public String getShortDescription(){return this.shortDescription;}
    public String getDescription(){return this.description;}
    public String getVideoURL(){return this.videoURL;}
    public String getThumbnailURL(){return this.thumbnailURL;}

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeString(shortDescription);
        parcel.writeString(description);
        parcel.writeString(videoURL);
        parcel.writeString(thumbnailURL);
    }

    public static final Creator<Steps> CREATOR = new Creator<Steps>() {
        @Override
        public Steps createFromParcel(Parcel in) {
            return new Steps(in);
        }

        @Override
        public Steps[] newArray(int size) {
            return new Steps[size];
        }
    };
}
