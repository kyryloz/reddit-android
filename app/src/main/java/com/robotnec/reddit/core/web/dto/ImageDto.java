package com.robotnec.reddit.core.web.dto;

import android.os.Parcel;
import android.os.Parcelable;

public class ImageDto implements Parcelable {

    private final String url;

    public ImageDto(String url) {
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

    private ImageDto(Parcel in) {
        this.url = in.readString();
    }

    public static final Parcelable.Creator<ImageDto> CREATOR = new Parcelable.Creator<ImageDto>() {
        @Override
        public ImageDto createFromParcel(Parcel source) {
            return new ImageDto(source);
        }

        @Override
        public ImageDto[] newArray(int size) {
            return new ImageDto[size];
        }
    };
}
