package com.example.android.cdocs.data.remote;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

public class ApiClient {

    public static Retrofit getClient() {

        // Get OkHttpClient
        OkHttpClient httpClient = new OkHttpClient.Builder().build();

        // Create a retrofit object
        return new Retrofit.Builder()
                .client(httpClient)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
    }
}
