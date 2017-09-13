package com.robotnec.reddit.core.service;

import com.robotnec.reddit.core.domain.TopFeed;

import io.reactivex.Observable;

public interface FeedService {
    Observable<TopFeed> getFeed();
}
