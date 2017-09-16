package com.robotnec.reddit.core.web.pagination;

import android.os.Parcelable;

import com.robotnec.reddit.core.mvp.model.Listing;

public interface Page<T extends Listing> extends Parcelable {
    T getListing();

    boolean hasNext();

    Pageable nextPageable();

    boolean isFirst();
}