package com.elsa.garagesale.data;

public class Datalistbarang {
    String id_barang;
    String id_penjual;
    String nama_barang;
    String deskripsi;
    int harga;
    String rating_total;
    String tanggal_masuk;
    String gambar_preview;

    public Datalistbarang(String id_barang2, String id_penjual2, String nama_barang2, int harga2, String rating_total2, String tanggal_masuk2, String gambar_preview2) {
        id_penjual = id_penjual2;
        id_barang = id_barang2;
        nama_barang = nama_barang2;
        harga = harga2;
        rating_total = rating_total2;
        tanggal_masuk = tanggal_masuk2;
        gambar_preview = gambar_preview2;
    }

    public String getId_barang() {
        return id_barang;
    }

    public String getId_penjual() {
        return id_penjual;
    }

    public String getNama_barang() {
        return nama_barang;
    }

    public int getHarga() {
        return harga;
    }

    public String getRating_total() {
        return rating_total;
    }

    public String getTanggal_masuk() {
        return tanggal_masuk;
    }

    public String getGambar_preview() {
        return gambar_preview;
    }
}
