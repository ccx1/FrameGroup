package com.android.xjhttp.interceptors;

import android.support.annotation.NonNull;

import com.android.xjhttp.model.RequestException;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class BasicInterceptor implements Interceptor {
    @Override
    public Response intercept(@NonNull Chain chain) throws IOException {
        Request.Builder builder = chain.request().newBuilder();
        builder.addHeader("User-Agent", "android")
                .addHeader("Content-Type", "application/json; charset=utf-8")
                .addHeader("Connection", "Keep-Alive");
        Response response = chain.proceed(builder.build());


        if (response.code() >= 200 && response.code() < 400) {
            return response;
        }

        throw new RequestException(response.code(), response.message());

    }
}