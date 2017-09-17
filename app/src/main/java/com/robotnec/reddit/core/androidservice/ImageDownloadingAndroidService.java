package com.robotnec.reddit.core.androidservice;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.JobIntentService;
import android.support.v4.app.NotificationCompat;

import com.robotnec.reddit.BuildConfig;
import com.robotnec.reddit.R;
import com.squareup.picasso.Picasso;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class ImageDownloadingAndroidService extends JobIntentService {

    private static final String EXTRA_IMAGE_URL = "image_url";
    private static final String DIRECTORY_NAME = "Reddit Images";

    private final Logger logger = LoggerFactory.getLogger(ImageDownloadingAndroidService.class);

    private Picasso picasso;
    private NotificationManager notifyManager;
    private NotificationCompat.Builder notificationBuilder;

    public static void downloadImage(Context context, String url) {
        Intent intent = new Intent();
        intent.putExtra(EXTRA_IMAGE_URL, url);
        JobIntentService.enqueueWork(context, ImageDownloadingAndroidService.class, 0, intent);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        picasso = Picasso.with(this);
        notifyManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationBuilder = new NotificationCompat.Builder(this, BuildConfig.APPLICATION_ID);
        notificationBuilder.setContentTitle(getString(R.string.image_download))
                .setContentText(getString(R.string.download_in_progress))
                .setSmallIcon(R.drawable.ic_download_image);
    }

    @Override
    protected void onHandleWork(@NonNull Intent intent) {
        String imageUrl = intent.getStringExtra(EXTRA_IMAGE_URL);
        logger.debug("Loading image '{}'...", imageUrl);

        try {
            File file = createOutputImageFile(imageUrl);
            if (file != null) {
                if (file.exists()) {
                    logger.debug("Already downloaded!");
                    stopNotification(Uri.fromFile(file));
                    return;
                }
                startNotification();
                Bitmap bitmap = picasso.load(imageUrl).get();
                logger.debug("Bitmap '{}' loaded, saving...", imageUrl);
                saveToFile(bitmap, file);
            } else {
                logger.error("Failed to create image file");
            }

        } catch (IOException e) {
            logger.error("Failed to load image");
        }
    }

    private void saveToFile(Bitmap bitmap, File file) {
        try (FileOutputStream outStream = new FileOutputStream(file)) {
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outStream);
            outStream.flush();
        } catch (IOException e) {
            logger.error("Failed to save bitmap to '{}'", file, e);
        }

        logger.debug("Bitmap successfully saved to: {}", file);

        Uri contentUri = Uri.fromFile(file);
        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        mediaScanIntent.setData(contentUri);
        sendBroadcast(mediaScanIntent);

        stopNotification(contentUri);
    }

    @Nullable
    private File createOutputImageFile(String imageUrl) {
        if (!Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            logger.warn("External storage not mounted!");
            return null;
        }

        File imagesDir = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES), DIRECTORY_NAME);

        if (!imagesDir.exists()) {
            if (!imagesDir.mkdirs()) {
                logger.warn("Failed to create '{}' directory!", DIRECTORY_NAME);
                return null;
            }
        }

        String fileName = Uri.parse(imageUrl).getLastPathSegment();
        File imageFile = new File(imagesDir.getPath(), fileName);

        logger.debug("Successfully created file for image: {}", imageFile);

        return imageFile;
    }

    private void startNotification() {
        notificationBuilder.setProgress(0, 0, true);
        notifyManager.notify(0, notificationBuilder.build());
    }

    private void stopNotification(Uri imageUri) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setDataAndType(imageUri, "image/jpeg");
        PendingIntent contentIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT);
        notificationBuilder.setContentText(getString(R.string.download_complete));
        notificationBuilder.setProgress(0, 0, false);
        notificationBuilder.setAutoCancel(true);
        notificationBuilder.setContentIntent(contentIntent);
        notifyManager.notify(0, notificationBuilder.build());
    }
}
