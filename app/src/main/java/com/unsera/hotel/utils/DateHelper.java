package com.unsera.hotel.utils;

import android.os.Build;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class DateHelper {

    // Input: "2023-10-25" -> Output: "25 Oktober 2023"
    public static String formatTanggalIndonesia(String rawDate) {
        if (rawDate == null || rawDate.isEmpty()) {
            return "-";
        }

        try {
            // 1. Parsing dari format default Python (YYYY-MM-DD)
            LocalDate date = null;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                date = LocalDate.parse(rawDate);
            }

            // 2. Format ke tampilan Indonesia
            DateTimeFormatter formatter = null;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                formatter = DateTimeFormatter.ofPattern("dd MMMM yyyy", new Locale("id", "ID"));
            }

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                return date.format(formatter);
            }

            return  rawDate;
        } catch (Exception e) {
            e.printStackTrace();
            return rawDate; // Kembalikan string asli jika error
        }
    }
}