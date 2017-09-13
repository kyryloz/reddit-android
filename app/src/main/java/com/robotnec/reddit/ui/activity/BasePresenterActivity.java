package com.robotnec.reddit.ui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.robotnec.reddit.RedditApplication;
import com.robotnec.reddit.core.di.ApplicationComponent;
import com.robotnec.reddit.core.mvp.presenter.Presenter;
import com.robotnec.reddit.core.mvp.view.View;

import butterknife.ButterKnife;

public abstract class BasePresenterActivity<P extends Presenter<V>, V extends View>
        extends AppCompatActivity implements View {

    protected P presenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        ButterKnife.bind(this);
        presenter = createPresenter();
        ApplicationComponent component = ((RedditApplication) getApplication()).getApplicationComponent();
        presenter.injectComponent(component);
    }

    protected abstract P createPresenter();

    protected abstract int getLayoutId();
}
