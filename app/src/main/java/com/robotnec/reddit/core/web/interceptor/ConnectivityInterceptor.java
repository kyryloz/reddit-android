package com.robotnec.reddit.core.web.interceptor;

import android.content.Context;

import com.robotnec.reddit.R;
import com.robotnec.reddit.core.exception.NoConnectivityException;
import com.robotnec.reddit.core.util.ConnectivityChecker;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Response;


public class ConnectivityInterceptor implements Interceptor {

    private final Context context;

    public ConnectivityInterceptor(Context context) {
        this.context = context;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        if (!isConnected()) {
            throw new NoConnectivityException(context.getString(R.string.no_connectivity_message));
        }
        return chain.proceed(chain
                .request()
                .newBuilder()
                .build());
    }

    private boolean isConnected() {
        return ConnectivityChecker.isNetworkAvailable(context);
    }
}
