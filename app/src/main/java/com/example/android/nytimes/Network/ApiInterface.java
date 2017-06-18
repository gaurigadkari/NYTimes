package com.example.android.nytimes.Network;

import java.util.Map;
import com.example.android.nytimes.Models.ResponseBody;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

/**
 * Created by Gauri Gadkari on 6/15/17.
 */

public interface ApiInterface {
    @GET("articlesearch.json")
    Call<ResponseBody> getSearchResults(@Query("api-key") String apiKey, @Query("q") String query, @Query("page") int page);

    @GET("articlesearch.json")
    Call<ResponseBody> getSearchResultsWithFilter(@QueryMap Map<String, String> options);
}
