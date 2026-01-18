package com.unsera.hotel.api;

import androidx.annotation.NonNull;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class AuthInterceptor implements Interceptor {
    private String token;

    public AuthInterceptor(String token) {
        this.token = token;
    }

    @NonNull
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request originalRequest = chain.request();

        if (token == null || token.isEmpty()) {
            return chain.proceed(originalRequest);
        }

        // Tambahkan header Authorization
        Request newRequest = originalRequest.newBuilder()
                .header("Authorization", token) // Sesuai skema openapi.json: name: Authorization
                .build();

        return chain.proceed(newRequest);
    }
}