package com.robotnec.reddit.core.web.dto;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class FeedItemDto implements Parcelable {

    @SerializedName("title")
    private String title;

    @SerializedName("id")
    private String id;

    @SerializedName("thumbnail")
    private String imageThumbnail;

    @SerializedName("preview")
    private ImageDto imageFull;

    public String getTitle() {
        return title;
    }

    public String getImageThumbnail() {
        return imageThumbnail;
    }

    public String getImageFull() {
        return imageFull.getUrl();
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
        dest.writeString(this.imageThumbnail);
        dest.writeParcelable(this.imageFull, flags);
    }

    private FeedItemDto(Parcel in) {
        this.title = in.readString();
        this.id = in.readString();
        this.imageThumbnail = in.readString();
        this.imageFull = in.readParcelable(ImageDto.class.getClassLoader());
    }

    public static final Creator<FeedItemDto> CREATOR = new Creator<FeedItemDto>() {
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
