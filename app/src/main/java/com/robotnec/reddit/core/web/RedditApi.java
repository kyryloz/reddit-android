package com.robotnec.reddit.core.web;

import com.robotnec.reddit.core.mvp.model.TopFeed;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface RedditApi {

    @GET("top.json")
    Observable<TopFeed> getTopFeed(@Query("limit") int limit);
}
