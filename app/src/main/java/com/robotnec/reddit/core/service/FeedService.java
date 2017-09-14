package com.robotnec.reddit.core.service;

import com.robotnec.reddit.core.mvp.model.TopFeed;
import com.robotnec.reddit.core.support.Result;

import io.reactivex.Observable;

public interface FeedService {
    Observable<Result<TopFeed>> getFeed();
}
