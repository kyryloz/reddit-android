package com.robotnec.reddit.core.service.impl;

import android.support.v7.app.AppCompatActivity;

import com.robotnec.reddit.R;
import com.robotnec.reddit.core.exception.FeedItemTypeNotSupportedException;
import com.robotnec.reddit.core.service.NavigatorService;
import com.robotnec.reddit.core.web.dto.FeedItemDto;
import com.robotnec.reddit.core.web.dto.ImageDto;
import com.robotnec.reddit.ui.activity.ImageViewerActivity;

public class NavigatorServiceImpl implements NavigatorService {

    @Override
    public void openFeedItem(AppCompatActivity parent, FeedItemDto feedItem) throws FeedItemTypeNotSupportedException {
        ImageDto image = feedItem.getImageFull();
        if (image != null) {
            if (image.isGif()) {
                throw new FeedItemTypeNotSupportedException(parent.getString(R.string.gif_not_supported));
            } else {
                parent.startActivity(ImageViewerActivity.createIntent(parent,
                        feedItem.getImageFull().getUrl(), feedItem.getTitle()));
            }
        }

    }
}
