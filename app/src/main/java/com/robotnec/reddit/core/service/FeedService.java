package com.robotnec.reddit.core.service;

import com.robotnec.reddit.core.model.FeedItem;

import java.util.List;

import io.reactivex.Observable;

public interface FeedService {
    Observable<List<FeedItem>> getFeed();
}
