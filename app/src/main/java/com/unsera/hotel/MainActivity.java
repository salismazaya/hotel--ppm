package com.unsera.hotel;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.GridView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.unsera.hotel.api.NinjaApiService;
import com.unsera.hotel.api.RetrofitClient;
import com.unsera.hotel.api.schemas.KamarOut;
import com.unsera.hotel.api.schemas.UserOut;
import com.unsera.hotel.helpers.TokenManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    TextView namaUser;

    private  void changeFragment(Fragment fragment) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.frame, fragment);
        ft.commit();
    }

    private void toLogin() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    private void init() {
        TokenManager tokenManager = new TokenManager(this);
        String token = tokenManager.getToken();

        if (token == null) {
            toLogin();
            return;
        }

        NinjaApiService apiService = RetrofitClient.getApiService(token);
        apiService.getMe().enqueue(new Callback<UserOut>() {
            @Override
            public void onResponse(Call<UserOut> call, Response<UserOut> response) {
                if (!response.isSuccessful()) {
                    toLogin();
                    return;
                }

                String nama = response.body().fullname;
                namaUser.setText(nama);
            }

            @Override
            public void onFailure(Call<UserOut> call, Throwable t) {

            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        namaUser = findViewById(R.id.namaUser);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        init();
        changeFragment(new MainFragment());

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                if (menuItem.getItemId() == R.id.nav_my_order) {
                    changeFragment(new MyOrder());
                    return true;
                } else if (menuItem.getItemId() == R.id.nav_order) {
                    changeFragment(new MainFragment());
                    return  true;
                } else if (menuItem.getItemId() == R.id.nav_account) {
                    changeFragment(new AccountFragment(MainActivity.this));
                    return  true;
                }
                return false;
            }
        });

    }

}