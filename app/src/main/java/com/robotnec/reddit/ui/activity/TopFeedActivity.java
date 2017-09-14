package com.robotnec.reddit.ui.activity;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.annimon.stream.Optional;
import com.robotnec.reddit.R;
import com.robotnec.reddit.core.mvp.model.TopFeed;
import com.robotnec.reddit.core.mvp.presenter.TopFeedPresenter;
import com.robotnec.reddit.core.mvp.view.TopFeedView;
import com.robotnec.reddit.ui.adapter.TopFeedAdapter;

import butterknife.BindView;

public class TopFeedActivity extends BasePresenterActivity<TopFeedPresenter, TopFeedView> implements TopFeedView {

    private static final String KEY_TOP_FEED = "key_top_feed";

    @BindView(R.id.swipeRefresh)
    SwipeRefreshLayout swipeRefresh;

    @BindView(R.id.feedRecycler)
    RecyclerView feedRecycler;

    private TopFeedAdapter feedAdapter;
    private Optional<TopFeed> topFeed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        feedAdapter = new TopFeedAdapter(this);
        feedRecycler.setAdapter(feedAdapter);
        feedRecycler.setLayoutManager(new LinearLayoutManager(this));

        swipeRefresh.setOnRefreshListener(() -> presenter.requestTopFeed());

        topFeed = Optional.ofNullable(savedInstanceState)
                .map(bundle -> savedInstanceState.getParcelable(KEY_TOP_FEED));
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        topFeed.executeIfPresent(feed -> outState.putParcelable(KEY_TOP_FEED, feed));
    }

    @Override
    protected void onStart() {
        super.onStart();
        topFeed.map(TopFeed::getFeedItems)
                .executeIfPresent(feedItems -> feedAdapter.setItems(feedItems))
                .executeIfAbsent(() -> presenter.requestTopFeed());
    }

    @Override
    protected TopFeedPresenter createPresenter() {
        return new TopFeedPresenter(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_top_feed;
    }

    @Override
    public void displayFeed(TopFeed feed) {
        topFeed = Optional.of(feed);
        feedAdapter.setItems(feed.getFeedItems());
    }

    @Override
    public void displayProgress(boolean inProgress) {
        swipeRefresh.setRefreshing(inProgress);
    }

    @Override
    public void showError(String errorMessage) {
        Snackbar.make(feedRecycler, errorMessage, Snackbar.LENGTH_LONG).show();
    }
}
