package com.robotnec.reddit.core.mvp.presenter;

import android.support.v7.app.AppCompatActivity;

import com.robotnec.reddit.core.di.ApplicationComponent;
import com.robotnec.reddit.core.exception.FeedItemTypeNotSupportedException;
import com.robotnec.reddit.core.mvp.model.Result;
import com.robotnec.reddit.core.mvp.model.TopFeedListing;
import com.robotnec.reddit.core.mvp.view.TopFeedView;
import com.robotnec.reddit.core.service.ListingService;
import com.robotnec.reddit.core.service.NavigatorService;
import com.robotnec.reddit.core.web.dto.FeedItemDto;
import com.robotnec.reddit.core.web.pagination.Page;
import com.robotnec.reddit.core.web.pagination.Pageable;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class TopFeedPresenter extends Presenter<TopFeedView> {

    @Inject
    ListingService listingService;

    @Inject
    NavigatorService navigatorService;

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

    public void requestTopFeed(Pageable pageable) {
        compositeDisposable.add(listingService.getTopFeedListing(pageable)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::handleTopFeedResult));
    }

    public void openFeedItem(AppCompatActivity parent, FeedItemDto feedItem) {
        try {
            navigatorService.openFeedItem(parent, feedItem);
        } catch (FeedItemTypeNotSupportedException e) {
            view.showError(e.getMessage());
        }
    }

    private void handleTopFeedResult(Result<Page<TopFeedListing>> result) {
        view.showProgress(result.isInProgress());
        if (!result.isInProgress()) {
            if (result.isSuccess()) {
                view.displayFeedPage(result.getResult());
            } else {
                view.showError(result.getErrorMessage());
            }
        }
    }
}
