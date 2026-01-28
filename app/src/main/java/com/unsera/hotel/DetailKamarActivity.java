package com.unsera.hotel;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.util.Pair;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.datepicker.CalendarConstraints;
import com.google.android.material.datepicker.DateValidatorPointForward;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.unsera.hotel.adapter.KamarImageAdapter;
import com.unsera.hotel.api.NinjaApiService;
import com.unsera.hotel.api.RetrofitClient;
import com.unsera.hotel.api.schemas.KamarOut;
import com.unsera.hotel.api.schemas.PesananCreateOut;
import com.unsera.hotel.api.schemas.PesananIn;
import com.unsera.hotel.helpers.TokenManager;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailKamarActivity extends AppCompatActivity {
    TextView namaKamar, hargaKamar, deskripsiKamar;
    Button btnOrder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_kamar);

//        imageKamar = findViewById(R.id.imageKamar);
        namaKamar = findViewById(R.id.tvNamaKamar);
        hargaKamar = findViewById(R.id.tvHarga);
        deskripsiKamar = findViewById(R.id.tvDeskripsi);
        btnOrder = findViewById(R.id.btnPesan);

        Integer kamarId = getIntent().getIntExtra("KAMAR_ID", 0);

        TokenManager tokenManager = new TokenManager(this);
        String token = tokenManager.getToken();

        NinjaApiService apiService = RetrofitClient.getApiService(token);

        apiService.detailKamar(kamarId).enqueue(new Callback<KamarOut>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(Call<KamarOut> call, Response<KamarOut> response) {
                String deskripsi = response.body().deskripsi;
                String name = response.body().nama;
                String[] gambars = response.body().gambars;
                int harga = response.body().harga;

                deskripsiKamar.setText(deskripsi);
                namaKamar.setText(name);
                hargaKamar.setText("Rp. " +Integer.toString(harga) + "/hari");

                ViewPager2 viewPager = findViewById(R.id.viewPagerKamar);

                List<String> images = new ArrayList<>();

                for (String gambar : gambars) {
                    images.add("https://hotel.presensee.cloud" + gambar);
                }

                KamarImageAdapter adapter =
                        new KamarImageAdapter(DetailKamarActivity.this, images);

                viewPager.setAdapter(adapter);

            }

            @Override
            public void onFailure(Call<KamarOut> call, Throwable t) {

            }
        });

        long today = MaterialDatePicker.todayInUtcMilliseconds();

        CalendarConstraints constraints = new CalendarConstraints.Builder()
                .setStart(today)
                .setValidator(DateValidatorPointForward.from(today))
                .build();

        MaterialDatePicker<Pair<Long, Long>> dateRangePicker =
                MaterialDatePicker.Builder.dateRangePicker()
                        .setTitleText("Pilih Rentang Tanggal")
                        .setCalendarConstraints(constraints)
                        .build();

        btnOrder.setOnClickListener(v -> {
            dateRangePicker.show(getSupportFragmentManager(), "DATE_RANGE");
        });

        dateRangePicker.addOnPositiveButtonClickListener(selection -> {
            Long startDate = selection.first;
            Long endDate = selection.second;

            Log.d("DATE_RANGE", "Start: " + startDate);
            Log.d("DATE_RANGE", "End: " + endDate);

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());

            String start = sdf.format(new Date(startDate));
            String end = sdf.format(new Date(endDate));

            apiService.buatPesanan(new PesananIn(
                    kamarId,
                    start,
                    end
            )).enqueue(new Callback<PesananCreateOut>() {
                @Override
                public void onResponse(Call<PesananCreateOut> call, Response<PesananCreateOut> response) {
//                    System.out.print(res)
//                    Log.d("KEREN", response.errorBody().toString());
                    if (response.isSuccessful()) {
                        Intent i = new Intent(DetailKamarActivity.this, WebViewActivity.class);
                        i.putExtra("URL", response.body().paymentLink);
                        startActivity(i);
                        finish();
                    } else {
                        try {
                            String errorJson = response.errorBody().string();

                            JsonObject jsonObject = JsonParser.parseString(errorJson).getAsJsonObject();
                            String message = jsonObject.get("detail").getAsString();

                            Log.e("API_ERROR", message);
                            Toast.makeText(DetailKamarActivity.this, message, Toast.LENGTH_LONG).show();

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }

                @Override
                public void onFailure(Call<PesananCreateOut> call, Throwable t) {

                }
            });

        });
    }


}
