package com.robotnec.reddit.core.service;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.robotnec.reddit.core.deserializer.TopFeedDeserializer;
import com.robotnec.reddit.core.domain.TopFeed;
import com.robotnec.reddit.core.support.Result;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

import io.reactivex.Observable;

public class FeedServiceImpl implements FeedService {

    private final Context context;
    private final Gson gson;

    public FeedServiceImpl(Context context) {
        this.context = context;
        gson = new GsonBuilder()
                .registerTypeAdapter(TopFeed.class, new TopFeedDeserializer())
                .create();
    }

    @Override
    public Observable<Result<TopFeed>> getFeed() {
        return Observable.create(emitter -> {
            emitter.onNext(Result.inProgress());

            Thread.sleep(3000);

            try (InputStream in = context.getAssets().open("top.json")) {
                Reader reader = new InputStreamReader(in, "UTF-8");
                TopFeed feed = gson.fromJson(reader, TopFeed.class);
                emitter.onNext(Result.success(feed));
                emitter.onComplete();
            }
        });
    }
}
