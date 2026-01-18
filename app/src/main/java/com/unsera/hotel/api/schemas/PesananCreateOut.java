package com.unsera.hotel.api.schemas;
import com.google.gson.annotations.SerializedName;

public class PesananCreateOut {
    int id;
    @SerializedName("kamar_nama") String kamarNama;
    @SerializedName("harga_total") int hargaTotal;
    @SerializedName("payment_link") String paymentLink;
}