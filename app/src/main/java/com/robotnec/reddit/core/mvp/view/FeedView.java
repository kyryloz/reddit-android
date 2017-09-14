package com.robotnec.reddit.core.mvp.view;

import com.robotnec.reddit.core.domain.TopFeed;

public interface FeedView extends View {
    void displayFeed(TopFeed feed);

    void displayProgress(boolean inProgress);

    void showError(String errorMessage);
}
