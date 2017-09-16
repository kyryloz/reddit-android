package com.robotnec.reddit.core.web.pagination;

import android.os.Parcelable;

public interface Pageable extends Parcelable {
    String getAfter();

    int getPageSize();

    int getPageNumber();

    Pageable next(String after);
}