package com.unsera.hotel;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.unsera.hotel.helpers.TokenManager;

public class DetailKamarActivity extends AppCompatActivity {

    ImageView imageKamar;
    TextView namaKamar, hargaKamar, deskripsiKamar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_kamar);

        imageKamar = findViewById(R.id.imageKamar);
        namaKamar = findViewById(R.id.tvNamaKamar);
        hargaKamar = findViewById(R.id.tvHarga);
        deskripsiKamar = findViewById(R.id.tvDeskripsi);

        Integer kamarId = getIntent().getIntExtra("KAMAR_ID", 0);

        TokenManager tokenManager = new TokenManager(this);


    }


}
