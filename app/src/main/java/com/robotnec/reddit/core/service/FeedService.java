package com.robotnec.reddit.core.service;

import com.robotnec.reddit.core.mvp.model.TopFeedListing;
import com.robotnec.reddit.core.support.Result;
import com.robotnec.reddit.core.web.pagination.Page;
import com.robotnec.reddit.core.web.pagination.Pageable;

import io.reactivex.Observable;

public interface FeedService {
    Observable<Result<Page<TopFeedListing>>> getFeed(Pageable pageable);
}
