package com.unsera.hotel.api.schemas;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.unsera.hotel.R;

import java.util.List;

public class KategoriKamarAdapter
        extends RecyclerView.Adapter<KategoriKamarAdapter.ViewHolder> {

    private final List<KategoriKamar> kategoriList;

    public KategoriKamarAdapter(List<KategoriKamar> kategoriList) {
        this.kategoriList = kategoriList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_hotel_category, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        KategoriKamar item = kategoriList.get(position);

        // 🔥 INI WAJIB ADA
        holder.imgCategory.setImageResource(item.getImageRes());
        holder.tvCategoryName.setText(item.getNama());
    }

    @Override
    public int getItemCount() {
        return kategoriList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imgCategory;
        TextView tvCategoryName;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgCategory = itemView.findViewById(R.id.imgCategory);
            tvCategoryName = itemView.findViewById(R.id.tvCategoryName);
        }
    }
}
