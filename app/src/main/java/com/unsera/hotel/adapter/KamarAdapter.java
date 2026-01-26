package com.unsera.hotel.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.card.MaterialCardView;
import com.unsera.hotel.KamarActivity;
import com.unsera.hotel.R;

import java.util.List;

public class KamarAdapter extends RecyclerView.Adapter<KamarAdapter.ViewHolder> {

    Context context;
    List<String> tipeKamarList;

    public KamarAdapter(Context context, List<String> tipeKamarList) {
        this.context = context;
        this.tipeKamarList = tipeKamarList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context)
                .inflate(R.layout.item_kamar_standard, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        String tipeKamar = tipeKamarList.get(position);

        holder.cardKamar.setOnClickListener(v -> {
            Intent intent = new Intent(context, KamarActivity.class);
            intent.putExtra("TIPE_KAMAR", tipeKamar);
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return tipeKamarList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        MaterialCardView cardKamar;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            cardKamar = itemView.findViewById(R.id.cardKamarStandard);
        }
    }
}
