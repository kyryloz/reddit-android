package com.robotnec.reddit.core.di.module;

import android.content.Context;

import com.robotnec.reddit.core.service.FeedService;
import com.robotnec.reddit.core.service.FeedServiceImpl;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class ServiceModule {

    @Singleton
    @Provides
    FeedService provideFeedService(Context context) {
        return new FeedServiceImpl(context);
    }
}
