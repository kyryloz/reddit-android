package com.robotnec.reddit.core.mvp.presenter;

import com.robotnec.reddit.core.di.ApplicationComponent;
import com.robotnec.reddit.core.mvp.model.TopFeed;
import com.robotnec.reddit.core.mvp.view.TopFeedView;
import com.robotnec.reddit.core.service.FeedService;
import com.robotnec.reddit.core.support.Result;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class TopFeedPresenter extends Presenter<TopFeedView> {

    @Inject
    FeedService feedService;

    private final CompositeDisposable compositeDisposable;

    public TopFeedPresenter(TopFeedView view) {
        super(view);
        compositeDisposable = new CompositeDisposable();
    }

    @Override
    public void injectComponent(ApplicationComponent applicationComponent) {
        applicationComponent.inject(this);
    }

    @Override
    public void dispose() {
        compositeDisposable.dispose();
    }

    public void requestTopFeed() {
        compositeDisposable.add(feedService.getFeed()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::processTopFeedResult));
    }

    private void processTopFeedResult(Result<TopFeed> result) {
        view.showProgress(result.isInProgress());
        if (!result.isInProgress()) {
            if (result.isSuccess()) {
                view.displayFeed(result.getResult());
            } else {
                view.showError(result.getErrorMessage());
            }
        }
    }
}
