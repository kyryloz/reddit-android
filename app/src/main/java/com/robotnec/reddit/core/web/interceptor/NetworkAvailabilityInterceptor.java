package com.robotnec.reddit.core.web.interceptor;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.robotnec.reddit.R;
import com.robotnec.reddit.core.exception.NoConnectivityException;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;


public class NetworkAvailabilityInterceptor implements Interceptor {

    private final ConnectivityManager mConnectivityManager;
    private final String errorMessage;

    public NetworkAvailabilityInterceptor(Context context) {
        mConnectivityManager = (ConnectivityManager)
                context.getSystemService(Context.CONNECTIVITY_SERVICE);
        errorMessage = context.getString(R.string.no_connectivity_message);
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        if (!isConnected()) {
            throw new NoConnectivityException(errorMessage);
        }
        Request.Builder r = chain.request().newBuilder();
        return chain.proceed(r.build());
    }

    protected boolean isConnected() {
        NetworkInfo networkInfo = mConnectivityManager.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnectedOrConnecting();
    }
}
