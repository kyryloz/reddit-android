package com.robotnec.reddit.core.mvp.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.robotnec.reddit.core.web.dto.FeedItemDto;

import java.util.ArrayList;
import java.util.List;

public class TopFeed implements Parcelable {

    private final List<FeedItemDto> feedItems;

    public TopFeed(List<FeedItemDto> feedItems) {
        this.feedItems = feedItems;
    }

    public List<FeedItemDto> getFeedItems() {
        return feedItems;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeList(this.feedItems);
    }

    private TopFeed(Parcel in) {
        feedItems = new ArrayList<>();
        in.readList(feedItems, FeedItemDto.class.getClassLoader());
    }

    public static final Parcelable.Creator<TopFeed> CREATOR = new Parcelable.Creator<TopFeed>() {
        @Override
        public TopFeed createFromParcel(Parcel source) {
            return new TopFeed(source);
        }

        @Override
        public TopFeed[] newArray(int size) {
            return new TopFeed[size];
        }
    };
}
