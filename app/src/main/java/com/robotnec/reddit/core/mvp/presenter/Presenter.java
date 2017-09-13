package com.robotnec.reddit.core.mvp.presenter;


import com.robotnec.reddit.core.di.ApplicationComponent;
import com.robotnec.reddit.core.mvp.view.View;

public abstract class Presenter<T extends View> {

    protected final T view;

    Presenter(T view) {
        this.view = view;
    }

    public abstract void injectComponent(ApplicationComponent applicationComponent);
}
