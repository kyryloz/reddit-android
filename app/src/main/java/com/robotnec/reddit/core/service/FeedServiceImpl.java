package com.robotnec.reddit.core.service;

import android.content.Context;
import android.support.annotation.NonNull;

import com.annimon.stream.Objects;
import com.robotnec.reddit.core.mvp.model.TopFeedListing;
import com.robotnec.reddit.core.support.Result;
import com.robotnec.reddit.core.web.RedditApi;
import com.robotnec.reddit.core.web.RedditClient;
import com.robotnec.reddit.core.web.pagination.Page;
import com.robotnec.reddit.core.web.pagination.PageImpl;
import com.robotnec.reddit.core.web.pagination.Pageable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.reactivex.Observable;

public class FeedServiceImpl implements FeedService {

    private final Logger logger = LoggerFactory.getLogger(FeedServiceImpl.class);

    private final RedditApi api;

    public FeedServiceImpl(Context context) {
        api = RedditClient.create(context).getApi();
    }

    @Override
    public Observable<Result<Page<TopFeedListing>>> getFeed(Pageable pageable) {
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
