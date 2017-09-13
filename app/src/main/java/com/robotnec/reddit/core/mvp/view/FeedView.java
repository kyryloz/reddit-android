package com.robotnec.reddit.core.mvp.view;

import com.robotnec.reddit.core.model.FeedItem;

import java.util.List;

public interface FeedView extends View {
    void displayFeed(List<FeedItem> feed);
}
