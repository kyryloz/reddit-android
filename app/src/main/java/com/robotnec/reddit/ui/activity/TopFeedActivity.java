package com.robotnec.reddit.ui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
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
import com.robotnec.reddit.ui.support.Dividers;
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
    private LazyLoadingListener lazyLoadingListener;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        feedAdapter = new TopFeedAdapter(this, this::processFeedItemClick);
        feedRecycler.setAdapter(feedAdapter);
        feedRecycler.setLayoutManager(new LinearLayoutManager(this));
        feedRecycler.addItemDecoration(Dividers.verticalLayoutDivider(this));
        lazyLoadingListener = new LazyLoadingListener() {
            @Override
            public void onLoadMore() {
                getLastLoadedPage()
                        .filter(Page::hasNext)
                        .map(Page::nextPageable)
                        .ifPresent(presenter::requestTopFeed);
            }
        };
        feedRecycler.addOnScrollListener(lazyLoadingListener);

        feedPages = Optional.ofNullable(savedInstanceState)
                .map(bundle -> bundle.<Page<TopFeedListing>>getParcelableArrayList(KEY_TOP_FEED))
                .orElse(new ArrayList<>());

        if (feedPages.isEmpty()) {
            presenter.requestTopFeed(PageRequest.first());
        } else {
            feedAdapter.addItems(Stream.of(feedPages)
                    .map(Page::getListing)
                    .flatMap(listing -> Stream.of(listing.getItems()))
                    .toList());
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList(KEY_TOP_FEED, new ArrayList<>(feedPages));
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
        lazyLoadingListener.setLoading(false);
        List<FeedItemDto> items = feedPage.getListing().getItems();
        feedAdapter.addItems(items);
        feedPages.add(feedPage);
    }

    @Override
    public void showProgress(boolean inProgress) {
        feedAdapter.setLoading(inProgress, feedRecycler);
    }

    @Override
    public void showError(String message) {
        Snackbar.make(feedRecycler, message, Snackbar.LENGTH_SHORT).show();
    }

    private void processFeedItemClick(FeedItemDto feedItem) {
        presenter.openFeedItem(this, feedItem);
    }

    private Optional<Page<TopFeedListing>> getLastLoadedPage() {
        return Optional.ofNullable(feedPages.isEmpty() ? null : feedPages.get(feedPages.size() - 1));
    }
}
