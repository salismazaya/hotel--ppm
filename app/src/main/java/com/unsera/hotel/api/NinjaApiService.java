package com.unsera.hotel.api;

import com.unsera.hotel.api.schemas.KamarOut;
import com.unsera.hotel.api.schemas.KategoriOut;
import com.unsera.hotel.api.schemas.LoginIn;
import com.unsera.hotel.api.schemas.MessageOut;
import com.unsera.hotel.api.schemas.MyPesananOut;
import com.unsera.hotel.api.schemas.PesananCreateOut;
import com.unsera.hotel.api.schemas.PesananIn;
import com.unsera.hotel.api.schemas.RegisterIn;
import com.unsera.hotel.api.schemas.RegisterOut;
import com.unsera.hotel.api.schemas.TokenOut;
import com.unsera.hotel.api.schemas.UserOut;
import com.unsera.hotel.api.schemas.UserProfileOut;
import com.unsera.hotel.api.schemas.UserUpdateIn;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface NinjaApiService {
    @POST("api/register")
    Call<RegisterOut> register(@Body RegisterIn body);

    @POST("api/login")
    Call<TokenOut> login(@Body LoginIn body);

    // --- Kamar & Kategori ---
    @GET("api/kategori")
    Call<List<KategoriOut>> listKategori();

    @GET("api/kamar")
    Call<List<KamarOut>> listKamar(
            @Query("kategori_id") Integer kategoriId
    );

    @GET("api/kamar/{kamar_id}")
    Call<KamarOut> detailKamar(@Path("kamar_id") int kamarId);

    // --- Pesanan (Membutuhkan Token Auth) ---
    @POST("api/pesanan")
    Call<PesananCreateOut> buatPesanan(
            @Header("Authorization") String token,
            @Body PesananIn body
    );

    @DELETE("api/pesanan/{pesanan_id}")
    Call<MessageOut> batalkanPesanan(
            @Header("Authorization") String token,
            @Path("pesanan_id") int pesananId
    );

    @GET("api/pesanan-saya")
    Call<List<MyPesananOut>> cekPesananSaya(
    );

    // --- Profile ---
    @GET("api/me")
    Call<UserOut> getMe();
    @PATCH("api/user/update")
    Call<UserProfileOut> updateProfile(
            @Body UserUpdateIn body
    );

    // --- Webhook ---
    @POST("api/webhook/payment-confirm")
    Call<Void> paymentWebhook(@Body Object webhookPayload);
}