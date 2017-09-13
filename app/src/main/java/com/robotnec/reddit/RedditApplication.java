package com.robotnec.reddit;

import android.app.Application;

import com.robotnec.reddit.core.di.ApplicationComponent;
import com.robotnec.reddit.core.di.ApplicationGraph;

public class RedditApplication extends Application {
    private ApplicationComponent applicationComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        applicationComponent = new ApplicationGraph(this).buildApplicationComponent();
    }

    public ApplicationComponent getApplicationComponent() {
        return applicationComponent;
    }
}
