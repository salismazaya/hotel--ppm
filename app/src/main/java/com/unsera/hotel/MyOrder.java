package com.unsera.hotel;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.unsera.hotel.adapter.MyOrderAdapter;
import com.unsera.hotel.api.NinjaApiService;
import com.unsera.hotel.api.RetrofitClient;
import com.unsera.hotel.api.schemas.MyPesananOut;
import com.unsera.hotel.helpers.TokenManager;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MyOrder#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MyOrder extends Fragment {
    public MyOrder() {
    }

    public static MyOrder newInstance(String param1, String param2) {
        MyOrder fragment = new MyOrder();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_my_order, container, false);
    }

    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ListView lvPesanan = view.findViewById(R.id.lvPesanan);

        TokenManager tokenManager = new TokenManager(getContext());
        String token = tokenManager.getToken();
        NinjaApiService apiService = RetrofitClient.getApiService(token);

        apiService.cekPesananSaya().enqueue(new Callback<List<MyPesananOut>>() {
            @Override
            public void onResponse(Call<List<MyPesananOut>> call, Response<List<MyPesananOut>> response) {
                if (response.isSuccessful()) {
                    lvPesanan.setAdapter(new MyOrderAdapter(getActivity(), response.body()));
                }
            }

            @Override
            public void onFailure(Call<List<MyPesananOut>> call, Throwable t) {

            }
        });

    }
}