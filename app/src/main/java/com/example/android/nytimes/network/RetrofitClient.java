package com.example.android.nytimes.network;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Gauri Gadkari on 6/15/17.
 */

public class RetrofitClient {
    private static RetrofitClient instance = null;
    public static final String BASE_URL = "https://api.nytimes.com/svc/search/v2/";
    public static final String API_KEY = "api-key";
    public static final String API_KEY_VALUE = "d31fe793adf546658bd67e2b6a7fd11a";

    // Keep your services here, build them in buildRetrofit method later
    private ApiInterface apiInterface;
    private Retrofit retrofit;

    public ApiInterface getApiInterface() {
        return apiInterface;
    }

    public Retrofit getRetrofit() {
        return retrofit;
    }

    public static RetrofitClient getInstance() {
        if (instance == null) {
            instance = new RetrofitClient();
        }
        return instance;
    }

    // Build retrofit once when creating a single instance
    private RetrofitClient() {
        // Implement a method to build your retrofit
        buildRetrofit();
    }

    private void buildRetrofit() {
        this.retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        // Build your services once
        this.apiInterface = retrofit.create(ApiInterface.class);
    }
}
