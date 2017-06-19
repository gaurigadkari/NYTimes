package com.example.android.nytimes.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;

@Parcel
public class Multimedium {

    @SerializedName("width")
    @Expose
    int width;
    @SerializedName("url")
    @Expose
    String url;
    @SerializedName("rank")
    @Expose
    int rank;
    @SerializedName("height")
    @Expose
    int height;
    @SerializedName("subtype")
    @Expose
    String subtype;
    @SerializedName("type")
    @Expose
    String type;

    public Multimedium() {
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public String getUrl() {
        return "http://www.nytimes.com/"+url;
    }

    public int getRank() {
        return rank;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public String getSubtype() {
        return subtype;
    }

    public String getType() {
        return type;
    }

}