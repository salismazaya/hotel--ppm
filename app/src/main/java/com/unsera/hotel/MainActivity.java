package com.unsera.hotel;

import android.content.Intent;
import android.os.Bundle;
import android.widget.GridView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.unsera.hotel.adapter.KategoriAdapter;
import com.unsera.hotel.api.NinjaApiService;
import com.unsera.hotel.api.RetrofitClient;
import com.unsera.hotel.api.schemas.KategoriOut;
import com.unsera.hotel.helpers.TokenManager;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TokenManager tokenManager = new TokenManager(this);
        String token = tokenManager.getToken();
        if (token == null) {
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
            return;
        }

        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

//        KategoriAdapter kategoriAdapter = new KategoriAdapter(this, )
//        String token = TokenManager()
        NinjaApiService apiService = RetrofitClient.getApiService(token);

        apiService.listKategori().enqueue(new Callback<List<KategoriOut>>() {
            @Override
            public void onResponse(Call<List<KategoriOut>> call, Response<List<KategoriOut>> response) {
                if (response.isSuccessful()) {
                    ArrayList<KategoriOut> kategoriOuts = (ArrayList<KategoriOut>) response.body();
                    KategoriAdapter adapter = new KategoriAdapter(MainActivity.this, kategoriOuts);
                    GridView gridView = findViewById(R.id.grid_view);
                    gridView.setAdapter(adapter);
                }
            }

            @Override
            public void onFailure(Call<List<KategoriOut>> call, Throwable t) {

            }
        });
    }

}