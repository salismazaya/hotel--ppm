package com.unsera.hotel.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter; // Ubah ke BaseAdapter
import android.widget.TextView;

import com.unsera.hotel.R;
import com.unsera.hotel.api.schemas.MyPesananOut;
import com.unsera.hotel.utils.DateHelper;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

// PENTING: Gunakan extends BaseAdapter, bukan implements ListAdapter
public class MyOrderAdapter extends BaseAdapter {
    private List<MyPesananOut> myOrders;
    private Activity activity;
    private LayoutInflater inflater;

    public MyOrderAdapter(Activity activity, List<MyPesananOut> myOrders) {
        this.activity = activity;
        this.myOrders = myOrders;
        // Inisialisasi inflater sekali saja di constructor untuk performa
        this.inflater = LayoutInflater.from(activity);
    }

    @Override
    public int getCount() {
        return (myOrders == null) ? 0 : myOrders.size();
    }

    @Override
    public MyPesananOut getItem(int position) {
        return myOrders.get(position);
    }

    @Override
    public long getItemId(int position) {
        // Mengembalikan ID unik dari pesanan (dari database) lebih baik daripada 0
        return myOrders.get(position).id;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // 1. Daur ulang view (Recycling) agar tidak berat saat scroll
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item_pesanan, parent, false);
        }

        // 2. Binding Data
        TextView tvRoomName = convertView.findViewById(R.id.tvRoomName);
        TextView tvCheckin = convertView.findViewById(R.id.tvCheckIn);
        TextView tvCheckout = convertView.findViewById(R.id.tvCheckOut);
        TextView tvStatus = convertView.findViewById(R.id.tvStatus);
        TextView tvPrice = convertView.findViewById(R.id.tvPrice);

        MyPesananOut pesanan = getItem(position);

        tvRoomName.setText(pesanan.kamarNama);

        // Menggunakan helper tanggal yang kita buat sebelumnya
        tvCheckin.setText(DateHelper.formatTanggalIndonesia(pesanan.mulaiTanggal));
        tvCheckout.setText(DateHelper.formatTanggalIndonesia(pesanan.berakhirTanggal));

        // Logic status pembayaran sederhana
        if (pesanan.dibayar) {
            tvStatus.setText("Lunas");
            // Opsional: Ganti warna teks jika lunas (misal Hijau)
            // tvStatus.setTextColor(Color.GREEN);
        } else {
            tvStatus.setText("Belum Dibayar");
            // tvStatus.setTextColor(Color.RED);
        }

        // 3. PERBAIKAN FATAL: Format Harga (Rupiah)
        // Jangan langsung memasukkan int ke setText!
        Locale localeID = new Locale("id", "ID");
        NumberFormat formatRupiah = NumberFormat.getCurrencyInstance(localeID);
        // Menghilangkan desimal ,00 jika tidak diinginkan
        formatRupiah.setMaximumFractionDigits(0);

        tvPrice.setText(formatRupiah.format(pesanan.hargaTotal));

        return convertView;
    }
}