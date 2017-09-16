package com.robotnec.reddit.ui.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import com.annimon.stream.Optional;
import com.robotnec.reddit.R;
import com.robotnec.reddit.core.mvp.model.TopFeedListing;
import com.robotnec.reddit.core.mvp.presenter.TopFeedPresenter;
import com.robotnec.reddit.core.mvp.view.TopFeedView;
import com.robotnec.reddit.core.web.pagination.Page;
import com.robotnec.reddit.core.web.pagination.PageRequest;
import com.robotnec.reddit.ui.adapter.TopFeedAdapter;

import butterknife.BindView;
import butterknife.OnClick;

public class TopFeedActivity extends BasePresenterActivity<TopFeedPresenter, TopFeedView> implements TopFeedView {

    private static final String KEY_TOP_FEED = "key_top_feed";

    @BindView(R.id.swipeRefresh)
    SwipeRefreshLayout swipeRefresh;

    @BindView(R.id.feedRecycler)
    RecyclerView feedRecycler;

    @BindView(R.id.nextButton)
    Button nextButton;

    private TopFeedAdapter feedAdapter;
    private Optional<Page<TopFeedListing>> topFeed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        feedAdapter = new TopFeedAdapter(this, this::displayFullSizeImage);
        feedRecycler.setAdapter(feedAdapter);
        feedRecycler.setLayoutManager(new LinearLayoutManager(this));

        swipeRefresh.setOnRefreshListener(() -> topFeed
                .ifPresent(feed -> presenter.requestTopFeed(PageRequest.first())));

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
        topFeed.map(Page::getListing)
                .map(TopFeedListing::getItems)
                .executeIfPresent(feedItems -> feedAdapter.setItems(feedItems))
                .executeIfAbsent(() -> presenter.requestTopFeed(PageRequest.first()));
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
    public void displayFeed(Page<TopFeedListing> feedPage) {
        topFeed = Optional.of(feedPage);
        feedAdapter.setItems(feedPage.getListing().getItems());

        nextButton.setEnabled(feedPage.hasNext());
    }

    @Override
    public void showProgress(boolean inProgress) {
        swipeRefresh.setRefreshing(inProgress);
    }

    @Override
    public void showError(String errorMessage) {
        Snackbar.make(feedRecycler, errorMessage, Snackbar.LENGTH_LONG).show();
    }

    @OnClick(R.id.nextButton)
    void onNavigationButtonClick(View view) {
        topFeed.filter(Page::hasNext)
                .map(Page::nextPageable)
                .ifPresent(presenter::requestTopFeed);
    }

    private void displayFullSizeImage(String fullImageUrl) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(fullImageUrl));
        startActivity(intent);
    }
}
