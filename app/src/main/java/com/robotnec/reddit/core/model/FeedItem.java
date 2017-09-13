package com.robotnec.reddit.core.model;

public class FeedItem {
    private final long id;
    private final String text;

    public FeedItem(long id, String text) {
        this.text = text;
        this.id = id;
    }

    public long getId() {
        return id;
    }

    public String getText() {
        return text;
    }
}
