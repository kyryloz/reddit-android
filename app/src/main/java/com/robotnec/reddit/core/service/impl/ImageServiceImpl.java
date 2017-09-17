package com.robotnec.reddit.core.service.impl;

import android.content.Context;
import android.graphics.Bitmap;
import android.media.MediaScannerConnection;

import com.robotnec.reddit.R;
import com.robotnec.reddit.core.exception.ImageLoadingException;
import com.robotnec.reddit.core.mvp.model.Result;
import com.robotnec.reddit.core.service.ImageService;
import com.robotnec.reddit.ui.util.StorageUtil;
import com.squareup.picasso.Picasso;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;

public class ImageServiceImpl implements ImageService {

    private final Logger logger = LoggerFactory.getLogger(ImageServiceImpl.class);

    private final Context context;
    private final Picasso picasso;

    public ImageServiceImpl(Context context) {
        this.context = context;
        this.picasso = Picasso.with(context);
    }

    @Override
    public Observable<Result<File>> saveImageToExternalStorage(String imageUrl) {
        return Observable.create(emitter -> {
            logger.debug("Loading image '{}'...", imageUrl);

            emitter.onNext(Result.inProgress());

            Bitmap bitmap = picasso.load(imageUrl).get();
            if (bitmap != null) {
                logger.debug("Bitmap '{}' loaded, saving...", imageUrl);
                StorageUtil.createOutputImageFile()
                        .executeIfPresent(file -> saveToFile(bitmap, file, emitter))
                        .executeIfAbsent(() -> emitter.onNext(createFailedResult()));
            } else {
                logger.error("Bitmap loading failed");
                emitter.onNext(createFailedResult());
                emitter.onComplete();
            }
        });
    }

    private void saveToFile(Bitmap bitmap, File file, ObservableEmitter<Result<File>> emitter) {
        try (FileOutputStream outStream = new FileOutputStream(file)) {
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outStream);
            outStream.flush();
        } catch (IOException e) {
            logger.error("Failed to save bitmap to '{}'", file, e);
            emitter.onNext(Result.failed(e));
            emitter.onComplete();
        }

        logger.debug("Bitmap successfully saved to: {}", file);

        MediaScannerConnection.scanFile(context, new String[]{file.getAbsolutePath()}, null,
                (path, uri) -> {
                    emitter.onNext(Result.success(file));
                    emitter.onComplete();
                });
    }

    private Result<File> createFailedResult() {
        String failedMessage = context.getString(R.string.failed_to_load_image);
        return Result.failed(new ImageLoadingException(failedMessage));
    }
}
