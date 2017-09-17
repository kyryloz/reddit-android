package com.robotnec.reddit.core.service;

import com.robotnec.reddit.core.mvp.model.Result;

import io.reactivex.Observable;

public interface ImageService {
    Observable<Result<Void>> saveImageToExternalStorage(String imageUrl);
}
