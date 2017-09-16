package com.robotnec.reddit.core.mvp.model;

import android.os.Parcelable;

import java.util.List;

public interface Listing<T> extends Parcelable {
    List<T> getItems();

    String getAfter();
}
