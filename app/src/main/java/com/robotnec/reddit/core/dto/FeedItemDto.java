package com.robotnec.reddit.core.dto;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class FeedItemDto implements Parcelable {

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.title);
        dest.writeString(this.id);
        dest.writeString(this.thumbnail);
    }

    private FeedItemDto(Parcel in) {
        this.title = in.readString();
        this.id = in.readString();
        this.thumbnail = in.readString();
    }

    public static final Parcelable.Creator<FeedItemDto> CREATOR = new Parcelable.Creator<FeedItemDto>() {
        @Override
        public FeedItemDto createFromParcel(Parcel source) {
            return new FeedItemDto(source);
        }

        @Override
        public FeedItemDto[] newArray(int size) {
            return new FeedItemDto[size];
        }
    };
}
