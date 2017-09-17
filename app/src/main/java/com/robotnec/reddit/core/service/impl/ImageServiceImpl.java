package com.robotnec.reddit.core.service.impl;

import android.content.Context;

import com.robotnec.reddit.core.androidservice.ImageDownloadingAndroidService;
import com.robotnec.reddit.core.service.ImageService;

public class ImageServiceImpl implements ImageService {

    private final Context context;

    public ImageServiceImpl(Context context) {
        this.context = context;
    }

    @Override
    public void saveImageToExternalStorage(String imageUrl) {
        ImageDownloadingAndroidService.downloadImage(context, imageUrl);
    }
}
