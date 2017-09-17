package com.robotnec.reddit.core.di.module;

import android.content.Context;

import com.robotnec.reddit.core.service.ImageService;
import com.robotnec.reddit.core.service.ListingService;
import com.robotnec.reddit.core.service.NavigatorService;
import com.robotnec.reddit.core.service.impl.ImageServiceImpl;
import com.robotnec.reddit.core.service.impl.ListingServiceImpl;
import com.robotnec.reddit.core.service.impl.NavigatorServiceImpl;
import com.robotnec.reddit.core.web.RedditApi;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class ServiceModule {

    @Singleton
    @Provides
    ListingService provideListingService(RedditApi api) {
        return new ListingServiceImpl(api);
    }

    @Singleton
    @Provides
    ImageService provideImageService(Context context) {
        return new ImageServiceImpl(context);
    }

    @Singleton
    @Provides
    NavigatorService provideNavigatorService() {
        return new NavigatorServiceImpl();
    }
}
