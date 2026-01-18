package com.unsera.hotel.api;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {
    private static Retrofit retrofit = null;

    public static NinjaApiService getApiService(String userToken) {
        // Buat client dengan interceptor
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(new AuthInterceptor(userToken))
                .build();

        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl("https://6b3be6d398f1.ngrok-free.app") // Ganti dengan URL server Anda
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(client)
                    .build();
        }
        return retrofit.create(NinjaApiService.class);
    }
}