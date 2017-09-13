package com.robotnec.reddit.core.dto;

import com.google.gson.annotations.SerializedName;

public class FeedItemDto {

    @SerializedName("title")
    private String title;

    @SerializedName("id")
    private String id;

    @SerializedName("thumbnail")
    private String thumbnail;

    public String getTitle() {
        return title;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public String getId() {
        return id;
    }
}
