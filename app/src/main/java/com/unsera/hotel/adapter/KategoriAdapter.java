package com.unsera.hotel.adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.unsera.hotel.R;
import com.unsera.hotel.api.schemas.KategoriOut;

import java.util.ArrayList;
public class KategoriAdapter extends ArrayAdapter<KategoriOut> {

    public KategoriAdapter(Context context, ArrayList<KategoriOut> list) {
        super(context, 0, list);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View itemView = convertView;
        if (itemView == null) {
            itemView = LayoutInflater.from(getContext()).inflate(R.layout.item_hotel_category, parent, false);
        }

        KategoriOut model = getItem(position);

        TextView textView = itemView.findViewById(R.id.tvCategoryName);
        ImageView imageView = itemView.findViewById(R.id.imgCategory);

        if (model != null) {
            textView.setText(model.nama);
            Glide.with(itemView.getContext())
                    .load("https://hotel.presensee.cloud/" + model.gambar)
                    .placeholder(R.drawable.standard)
                    .error(R.drawable.standard)
                    .into(imageView);
        }

        return itemView;
    }
}