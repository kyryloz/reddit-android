package com.robotnec.reddit.core.di.module;

import android.content.Context;

import com.robotnec.reddit.core.web.RedditApi;
import com.robotnec.reddit.core.web.RedditClient;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class ApiModule {

    @Singleton
    @Provides
    RedditApi provideRedditApi(Context context) {
        return RedditClient.create(context).getApi();
    }
}
