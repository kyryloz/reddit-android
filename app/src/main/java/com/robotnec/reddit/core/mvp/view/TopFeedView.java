package com.robotnec.reddit.core.mvp.view;

import com.robotnec.reddit.core.mvp.model.TopFeedListing;
import com.robotnec.reddit.core.web.pagination.Page;

public interface TopFeedView extends View {
    void displayFeedPage(Page<TopFeedListing> feedPage);

    void showProgress(boolean inProgress);
}
