package com.example.android.nytimes.models;

/**
 * Created by Gauri Gadkari on 6/15/17.
 */

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;

import java.util.Date;
import java.util.List;

@Parcel
public class Article {
    @SerializedName("web_url")
    @Expose
    String webUrl;
    @SerializedName("snippet")
    @Expose
    String snippet;
    @SerializedName("headline")
    @Expose
    Headline headline;
    @SerializedName("multimedia")
    @Expose
    List<Multimedium> multimedia = null;
    @SerializedName("section_name")
    @Expose
    String tag;
    @SerializedName("pub_date")
    @Expose
    String publishDate;

    public Article() {
    }

    public String getWebUrl() {
        return webUrl;
    }

    public String getSnippet() {
        return snippet;
    }

    public Headline getHeadline() {
        return headline;
    }


    public void setWebUrl(String webUrl) {
        this.webUrl = webUrl;
    }

    public List<Multimedium> getMultimedia() {
        return multimedia;
    }

    public void setMultimedia(List<Multimedium> multimedia) {
        this.multimedia = multimedia;
    }

    public String getTag() {
        return tag;
    }

    public String getPublishDate() {
        return publishDate;
    }
}
