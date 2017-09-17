package com.robotnec.reddit.core.mvp.presenter;

import com.robotnec.reddit.core.di.ApplicationComponent;
import com.robotnec.reddit.core.mvp.model.Result;
import com.robotnec.reddit.core.mvp.model.TopFeedListing;
import com.robotnec.reddit.core.mvp.view.TopFeedView;
import com.robotnec.reddit.core.service.ListingService;
import com.robotnec.reddit.core.web.pagination.Page;
import com.robotnec.reddit.core.web.pagination.Pageable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class TopFeedPresenter extends Presenter<TopFeedView> {

    private final Logger logger = LoggerFactory.getLogger(TopFeedPresenter.class);

    @Inject
    ListingService listingService;

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
        logger.debug("Request feed: {}", pageable);
        compositeDisposable.add(listingService.getTopFeedListing(pageable)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::processTopFeedResult));
    }

    private void processTopFeedResult(Result<Page<TopFeedListing>> result) {
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
