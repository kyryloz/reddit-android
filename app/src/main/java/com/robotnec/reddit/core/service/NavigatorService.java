package com.robotnec.reddit.core.service;

import android.support.v7.app.AppCompatActivity;

import com.robotnec.reddit.core.exception.FeedItemTypeNotSupportedException;
import com.robotnec.reddit.core.web.dto.FeedItemDto;

public interface NavigatorService {
    void openFeedItem(AppCompatActivity parent, FeedItemDto feedItem) throws FeedItemTypeNotSupportedException;
}
