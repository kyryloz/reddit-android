package com.robotnec.reddit.core.mvp.model;

import android.os.Parcel;

import com.robotnec.reddit.core.web.dto.FeedItemDto;

import java.util.List;

public class TopFeedListing implements Listing<FeedItemDto> {

    private final List<FeedItemDto> feedItems;
    private final String after;

    public TopFeedListing(List<FeedItemDto> feedItems, String after) {
        this.feedItems = feedItems;
        this.after = after;
    }

    @Override
    public List<FeedItemDto> getItems() {
        return feedItems;
    }

    @Override
    public String getAfter() {
        return after;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(this.feedItems);
        dest.writeString(this.after);
    }

    private TopFeedListing(Parcel in) {
        this.feedItems = in.createTypedArrayList(FeedItemDto.CREATOR);
        this.after = in.readString();
    }

    public static final Creator<TopFeedListing> CREATOR = new Creator<TopFeedListing>() {
        @Override
        public TopFeedListing createFromParcel(Parcel source) {
            return new TopFeedListing(source);
        }

        @Override
        public TopFeedListing[] newArray(int size) {
            return new TopFeedListing[size];
        }
    };
}
