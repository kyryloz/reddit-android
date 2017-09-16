package com.robotnec.reddit.core.web.pagination;


import android.os.Parcel;

import com.robotnec.reddit.core.web.RedditApi;

public class PageRequest implements Pageable {

    private final String after;
    private final int pageSize;
    private final int pageNumber;

    public static Pageable first() {
        return new PageRequest(null, RedditApi.PAGE_SIZE, 0);
    }

    private PageRequest(String after, int pageSize, int pageNumber) {
        this.after = after;
        this.pageSize = pageSize;
        this.pageNumber = pageNumber;
    }

    @Override
    public String getAfter() {
        return after;
    }

    @Override
    public int getPageSize() {
        return pageSize;
    }

    @Override
    public int getPageNumber() {
        return pageNumber;
    }

    @Override
    public Pageable next(String after) {
        return new PageRequest(after, pageSize, pageNumber + 1);
    }

    @Override
    public String toString() {
        return "PageRequest{" +
                "after='" + after + '\'' +
                ", pageSize=" + pageSize +
                ", pageNumber=" + pageNumber +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.after);
        dest.writeInt(this.pageSize);
        dest.writeInt(this.pageNumber);
    }

    private PageRequest(Parcel in) {
        this.after = in.readString();
        this.pageSize = in.readInt();
        this.pageNumber = in.readInt();
    }

    public static final Creator<PageRequest> CREATOR = new Creator<PageRequest>() {
        @Override
        public PageRequest createFromParcel(Parcel source) {
            return new PageRequest(source);
        }

        @Override
        public PageRequest[] newArray(int size) {
            return new PageRequest[size];
        }
    };
}