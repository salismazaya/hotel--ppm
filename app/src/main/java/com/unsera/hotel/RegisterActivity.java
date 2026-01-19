package com.unsera.hotel;

import android.content.Intent;
import android.os.Bundle;
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
import com.unsera.hotel.api.schemas.RegisterIn;
import com.unsera.hotel.api.schemas.RegisterOut;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {
    private EditText etName, etUsername, etPassword;
    private Button btnRegister, btnGoToLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_register);

        etName = findViewById(R.id.etName);
        etUsername = findViewById(R.id.etUsername);
        etPassword = findViewById(R.id.etPassword);
        btnRegister = findViewById(R.id.btnRegister);
        btnGoToLogin = findViewById(R.id.btnGoToLogin);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        btnRegister.setOnClickListener(v -> {
            handleRegister();
        });

        btnGoToLogin.setOnClickListener(v -> {
            finish();
        });
    }

    private void handleRegister() {
        String name = etName.getText().toString().trim();
        String username = etUsername.getText().toString().trim();
        String password = etPassword.getText().toString().trim();

        if (name.isEmpty()) {
            etName.setError("Nama harus diisi");
            return;
        }

        if (username.isEmpty()) {
            etUsername.setError("Username harus diisi");
            return;
        }

        if (password.length() < 6) {
            etPassword.setError("Password minimal 6 karakter");
            return;
        }

        executeRegisterApi(name, username, password);
    }

    private void executeRegisterApi(String name, String username, String password) {
        NinjaApiService apiService = RetrofitClient.getApiService(null);

        apiService.register(new RegisterIn(username, password, name)).enqueue(new Callback<RegisterOut>() {
            @Override
            public void onResponse(Call<RegisterOut> call, Response<RegisterOut> response) {
                if (!response.isSuccessful()) {
                    try {
                        String errorRawJson = response.errorBody().string();
                        JSONObject jsonObject = new JSONObject(errorRawJson);

                        String detailMessage = jsonObject.getString("detail");

                        Toast.makeText(RegisterActivity.this, detailMessage, Toast.LENGTH_LONG).show();
                    } catch (IOException | JSONException e) {

                    }

                } else {
                    Toast.makeText(RegisterActivity.this, "Berhasil mendaftar", Toast.LENGTH_LONG).show();
                    finish();
                }
            }

            @Override
            public void onFailure(Call<RegisterOut> call, Throwable t) {

            }
        });

    }
}