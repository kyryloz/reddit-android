package com.robotnec.reddit.core.mvp.presenter;

import com.robotnec.reddit.core.di.ApplicationComponent;
import com.robotnec.reddit.core.mvp.model.Result;
import com.robotnec.reddit.core.mvp.view.ImageViewerView;
import com.robotnec.reddit.core.service.ImageService;

import java.io.File;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class ImageViewerPresenter extends Presenter<ImageViewerView> {

    @Inject
    ImageService imageService;

    private final CompositeDisposable compositeDisposable;

    public ImageViewerPresenter(ImageViewerView view) {
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

    public void saveImageToExternalStorage(String imageUrl) {
        compositeDisposable.add(imageService.saveImageToExternalStorage(imageUrl)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::processImageSaveResult));
    }

    private void processImageSaveResult(Result<File> result) {
        view.showProgress(result.isInProgress());
        if (!result.isInProgress()) {
            if (result.isSuccess()) {
                view.showSaveImageSuccess();
            } else {
                view.showError(result.getErrorMessage());
            }
        }
    }
}
