package com.robotnec.reddit.ui.util;

import android.net.Uri;
import android.os.Environment;

import com.annimon.stream.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class StorageUtil {

    private static final Logger logger = LoggerFactory.getLogger(StorageUtil.class);

    private static final String DIRECTORY_NAME = "Reddit Images";

    public static Optional<File> createOutputImageFile() {
        if (!Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            logger.warn("External storage not mounted!");
            return Optional.empty();
        }

        File imagesDir = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES), DIRECTORY_NAME);

        if (!imagesDir.exists()) {
            if (!imagesDir.mkdirs()) {
                logger.warn("Failed to create '{}' directory!", DIRECTORY_NAME);
                return Optional.empty();
            }
        }

        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.US).format(new Date());
        File imageFile = new File(imagesDir.getPath(), "IMG_" + timeStamp + ".png");

        logger.debug("Successfully created file for image: {}", imageFile);

        return Optional.of(imageFile);
    }
}
