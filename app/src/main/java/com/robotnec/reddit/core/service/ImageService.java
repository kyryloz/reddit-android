package com.robotnec.reddit.core.service;

import com.robotnec.reddit.core.mvp.model.Result;

import java.io.File;

import io.reactivex.Observable;

public interface ImageService {
    Observable<Result<File>> saveImageToExternalStorage(String imageUrl);
}
