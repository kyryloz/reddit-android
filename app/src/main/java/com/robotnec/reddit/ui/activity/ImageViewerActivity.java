package com.robotnec.reddit.ui.activity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;

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

    @BindView(R.id.imageView)
    ImageView imageView;

    @BindView(R.id.progressBar)
    ProgressBar progressBar;

    @BindView(R.id.saveButton)
    Button saveButton;

    @BindView(R.id.rootView)
    ViewGroup rootView;

    private String imageUrl;

    public static Intent createIntent(Context context, String imageUrl) {
        if (TextUtils.isEmpty(imageUrl)) {
            throw new IllegalArgumentException("imageUrl must not be empty");
        }
        Intent intent = new Intent(context, ImageViewerActivity.class);
        intent.putExtra(EXTRA_IMAGE_URL, imageUrl);
        return intent;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        imageUrl = getIntent().getStringExtra(EXTRA_IMAGE_URL);
        Picasso.with(this)
                .load(imageUrl)
                .fit()
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

    @OnClick(R.id.saveButton)
    void onSaveButtonClick() {
        Dexter.withActivity(this)
                .withPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .withListener(createPermissionListener())
                .check();
    }

    @Override
    public void showProgress(boolean inProgress) {
        progressBar.setVisibility(inProgress ? View.VISIBLE : View.INVISIBLE);
        saveButton.setEnabled(!inProgress);
    }

    @Override
    public void showError(String errorMessage) {
        Snackbar.make(rootView, errorMessage, Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void showSaveImageSuccess() {
        Snackbar.make(rootView, R.string.successfully_saved, Snackbar.LENGTH_SHORT).show();
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
