package com.unsera.hotel;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.unsera.hotel.adapter.RoomAdapter;
import com.unsera.hotel.api.NinjaApiService;
import com.unsera.hotel.api.RetrofitClient;
import com.unsera.hotel.api.schemas.KamarOut;
import com.unsera.hotel.helpers.TokenManager;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class KamarActivity extends AppCompatActivity {

    Integer kategoriId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_kamar);

        kategoriId = getIntent().getIntExtra("KATEGORI_ID", 0);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.layout_kamar), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        ListView listView = findViewById(R.id.lvKamar);
//        listView.setAdapter();

        TokenManager tokenManager = new TokenManager(this);
        String token = tokenManager.getToken();
        NinjaApiService apiService = RetrofitClient.getApiService(token);

        apiService.listKamar(kategoriId).enqueue(new Callback<List<KamarOut>>() {
            @Override
            public void onResponse(Call<List<KamarOut>> call, Response<List<KamarOut>> response) {
                Log.d("KASEP", response.body().toString() + kategoriId);
                RoomAdapter adapter = new RoomAdapter(KamarActivity.this, response.body());
                listView.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<List<KamarOut>> call, Throwable t) {

            }
        });
    }



    private void bukaDetail(String tipe) {
        Intent intent = new Intent(this, DetailKamarActivity.class);
        intent.putExtra("TIPE_KAMAR", tipe);
        startActivity(intent);
    }
}
