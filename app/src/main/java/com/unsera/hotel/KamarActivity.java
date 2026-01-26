package com.unsera.hotel;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class KamarActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_kamar);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.layout_kamar), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // ==============================
        // AMBIL VIEW DARI INCLUDE
        // ==============================
        View standard = findViewById(R.id.cardKamarStandard); // item pertama
        View deluxe = findViewById(R.id.cardKamarDeluxe);   // aman karena include beda root
        View superior = findViewById(R.id.cardKamarSuperior);
        View suite = findViewById(R.id.cardKamarSuite);
        View twin = findViewById(R.id.cardKamarTwin);
        View presidential = findViewById(R.id.cardKamarPresidential);

        // ==============================
        // SET CLICK
        // ==============================
        standard.setOnClickListener(v -> bukaDetail("standard"));
        deluxe.setOnClickListener(v -> bukaDetail("deluxe"));
        superior.setOnClickListener(v -> bukaDetail("superior"));
        suite.setOnClickListener(v -> bukaDetail("suite"));
        twin.setOnClickListener(v -> bukaDetail("twin"));
        presidential.setOnClickListener(v -> bukaDetail("presidential"));
    }

    private void bukaDetail(String tipe) {
        Intent intent = new Intent(this, DetailKamarActivity.class);
        intent.putExtra("TIPE_KAMAR", tipe);
        startActivity(intent);
    }
}
