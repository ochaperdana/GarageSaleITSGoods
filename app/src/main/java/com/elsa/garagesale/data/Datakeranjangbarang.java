package com.elsa.garagesale.data;

public class Datakeranjangbarang {
    String id_keranjang;
    String id_user;
    String id_barang;
    String jumlah_barang;
    String harga_total;
    String nama_barang;
    String gambar_preview;
    String id_penjual;

    public Datakeranjangbarang(String id_keranjang2, String id_user2, String id_barang2, String jumlah_barang2, String harga_total2, String nama_barang2, String gambar_preview2, String id_penjual2) {
        id_keranjang = id_keranjang2;
        id_user = id_user2;
        id_barang = id_barang2;
        jumlah_barang = jumlah_barang2;
        harga_total = harga_total2;
        nama_barang = nama_barang2;
        gambar_preview = gambar_preview2;
        id_penjual = id_penjual2;
    }

    public String getId_keranjang() {
        return id_keranjang;
    }

    public String getId_user() {
        return id_user;
    }

    public String getId_barang() {
        return id_barang;
    }

    public String getJumlah_barang() {
        return jumlah_barang;
    }

    public String getHarga_total() {
        return harga_total;
    }

    public String getNama_barang() {
        return nama_barang;
    }

    public String getGambar_preview() {
        return gambar_preview;
    }

    public String getId_penjual() {
        return id_penjual;
    }
}
