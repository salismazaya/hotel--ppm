package com.unsera.hotel.api.schemas;

public class KategoriKamar {

    private final int imageRes;
    private final String nama;

    public KategoriKamar(int imageRes, String nama) {
        this.imageRes = imageRes;
        this.nama = nama;
    }

    public int getImageRes() {
        return imageRes;
    }

    public String getNama() {
        return nama;
    }
}
