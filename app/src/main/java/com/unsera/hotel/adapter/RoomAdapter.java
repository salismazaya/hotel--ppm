package com.unsera.hotel.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.unsera.hotel.DetailKamarActivity;
import com.unsera.hotel.KamarActivity;
import com.unsera.hotel.R;
import com.unsera.hotel.api.schemas.KamarOut;
import com.unsera.hotel.api.schemas.MyPesananOut;
import com.unsera.hotel.utils.DateHelper;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

public class RoomAdapter extends BaseAdapter {
    private List<KamarOut> rooms;
    private LayoutInflater inflater;
    Activity activity;

    public RoomAdapter(Activity activity, List<KamarOut> rooms) {
        this.rooms = rooms;
        this.activity = activity;
        this.inflater = LayoutInflater.from(activity);
    }

    @Override
    public int getCount() {
        return (rooms == null) ? 0 : rooms.size();
    }

    @Override
    public KamarOut getItem(int position) {
        return rooms.get(position);
    }

    @Override
    public long getItemId(int position) {
        // Mengembalikan ID unik dari pesanan (dari database) lebih baik daripada 0
        return rooms.get(position).id;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // 1. Daur ulang view (Recycling) agar tidak berat saat scroll
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item_kamar_standard, parent, false);
        }

        // 2. Binding Data

        TextView tvRoomName = convertView.findViewById(R.id.roomName);
        TextView tvDeskripsi = convertView.findViewById(R.id.tvDeskripsi);
        TextView tvHarga = convertView.findViewById(R.id.tvHarga);

        KamarOut room = getItem(position);

        tvRoomName.setText(room.nama);
        tvDeskripsi.setText(room.deskripsi);
        tvHarga.setText("Rp. " + String.valueOf(room.harga) + "/malam");

        convertView.findViewById(R.id.cardKamarStandard).setOnClickListener(v -> {
            Intent intent = new Intent(activity, DetailKamarActivity.class);
            Integer kamarId = room.id;
            intent.putExtra("KAMAR_ID", kamarId);
            activity.startActivity(intent);
        });

        return convertView;
    }
}