package com.robotnec.reddit.core.mvp.view;

public interface ImageViewerView extends View {
    void showProgress(boolean inProgress);

    void showError(String errorMessage);

    void showSaveImageSuccess();
}
