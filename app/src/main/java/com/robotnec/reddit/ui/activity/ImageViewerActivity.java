package com.robotnec.reddit.ui.activity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.media.MediaScannerConnection;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.karumi.dexter.Dexter;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.single.BasePermissionListener;
import com.karumi.dexter.listener.single.CompositePermissionListener;
import com.karumi.dexter.listener.single.PermissionListener;
import com.karumi.dexter.listener.single.SnackbarOnDeniedPermissionListener;
import com.robotnec.reddit.R;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ImageViewerActivity extends AppCompatActivity {

    private static final String EXTRA_IMAGE_URL = "image_url";

    @BindView(R.id.imageView)
    ImageView imageView;

    @BindView(R.id.rootView)
    ViewGroup rootView;

    private String imageUrl;
    private Target target;

    public static Intent createIntent(Context context, String imageUrl) {
        Intent intent = new Intent(context, ImageViewerActivity.class);
        intent.putExtra(EXTRA_IMAGE_URL, imageUrl);
        return intent;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_viewer);
        ButterKnife.bind(this);

        imageUrl = getIntent().getStringExtra(EXTRA_IMAGE_URL);

        Picasso.with(this)
                .load(imageUrl)
                .into(imageView);
    }

    @OnClick(R.id.saveButton)
    void onSaveButtonClick() {
        Dexter.withActivity(this)
                .withPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .withListener(createPermissionListener())
                .check();
    }

    private PermissionListener createPermissionListener() {
        return new CompositePermissionListener(
                SnackbarOnDeniedPermissionListener.Builder
                        .with(rootView, getString(R.string.access_storage_permission_rationale))
                        .withDuration(Snackbar.LENGTH_INDEFINITE)
                        .withOpenSettingsButton(getString(R.string.settings)).build(),
                new BasePermissionListener() {
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse response) {
                        saveImage();
                    }
                });
    }

    private void saveImage() {
        target = new Target() {
            @Override
            public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                saveBitmap(bitmap);
            }

            @Override
            public void onBitmapFailed(Drawable errorDrawable) {

            }

            @Override
            public void onPrepareLoad(Drawable placeHolderDrawable) {

            }
        };
        Picasso.with(this)
                .load(imageUrl)
                .into(target);
    }

    private void saveBitmap(Bitmap bitmap) {
        File imagesDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);

        File imageFile = new File(imagesDir, "reddit-image.png");
        FileOutputStream outStream = null;
        try {
            outStream = new FileOutputStream(imageFile);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, outStream);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (outStream != null) {
                try {
                    outStream.flush();
                    outStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        MediaScannerConnection.scanFile(this,
                new String[]{imageFile.toString()}, null,
                (path, uri) -> Toast.makeText(this, "Saved", Toast.LENGTH_SHORT).show());
    }
}
