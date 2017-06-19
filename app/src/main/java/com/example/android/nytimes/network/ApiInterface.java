package com.example.android.nytimes.network;

import java.util.Map;
import com.example.android.nytimes.models.ResponseBody;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

public interface ApiInterface {
    @GET("articlesearch.json")
    Call<ResponseBody> getSearchResults(@Query("api-key") String apiKey, @Query("q") String query, @Query("page") int page);

    @GET("articlesearch.json")
    Call<ResponseBody> getSearchResultsWithFilter(@QueryMap Map<String, String> options);
}
