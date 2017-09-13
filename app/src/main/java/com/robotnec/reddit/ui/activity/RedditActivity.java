package com.robotnec.reddit.ui.activity;

import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.robotnec.reddit.R;
import com.robotnec.reddit.core.domain.TopFeed;
import com.robotnec.reddit.core.mvp.presenter.FeedPresenter;
import com.robotnec.reddit.core.mvp.view.FeedView;
import com.robotnec.reddit.ui.adapter.FeedAdapter;

import butterknife.BindView;

public class RedditActivity extends BasePresenterActivity<FeedPresenter, FeedView> implements FeedView {

    @BindView(R.id.feed)
    RecyclerView feedRecycler;

    private FeedAdapter feedAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        feedAdapter = new FeedAdapter(this);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        feedRecycler.setLayoutManager(layoutManager);
        feedRecycler.setAdapter(feedAdapter);
        DividerItemDecoration dividerDecoration =
                new DividerItemDecoration(feedRecycler.getContext(), layoutManager.getOrientation());
        feedRecycler.addItemDecoration(dividerDecoration);
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
        feedAdapter.setItems(feed.getFeedItems());
    }
}
