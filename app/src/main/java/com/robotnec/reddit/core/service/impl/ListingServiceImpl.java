package com.robotnec.reddit.core.service.impl;

import android.support.annotation.NonNull;

import com.annimon.stream.Objects;
import com.robotnec.reddit.core.mvp.model.TopFeedListing;
import com.robotnec.reddit.core.service.ListingService;
import com.robotnec.reddit.core.mvp.model.Result;
import com.robotnec.reddit.core.web.RedditApi;
import com.robotnec.reddit.core.web.pagination.Page;
import com.robotnec.reddit.core.web.pagination.PageImpl;
import com.robotnec.reddit.core.web.pagination.Pageable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.reactivex.Observable;

public class ListingServiceImpl implements ListingService {

    private final Logger logger = LoggerFactory.getLogger(ListingServiceImpl.class);

    private final RedditApi api;

    public ListingServiceImpl(RedditApi api) {
        this.api = api;
    }

    @Override
    public Observable<Result<Page<TopFeedListing>>> getTopFeedListing(Pageable pageable) {
        Objects.requireNonNull(pageable);
        logger.debug("Request feed: {}", pageable);

        return api.getTopFeed(pageable.getPageSize(), pageable.getAfter())
                .map(feed -> createPage(feed, pageable))
                .map(Result::success)
                .startWith(Result.inProgress())
                .onErrorReturn(Result::failed);
    }

    @NonNull
    private Page<TopFeedListing> createPage(TopFeedListing feed, Pageable pageable) {
        return new PageImpl<>(feed, pageable, RedditApi.TOTAL_LISTING_SIZE);
    }
}
