package com.robotnec.reddit.core.web.pagination;

import android.os.Parcel;

import com.annimon.stream.Objects;
import com.robotnec.reddit.core.mvp.model.Listing;

public class PageImpl<T extends Listing> implements Page<T> {

    private final T listing;
    private final Pageable pageable;
    private final int total;

    public PageImpl(T listing, Pageable pageable, int total) {
        this.listing = Objects.requireNonNull(listing);
        this.pageable = Objects.requireNonNull(pageable);
        this.total = total;
    }

    public T getListing() {
        return listing;
    }

    @Override
    public boolean hasNext() {
        return (pageable.getPageNumber() + 1) * pageable.getPageSize() < total;
    }

    @Override
    public Pageable nextPageable() {
        return pageable.next(listing.getAfter());
    }

    @Override
    public boolean isFirst() {
        return pageable.getPageNumber() == 0;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(this.listing, flags);
        dest.writeParcelable(this.pageable, flags);
        dest.writeInt(this.total);
    }

    private PageImpl(Parcel in) {
        this.listing = in.readParcelable(getClass().getClassLoader());
        this.pageable = in.readParcelable(Pageable.class.getClassLoader());
        this.total = in.readInt();
    }

    public static final Creator<PageImpl> CREATOR = new Creator<PageImpl>() {
        @Override
        public PageImpl createFromParcel(Parcel source) {
            return new PageImpl(source);
        }

        @Override
        public PageImpl[] newArray(int size) {
            return new PageImpl[size];
        }
    };
}