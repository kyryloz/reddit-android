package com.robotnec.reddit.core.mvp.model;

import android.support.annotation.NonNull;

public class Result<T> {
    private final T result;
    private final boolean inProgress;
    private final String errorMessage;

    public static <T> Result<T> success(T result) {
        return new Result<>(result, false, null);
    }

    public static <T> Result<T> failed(Throwable throwable) {
        if (throwable instanceof RuntimeException) {
            throw (RuntimeException) throwable;
        }
        return new Result<>(null, false, throwable.getMessage());
    }

    public static <T> Result<T> inProgress() {
        return new Result<>(null, true, null);
    }

    private Result(T result, boolean inProgress, String errorMessage) {
        this.result = result;
        this.inProgress = inProgress;
        this.errorMessage = errorMessage;
    }

    @NonNull
    public T getResult() {
        if (result == null) {
            throw new NullPointerException("Result doesn't exist");
        }
        return result;
    }

    public boolean isSuccess() {
        return result != null;
    }

    public boolean isInProgress() {
        return inProgress;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}
