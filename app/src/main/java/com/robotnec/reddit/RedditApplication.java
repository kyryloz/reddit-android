package com.robotnec.reddit;

import android.app.Application;

import com.robotnec.reddit.core.di.ApplicationComponent;
import com.robotnec.reddit.core.di.DaggerApplicationComponent;
import com.robotnec.reddit.core.di.module.AndroidModule;
import com.robotnec.reddit.core.di.module.ApiModule;
import com.robotnec.reddit.core.di.module.ServiceModule;

public class RedditApplication extends Application {
    private ApplicationComponent applicationComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        applicationComponent = DaggerApplicationComponent
                .builder()
                .androidModule(new AndroidModule(this))
                .serviceModule(new ServiceModule())
                .apiModule(new ApiModule())
                .build();
    }

    public ApplicationComponent getApplicationComponent() {
        return applicationComponent;
    }
}
