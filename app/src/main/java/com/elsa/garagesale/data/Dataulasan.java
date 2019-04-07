package com.elsa.garagesale.data;

public class Dataulasan {
    String nama_lengkap;
    String rating;
    String tanggal_masuk;
    String komentar;
    String foto_profil;

    public Dataulasan(String nama_lengkap2, String rating2, String tanggal_masuk2, String komentar2, String foto_profil2) {
        nama_lengkap = nama_lengkap2;
        rating = rating2;
        tanggal_masuk = tanggal_masuk2;
        komentar = komentar2;
        foto_profil = foto_profil2;
    }

    public String getNama_lengkap() {
        return nama_lengkap;
    }

    public String getFoto_profil() {
        return foto_profil;
    }

    public String getKomentar() {
        return komentar;
    }

    public String getRating() {
        return rating;
    }

    public String getTanggal_masuk() {
        return tanggal_masuk;
    }
}
