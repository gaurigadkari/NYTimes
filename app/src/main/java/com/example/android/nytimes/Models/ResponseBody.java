package com.example.android.nytimes.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Gauri Gadkari on 6/15/17.
 */

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

    public void setResponse(Response response) {
        this.response = response;
    }
}
