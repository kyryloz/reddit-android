package com.robotnec.reddit.core.service;

import com.robotnec.reddit.core.model.FeedItem;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;

public class FeedServiceImpl implements FeedService {

    private final List<FeedItem> feedItems;

    public FeedServiceImpl() {
        feedItems = new ArrayList<>();
        feedItems.add(new FeedItem(0, "Hello world"));
        feedItems.add(new FeedItem(1, "Hello world 2"));
    }

    @Override
    public Observable<List<FeedItem>> getFeed() {
        return Observable.just(feedItems);
    }
}
