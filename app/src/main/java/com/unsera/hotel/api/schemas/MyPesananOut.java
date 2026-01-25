package com.unsera.hotel.api.schemas;

import com.google.gson.annotations.SerializedName;

public class MyPesananOut {

    public int id;

    @SerializedName("kamar_nama")
    public String kamarNama;

    // Field baru ditambahkan
    @SerializedName("kategori_nama")
    public String kategoriNama;

    @SerializedName("mulai_tanggal")
    public String mulaiTanggal;

    // Field baru ditambahkan
    @SerializedName("berakhir_tanggal")
    public String berakhirTanggal;

    public boolean dibayar;

    @SerializedName("harga_total")
    public int hargaTotal;
}