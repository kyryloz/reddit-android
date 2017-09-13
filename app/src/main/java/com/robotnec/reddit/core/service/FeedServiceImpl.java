package com.robotnec.reddit.core.service;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.robotnec.reddit.core.deserializer.RedditTopFeedDeserializer;
import com.robotnec.reddit.core.domain.TopFeed;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

import io.reactivex.Observable;

public class FeedServiceImpl implements FeedService {

    private final Context context;
    private final Gson gson;

    public FeedServiceImpl(Context context) {
        this.context = context;
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(TopFeed.class, new RedditTopFeedDeserializer());
        gson = gsonBuilder.create();
    }

    @Override
    public Observable<TopFeed> getFeed() {
        return Observable.create(emitter -> {
            try (InputStream in = context.getAssets().open("top.json")) {
                Reader reader = new InputStreamReader(in, "UTF-8");
                emitter.onNext(gson.fromJson(reader, TopFeed.class));
                emitter.onComplete();
            }
        });
    }
}
