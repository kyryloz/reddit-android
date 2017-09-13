package com.robotnec.reddit.ui.activity;

import android.os.Bundle;
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
    RecyclerView feed;

    private FeedAdapter feedAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        feedAdapter = new FeedAdapter(this);
        feed.setLayoutManager(new LinearLayoutManager(this));
        feed.setAdapter(feedAdapter);
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
