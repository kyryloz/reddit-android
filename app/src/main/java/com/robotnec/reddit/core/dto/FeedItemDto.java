package com.robotnec.reddit.core.dto;

import com.google.gson.annotations.SerializedName;

public class FeedItemDto {

    @SerializedName("title")
    private String title;

    @SerializedName("id")
    private String id;

    public String getTitle() {
        return title;
    }
}
