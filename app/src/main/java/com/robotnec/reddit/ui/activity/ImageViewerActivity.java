package com.robotnec.reddit.ui.activity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.text.TextUtils;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.karumi.dexter.Dexter;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.single.BasePermissionListener;
import com.karumi.dexter.listener.single.CompositePermissionListener;
import com.karumi.dexter.listener.single.PermissionListener;
import com.karumi.dexter.listener.single.SnackbarOnDeniedPermissionListener;
import com.robotnec.reddit.R;
import com.robotnec.reddit.core.mvp.presenter.ImageViewerPresenter;
import com.robotnec.reddit.core.mvp.view.ImageViewerView;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.OnClick;

public class ImageViewerActivity extends BasePresenterActivity<ImageViewerPresenter, ImageViewerView>
        implements ImageViewerView {

    private static final String EXTRA_IMAGE_URL = "image_url";
    private static final String EXTRA_TITLE = "title";

    @BindView(R.id.imageView)
    ImageView imageView;

    @BindView(R.id.saveButton)
    Button saveButton;

    @BindView(R.id.rootView)
    ViewGroup rootView;

    private String imageUrl;

    public static Intent createIntent(Context context, String imageUrl, String title) {
        if (TextUtils.isEmpty(imageUrl)) {
            throw new IllegalArgumentException("imageUrl must not be empty");
        }
        Intent intent = new Intent(context, ImageViewerActivity.class);
        intent.putExtra(EXTRA_IMAGE_URL, imageUrl);
        intent.putExtra(EXTRA_TITLE, title);
        return intent;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(getIntent().getStringExtra(EXTRA_TITLE));
        imageUrl = getIntent().getStringExtra(EXTRA_IMAGE_URL);
        Picasso.with(this)
                .load(imageUrl)
                .into(imageView);
    }

    @Override
    protected ImageViewerPresenter createPresenter() {
        return new ImageViewerPresenter(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_image_viewer;
    }

    @Override
    public void showError(String message) {
        Snackbar.make(rootView, message, Snackbar.LENGTH_SHORT).show();
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
                        presenter.saveImageToExternalStorage(imageUrl);
                    }
                });
    }
}
