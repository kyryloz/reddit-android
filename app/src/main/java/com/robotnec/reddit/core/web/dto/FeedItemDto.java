package com.robotnec.reddit.core.web.dto;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.Nullable;

import com.google.gson.annotations.SerializedName;

public class FeedItemDto implements Parcelable {

    @SerializedName("title")
    private String title;

    @SerializedName("id")
    private String id;

    @SerializedName("thumbnail")
    @Nullable
    private ImageThumbnailDto imageThumbnail;

    @SerializedName("preview")
    @Nullable
    private ImageDto imageFull;

    @SerializedName("author")
    private String author;

    @SerializedName("num_comments")
    private int numberOfComments;

    @SerializedName("created_utc")
    private long createdTimestamp;

    public String getTitle() {
        return title;
    }

    public String getImageThumbnail() {
        return imageThumbnail != null ? imageThumbnail.getUrl() : null;
    }

    public String getImageFull() {
        return imageFull != null ? imageFull.getUrl() : null;
    }

    public String getId() {
        return id;
    }

    public String getAuthor() {
        return author;
    }

    public int getNumberOfComments() {
        return numberOfComments;
    }

    public long getCreatedTimestamp() {
        return createdTimestamp;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.title);
        dest.writeString(this.id);
        dest.writeParcelable(this.imageThumbnail, flags);
        dest.writeParcelable(this.imageFull, flags);
        dest.writeString(this.author);
        dest.writeInt(this.numberOfComments);
        dest.writeLong(this.createdTimestamp);
    }

    private FeedItemDto(Parcel in) {
        this.title = in.readString();
        this.id = in.readString();
        this.imageThumbnail = in.readParcelable(ImageDto.class.getClassLoader());
        this.imageFull = in.readParcelable(ImageDto.class.getClassLoader());
        this.author = in.readString();
        this.numberOfComments = in.readInt();
        this.createdTimestamp = in.readLong();
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
