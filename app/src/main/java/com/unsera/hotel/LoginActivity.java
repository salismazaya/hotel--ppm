package com.unsera.hotel;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.unsera.hotel.api.NinjaApiService;
import com.unsera.hotel.api.RetrofitClient;
import com.unsera.hotel.api.schemas.LoginIn;
import com.unsera.hotel.api.schemas.TokenOut;
import com.unsera.hotel.helpers.TokenManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    private EditText etUsername, etPassword;
    private Button btnLogin, btnRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);

        etUsername = findViewById(R.id.etUsername);
        etPassword = findViewById(R.id.etPassword);
        btnLogin = findViewById(R.id.btnLogin); // Pastikan di XML button Anda memiliki id: btnLogin
        btnRegister = findViewById(R.id.btnRegister);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Listener untuk tombol login
        btnLogin.setOnClickListener(v -> {
            handleLogin();
        });

        btnRegister.setOnClickListener(v -> {
            Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
            startActivity(intent);
        });
    }

    private void handleLogin() {
        // Mengambil input dari user
        String username = etUsername.getText().toString().trim();
        String password = etPassword.getText().toString().trim();

        // Validasi sederhana sebelum menjalankan logika Anda
        if (username.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Username dan Password tidak boleh kosong", Toast.LENGTH_SHORT).show();
            return;
        }

        NinjaApiService apiService = RetrofitClient.getApiService(null);
        apiService.login(new LoginIn(username, password)).enqueue(new Callback<TokenOut>() {
            @Override
            public void onResponse(Call<TokenOut> call, Response<TokenOut> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(LoginActivity.this, "Username atau Password tidak tepat", Toast.LENGTH_SHORT).show();
                    return;
                }

                Toast.makeText(LoginActivity.this, "Login berhasil!", Toast.LENGTH_SHORT).show();
                String token = response.body().token;
//
                TokenManager tokenManager = new TokenManager(getApplicationContext());
                tokenManager.saveToken(token);

                Log.d("CURRENT_TOKEN", tokenManager.getToken());
//
//                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
//                startActivity(intent);
//                finish();

                // Gunakan Handler untuk menunda finish() selama 2 detik (2000ms)
                new android.os.Handler(android.os.Looper.getMainLooper()).postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        // Kode di dalam sini akan dijalankan setelah jeda 2 detik
//                         Intent intent = new Intent(LoginActivity.this, MainActivity.class);
//                         startActivity(intent);
                        finishAffinity();
                        System.exit(0);
                    }
                }, 2000); // 2000 milidetik = 2 detik
            }


            @Override
            public void onFailure(Call<TokenOut> call, Throwable t) {

            }
        });
    }
}