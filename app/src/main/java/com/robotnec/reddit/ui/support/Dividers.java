package com.robotnec.reddit.ui.support;

import android.content.Context;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.RecyclerView;

public class Dividers {
    public static RecyclerView.ItemDecoration verticalLayoutDivider(Context context) {
        return new DividerItemDecoration(context, DividerItemDecoration.VERTICAL);
    }
}
