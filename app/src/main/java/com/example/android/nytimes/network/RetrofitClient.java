package com.example.android.nytimes.network;

import com.example.android.nytimes.utils.Constants;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Gauri Gadkari on 6/15/17.
 */

public class RetrofitClient {
    //TODO fix this class
    private static RetrofitClient instance = null;
    private Retrofit retrofit;

    public Retrofit getRetrofit() {
        return retrofit;
    }

    public RetrofitClient() {
        retrofit = new Retrofit.Builder().baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public static RetrofitClient getInstance() {
        if (instance == null) {
            instance = new RetrofitClient();
        }

        return instance;
    }
}
