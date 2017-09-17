package com.robotnec.reddit.core.service;

import com.robotnec.reddit.core.mvp.model.TopFeedListing;
import com.robotnec.reddit.core.mvp.model.Result;
import com.robotnec.reddit.core.web.pagination.Page;
import com.robotnec.reddit.core.web.pagination.Pageable;

import io.reactivex.Observable;

public interface ListingService {
    Observable<Result<Page<TopFeedListing>>> getTopFeedListing(Pageable pageable);
}
