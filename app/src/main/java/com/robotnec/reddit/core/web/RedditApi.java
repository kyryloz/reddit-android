package com.robotnec.reddit.core.web;

import com.robotnec.reddit.core.mvp.model.TopFeedListing;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface RedditApi {

    int TOTAL_LISTING_SIZE = 10;
    int PAGE_SIZE = 2;

    @GET("top.json")
    Observable<TopFeedListing> getTopFeed(@Query("limit") int limit,
                                          @Query("after") String after);
}
