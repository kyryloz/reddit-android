package com.robotnec.reddit.core.mvp.view;

import com.robotnec.reddit.core.mvp.model.TopFeed;

public interface TopFeedView extends View {
    void displayFeed(TopFeed feed);

    void displayProgress(boolean inProgress);

    void showError(String errorMessage);
}