package com.unsera.hotel.api.schemas;
import com.google.gson.annotations.SerializedName;

public class MyPesananOut {
    int id;
    @SerializedName("kamar_nama")
    public String kamarNama;
    boolean dibayar;
    @SerializedName("harga_total")
    public int hargaTotal;
}