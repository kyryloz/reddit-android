package com.robotnec.reddit.core.di;

import com.robotnec.reddit.core.di.module.AndroidModule;
import com.robotnec.reddit.core.di.module.ApiModule;
import com.robotnec.reddit.core.di.module.ServiceModule;
import com.robotnec.reddit.core.mvp.presenter.ImageViewerPresenter;
import com.robotnec.reddit.core.mvp.presenter.TopFeedPresenter;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {
        AndroidModule.class,
        ServiceModule.class,
        ApiModule.class
})
public interface ApplicationComponent {
    void inject(TopFeedPresenter presenter);

    void inject(ImageViewerPresenter presenter);
}
