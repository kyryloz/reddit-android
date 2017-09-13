package com.robotnec.reddit.core.domain;

import com.robotnec.reddit.core.dto.FeedItemDto;

import java.util.List;

public class TopFeed {

    private final List<FeedItemDto> feedItems;

    public TopFeed(List<FeedItemDto> feedItems) {
        this.feedItems = feedItems;
    }

    public List<FeedItemDto> getFeedItems() {
        return feedItems;
    }
}
