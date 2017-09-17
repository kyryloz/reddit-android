package com.robotnec.reddit.core.mvp.presenter;

import com.robotnec.reddit.core.di.ApplicationComponent;
import com.robotnec.reddit.core.mvp.view.ImageViewerView;
import com.robotnec.reddit.core.service.ImageService;

import javax.inject.Inject;

public class ImageViewerPresenter extends Presenter<ImageViewerView> {

    @Inject
    ImageService imageService;

    public ImageViewerPresenter(ImageViewerView view) {
        super(view);
    }

    @Override
    public void injectComponent(ApplicationComponent applicationComponent) {
        applicationComponent.inject(this);
    }

    @Override
    public void dispose() {
    }

    public void saveImageToExternalStorage(String imageUrl) {
        imageService.saveImageToExternalStorage(imageUrl);
    }
}
