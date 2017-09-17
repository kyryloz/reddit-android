package com.robotnec.reddit.core.service.impl;

import android.content.Context;

import com.robotnec.reddit.R;
import com.robotnec.reddit.core.androidservice.ImageDownloadingAndroidService;
import com.robotnec.reddit.core.exception.NoConnectivityException;
import com.robotnec.reddit.core.service.ImageService;
import com.robotnec.reddit.core.util.ConnectivityChecker;

public class ImageServiceImpl implements ImageService {

    private final Context context;

    public ImageServiceImpl(Context context) {
        this.context = context;
    }

    @Override
    public void saveImageToExternalStorage(String imageUrl) throws NoConnectivityException {
        if (ConnectivityChecker.isNetworkAvailable(context)) {
            ImageDownloadingAndroidService.downloadImage(context, imageUrl);
        } else {
            throw new NoConnectivityException(context.getString(R.string.no_connectivity_message));
        }
    }
}
