package com.robotnec.reddit.ui.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.annimon.stream.Optional;
import com.annimon.stream.Stream;
import com.robotnec.reddit.R;
import com.robotnec.reddit.core.mvp.model.TopFeedListing;
import com.robotnec.reddit.core.mvp.presenter.TopFeedPresenter;
import com.robotnec.reddit.core.mvp.view.TopFeedView;
import com.robotnec.reddit.core.web.dto.FeedItemDto;
import com.robotnec.reddit.core.web.pagination.Page;
import com.robotnec.reddit.core.web.pagination.PageRequest;
import com.robotnec.reddit.ui.adapter.TopFeedAdapter;
import com.robotnec.reddit.ui.support.LazyLoadingListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class TopFeedActivity extends BasePresenterActivity<TopFeedPresenter, TopFeedView> implements TopFeedView {

    private static final String KEY_TOP_FEED = "key_top_feed";

    @BindView(R.id.feedRecycler)
    RecyclerView feedRecycler;

    private TopFeedAdapter feedAdapter;
    private List<Page<TopFeedListing>> feedPages;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        feedAdapter = new TopFeedAdapter(this, this::displayFullSizeImage);
        feedRecycler.setAdapter(feedAdapter);
        feedRecycler.setLayoutManager(new LinearLayoutManager(this));
        feedRecycler.addOnScrollListener(new LazyLoadingListener() {
            @Override
            public void onLoadMore() {
                getLastLoadedPage()
                        .filter(Page::hasNext)
                        .map(Page::nextPageable)
                        .ifPresent(presenter::requestTopFeed);
            }
        });

        feedPages = Optional.ofNullable(savedInstanceState)
                .map(bundle -> bundle.<Page<TopFeedListing>>getParcelableArrayList(KEY_TOP_FEED))
                .orElse(new ArrayList<>());
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList(KEY_TOP_FEED, new ArrayList<>(feedPages));
    }

    @Override
    protected void onStart() {
        super.onStart();
        feedAdapter.addItems(Stream.of(feedPages)
                .map(Page::getListing)
                .flatMap(listing -> Stream.of(listing.getItems()))
                .toList());

        if (feedPages.isEmpty()) {
            presenter.requestTopFeed(PageRequest.first());
        }
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
    public void displayFeedPage(Page<TopFeedListing> feedPage) {
        List<FeedItemDto> items = feedPage.getListing().getItems();
        feedAdapter.addItems(items);
        feedPages.add(feedPage);
    }

    @Override
    public void showProgress(boolean inProgress) {
        feedAdapter.setLoading(inProgress);
    }

    @Override
    public void showError(String errorMessage) {
        Snackbar.make(feedRecycler, errorMessage, Snackbar.LENGTH_LONG).show();
    }

    private void displayFullSizeImage(String fullImageUrl) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(fullImageUrl));
        startActivity(intent);
    }

    private Optional<Page<TopFeedListing>> getLastLoadedPage() {
        return Optional.ofNullable(feedPages.isEmpty() ? null : feedPages.get(feedPages.size() - 1));
    }
}
