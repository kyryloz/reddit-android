package com.robotnec.reddit.ui.support;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

public abstract class LazyLoadingListener extends RecyclerView.OnScrollListener {

    @Override
    public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
        super.onScrollStateChanged(recyclerView, newState);

        if (newState == RecyclerView.SCROLL_STATE_IDLE) {

            LinearLayoutManager linearLayoutManager;

            RecyclerView.LayoutManager recyclerViewLayoutManager = recyclerView.getLayoutManager();

            if (recyclerViewLayoutManager instanceof LinearLayoutManager) {
                linearLayoutManager = (LinearLayoutManager) recyclerViewLayoutManager;
            } else {
                throw new RuntimeException("Only supports LinearLayoutManager");
            }

            RecyclerView.Adapter recyclerAdapter = recyclerView.getAdapter();
            if (linearLayoutManager.findLastCompletelyVisibleItemPosition() == recyclerAdapter.getItemCount() - 1) {
                onLoadMore();
            }
        }
    }

    public abstract void onLoadMore();
}
