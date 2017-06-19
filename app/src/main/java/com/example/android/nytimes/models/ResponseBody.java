package com.example.android.nytimes.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ResponseBody {
    @SerializedName("response")
    @Expose
    private Response response;
    @SerializedName("status")
    @Expose
    private String status;

    public Response getResponse() {
        return response;
    }
    public String getStatus() {
        return status;
    }

}
