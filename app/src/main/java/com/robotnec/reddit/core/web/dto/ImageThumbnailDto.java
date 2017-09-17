package com.robotnec.reddit.core.web.dto;

import android.os.Parcel;
import android.os.Parcelable;

public class ImageThumbnailDto implements Parcelable {

    private final String url;

    public ImageThumbnailDto(String url) {
        this.url = url;
    }

    String getUrl() {
        return url;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.url);
    }

    private ImageThumbnailDto(Parcel in) {
        this.url = in.readString();
    }

    public static final Creator<ImageThumbnailDto> CREATOR = new Creator<ImageThumbnailDto>() {
        @Override
        public ImageThumbnailDto createFromParcel(Parcel source) {
            return new ImageThumbnailDto(source);
        }

        @Override
        public ImageThumbnailDto[] newArray(int size) {
            return new ImageThumbnailDto[size];
        }
    };
}
