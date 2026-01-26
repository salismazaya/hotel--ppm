package com.unsera.hotel;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;

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

public class MainFragment extends Fragment {

    public MainFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_main, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        TokenManager tokenManager = new TokenManager(requireContext());
        String token = tokenManager.getToken();
        if (token == null) {
            Intent intent = new Intent(requireActivity(), LoginActivity.class);
            startActivity(intent);
            requireActivity().finish();
        }

        EdgeToEdge.enable(requireActivity());

        View root = view.findViewById(R.id.main);
        if (root != null) {
            ViewCompat.setOnApplyWindowInsetsListener(root, (v, insets) -> {
                Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
                v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
                return insets;
            });
        }

        GridView gridView = view.findViewById(R.id.grid_view);

        NinjaApiService apiService = RetrofitClient.getApiService(token);
        apiService.listKategori().enqueue(new Callback<List<KategoriOut>>() {
            @Override
            public void onResponse(Call<List<KategoriOut>> call, Response<List<KategoriOut>> response) {
                if (response.isSuccessful() && response.body() != null) {

                    ArrayList<KategoriOut> kategoriOuts = new ArrayList<>(response.body());
                    KategoriAdapter adapter = new KategoriAdapter(requireContext(), kategoriOuts);
                    gridView.setAdapter(adapter);

                    // ==========================
                    // ✅ TAMBAHAN KODE KLIK ITEM
                    // ==========================
                    gridView.setOnItemClickListener((parent, view1, position, id) -> {
                        Intent intent = new Intent(requireActivity(), KamarActivity.class);
                        Integer kategoriId = response.body().get(position).id;                        intent.putExtra("KATEGORI_ID", id);
                        intent.putExtra("KATEGORI_ID", kategoriId);
                        Log.d("KATEGORI", kategoriId.toString());
//                        startActivity(intent);
                    });

                } else {
                    Toast.makeText(requireContext(), "Gagal memuat kategori", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<KategoriOut>> call, Throwable t) {
                Toast.makeText(requireContext(), "Kesalahan jaringan: " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
}
