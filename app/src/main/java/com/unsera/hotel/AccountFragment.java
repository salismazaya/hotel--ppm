package com.unsera.hotel;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.unsera.hotel.api.NinjaApiService;
import com.unsera.hotel.api.RetrofitClient;
import com.unsera.hotel.api.schemas.UserOut;
import com.unsera.hotel.api.schemas.UserProfileOut;
import com.unsera.hotel.api.schemas.UserUpdateIn;
import com.unsera.hotel.helpers.TokenManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AccountFragment extends Fragment {
    EditText etFullname;
    EditText etUsername;
    Button btnSave;
    Activity parrent;


    public AccountFragment(Activity parrent) {
        this.parrent = parrent;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_edit_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        etFullname = view.findViewById(R.id.etFullName);
        etUsername = view.findViewById(R.id.etUsername);
        btnSave = view.findViewById(R.id.btnSave);

        TokenManager tokenManager = new TokenManager(getContext());
        String token = tokenManager.getToken();

        NinjaApiService apiService = RetrofitClient.getApiService(token);

        apiService.getMe().enqueue(new Callback<UserOut>() {
            @Override
            public void onResponse(Call<UserOut> call, Response<UserOut> response) {
                assert response.body() != null;
                String fullname = response.body().fullname;
                String username = response.body().username;

                etFullname.setText(fullname);
                etUsername.setText(username);

            }

            @Override
            public void onFailure(Call<UserOut> call, Throwable t) {

            }
        });

        btnSave.setOnClickListener(v -> {
            apiService.updateProfile(new UserUpdateIn(
                    etFullname.getText().toString(),
                    etUsername.getText().toString()
            )).enqueue(new Callback<UserProfileOut>() {
                @Override
                public void onResponse(Call<UserProfileOut> call, Response<UserProfileOut> response) {
                    Intent i = new Intent(view.getContext(), MainActivity.class);
                    startActivity(i);
                    parrent.finish();
                }

                @Override
                public void onFailure(Call<UserProfileOut> call, Throwable t) {

                }
            });
        });

    }
}
