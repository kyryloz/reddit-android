package com.robotnec.reddit.core.mvp.presenter;

import com.robotnec.reddit.core.di.ApplicationComponent;
import com.robotnec.reddit.core.mvp.view.FeedView;
import com.robotnec.reddit.core.service.FeedService;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class FeedPresenter extends Presenter<FeedView> {

    @Inject
    FeedService feedService;

    private final CompositeDisposable compositeDisposable;

    public FeedPresenter(FeedView view) {
        super(view);
        compositeDisposable = new CompositeDisposable();
    }

    @Override
    public void injectComponent(ApplicationComponent applicationComponent) {
        applicationComponent.inject(this);
    }

    public void subscribeFeed() {
        compositeDisposable.add(feedService.getFeed()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(view::displayFeed));
    }

    public void unsubscribeFeed() {
        compositeDisposable.dispose();
    }
}
