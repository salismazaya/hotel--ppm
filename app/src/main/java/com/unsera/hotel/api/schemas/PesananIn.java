package com.unsera.hotel.api.schemas;

import com.google.gson.annotations.SerializedName;

public class PesananIn {
    @SerializedName("kamar_id") int kamarId;
    @SerializedName("mulai_tanggal") String mulaiTanggal; // Format: YYYY-MM-DD
    @SerializedName("berakhir_tanggal") String berakhirTanggal;
}