package com.robotnec.reddit.core.web;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.robotnec.reddit.core.mvp.model.TopFeedListing;
import com.robotnec.reddit.core.web.deserializer.TopFeedDeserializer;
import com.robotnec.reddit.core.web.interceptor.NetworkAvailabilityInterceptor;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class RedditClient {
    private static final String BASE_URL = "https://api.reddit.com";
    private static final Gson GSON = new GsonBuilder()
            .registerTypeAdapter(TopFeedListing.class, new TopFeedDeserializer())
            .create();

    private final RedditApi mApi;

    private RedditClient(RedditApi api) {
        mApi = api;
    }

    public static RedditClient create(Context context) {
        OkHttpClient httpClient = new OkHttpClient.Builder()
                .connectTimeout(5, TimeUnit.SECONDS)
                .readTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(10, TimeUnit.SECONDS)
                .addInterceptor(new NetworkAvailabilityInterceptor(context))
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(GSON))
                .client(httpClient)
                .build();

        return new RedditClient(retrofit.create(RedditApi.class));
    }

    public RedditApi getApi() {
        return mApi;
    }
}
