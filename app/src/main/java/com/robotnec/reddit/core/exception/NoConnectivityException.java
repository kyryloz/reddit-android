package com.robotnec.reddit.core.exception;

import java.io.IOException;

public class NoConnectivityException extends IOException {
    public NoConnectivityException(String message) {
        super(message);
    }
}
