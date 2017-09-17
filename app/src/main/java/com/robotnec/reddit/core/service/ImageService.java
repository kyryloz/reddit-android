package com.robotnec.reddit.core.service;

import com.robotnec.reddit.core.exception.NoConnectivityException;

public interface ImageService {
    void saveImageToExternalStorage(String imageUrl) throws NoConnectivityException;
}
