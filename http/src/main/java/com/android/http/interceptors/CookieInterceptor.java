package com.android.http.interceptors;


import androidx.annotation.NonNull;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * @author chicunxiang
 */
public abstract class CookieInterceptor implements Interceptor {
    @Override
    public Response intercept(@NonNull Chain chain) throws IOException {
        Request.Builder builder = chain.request().newBuilder();
        String          cookies = getCookies();
        if (cookies != null) {
            builder.addHeader("Set-Cookie", cookies);
        }
        return chain.proceed(builder.build());
    }

    public abstract String getCookies();
}
