package com.robotnec.reddit.ui.support;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

public abstract class LazyLoadingListener extends RecyclerView.OnScrollListener {

    private boolean loading;

    protected LazyLoadingListener() {
        this.loading = false;
    }

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
            if (!loading && linearLayoutManager.findLastCompletelyVisibleItemPosition() == recyclerAdapter.getItemCount() - 1) {
                loading = true;
                onLoadMore();
            }
        }
    }

    public abstract void onLoadMore();

    public void setLoading(boolean loading) {
        this.loading = loading;
    }
}
