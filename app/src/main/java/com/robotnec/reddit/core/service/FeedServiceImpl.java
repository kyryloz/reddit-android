package com.robotnec.reddit.core.service;

import android.content.Context;

import com.robotnec.reddit.core.mvp.model.TopFeed;
import com.robotnec.reddit.core.support.Result;
import com.robotnec.reddit.core.web.RedditApi;
import com.robotnec.reddit.core.web.RedditClient;

import io.reactivex.Observable;

public class FeedServiceImpl implements FeedService {

    private final RedditApi api;

    public FeedServiceImpl(Context context) {
        api = RedditClient.create(context).getApi();
    }

    @Override
    public Observable<Result<TopFeed>> getFeed() {
        return api.getTopFeed(50)
                .map(Result::success)
                .startWith(Result.inProgress())
                .onErrorReturn(throwable -> Result.failed(throwable.getMessage()));
    }
}
