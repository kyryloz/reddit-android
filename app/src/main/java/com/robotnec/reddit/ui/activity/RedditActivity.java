package com.robotnec.reddit.ui.activity;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.annimon.stream.Optional;
import com.robotnec.reddit.R;
import com.robotnec.reddit.core.domain.TopFeed;
import com.robotnec.reddit.core.mvp.presenter.FeedPresenter;
import com.robotnec.reddit.core.mvp.view.FeedView;
import com.robotnec.reddit.ui.adapter.FeedAdapter;

import butterknife.BindView;

public class RedditActivity extends BasePresenterActivity<FeedPresenter, FeedView> implements FeedView {

    private static final String KEY_TOP_FEED = "key_top_feed";

    @BindView(R.id.swipeRefresh)
    SwipeRefreshLayout swipeRefresh;

    @BindView(R.id.feedRecycler)
    RecyclerView feedRecycler;

    private FeedAdapter feedAdapter;
    private Optional<TopFeed> topFeed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        feedAdapter = new FeedAdapter(this);
        feedRecycler.setAdapter(feedAdapter);
        feedRecycler.setLayoutManager(new LinearLayoutManager(this));

        swipeRefresh.setOnRefreshListener(() -> presenter.requestFeed());

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
                .executeIfAbsent(() -> presenter.requestFeed());
    }

    @Override
    protected FeedPresenter createPresenter() {
        return new FeedPresenter(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_reddit;
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

    }
}
