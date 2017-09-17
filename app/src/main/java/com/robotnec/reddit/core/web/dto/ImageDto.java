package com.robotnec.reddit.core.web.dto;

import android.os.Parcel;
import android.os.Parcelable;

public class ImageDto implements Parcelable {

    private final String url;
    private final boolean gif;

    public ImageDto(String url, boolean gif) {
        this.url = url;
        this.gif = gif;
    }

    public String getUrl() {
        return url;
    }

    public boolean isGif() {
        return gif;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.url);
        dest.writeByte(this.gif ? (byte) 1 : (byte) 0);
    }

    private ImageDto(Parcel in) {
        this.url = in.readString();
        this.gif = in.readByte() != 0;
    }

    public static final Creator<ImageDto> CREATOR = new Creator<ImageDto>() {
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
