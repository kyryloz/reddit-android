package com.robotnec.reddit.ui.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.robotnec.reddit.R;
import com.robotnec.reddit.core.model.FeedItem;
import com.robotnec.reddit.core.mvp.presenter.FeedPresenter;
import com.robotnec.reddit.core.mvp.view.FeedView;
import com.robotnec.reddit.ui.adapter.FeedAdapter;

import java.util.List;

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
        presenter.subscribeFeed();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.unsubscribeFeed();
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
    public void displayFeed(List<FeedItem> feed) {
        feedAdapter.setItems(feed);
    }
}
