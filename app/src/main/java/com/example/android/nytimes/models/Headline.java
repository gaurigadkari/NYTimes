package com.example.android.nytimes.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;

/**
 * Created by Gauri Gadkari on 6/15/17.
 */

@Parcel
public class Headline {

    @SerializedName("main")
    @Expose
    String main;
    @SerializedName("print_headline")
    @Expose
    String printHeadline;
    @SerializedName("kicker")
    @Expose
    String kicker;
    @SerializedName("content_kicker")
    @Expose
    String contentKicker;

    public Headline() {
    }

    public String getMain() {
        return main;
    }

    public String getPrintHeadline() {
        return printHeadline;
    }

    public String getKicker() {
        return kicker;
    }

    public String getContentKicker() {
        return contentKicker;
    }
    
}


