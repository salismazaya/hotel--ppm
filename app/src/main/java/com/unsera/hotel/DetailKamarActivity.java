package com.unsera.hotel;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

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

        String tipe = getIntent().getStringExtra("TIPE_KAMAR");

        if (tipe == null) return;

        switch (tipe) {
            case "standard":
                setStandard();
                break;
            case "deluxe":
                setDeluxe();
                break;
            case "superior":
                setSuperior();
                break;
            case "suite":
                setSuite();
                break;
            case "twin":
                setTwin();
                break;
            case "presidential":
                setPresidential();
                break;
        }
    }

    private void setStandard() {
        imageKamar.setImageResource(R.drawable.standard);
        namaKamar.setText("Standard Room");
        hargaKamar.setText("Rp 450.000 / malam");
        deskripsiKamar.setText("Standard Room nyaman dan modern.");
    }

    private void setDeluxe() {
        imageKamar.setImageResource(R.drawable.deluxe);
        namaKamar.setText("Deluxe Room");
        hargaKamar.setText("Rp 650.000 / malam");
        deskripsiKamar.setText("Deluxe Room dengan fasilitas premium.");
    }

    private void setSuperior() {
        imageKamar.setImageResource(R.drawable.superior);
        namaKamar.setText("Superior Room");
        hargaKamar.setText("Rp 550.000 / malam");
        deskripsiKamar.setText("Superior Room luas dan elegan.");
    }

    private void setSuite() {
        imageKamar.setImageResource(R.drawable.suite);
        namaKamar.setText("Suite Room");
        hargaKamar.setText("Rp 1.200.000 / malam");
        deskripsiKamar.setText("Suite Room eksklusif dan mewah.");
    }

    private void setTwin() {
        imageKamar.setImageResource(R.drawable.twin);
        namaKamar.setText("Twin Room");
        hargaKamar.setText("Rp 500.000 / malam");
        deskripsiKamar.setText("Twin Room dengan dua tempat tidur.");
    }

    private void setPresidential() {
        imageKamar.setImageResource(R.drawable.presidential);
        namaKamar.setText("Presidential Suite");
        hargaKamar.setText("Rp 3.500.000 / malam");
        deskripsiKamar.setText("Presidential Suite kelas tertinggi.");
    }
}
