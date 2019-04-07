package com.elsa.garagesale.data;

public class Datalistkonfirmasipembeli {
    String nama_barang;
    String id_pembayaran;
    String id_penjual;
    String id_user;
    String id_barang;
    String kurir;
    String jumlah_barang;
    String harga_total;
    String bukti_foto;
    String no_resi;
    String tanggal_mulai;
    String tanggal_selesai;
    String status;


    public Datalistkonfirmasipembeli(String nama_barang2, String id_pembayaran2, String id_penjual2, String id_user2, String id_barang2, String kurir2, String jumlah_barang2, String harga_total2, String bukti_foto2,
                                     String no_resi2, String tanggal_mulai2, String tanggal_selesai2, String status2) {
        nama_barang = nama_barang2;
        id_pembayaran = id_pembayaran2;
        id_penjual = id_penjual2;
        id_user = id_user2;
        id_barang = id_barang2;
        kurir = kurir2;
        jumlah_barang = jumlah_barang2;
        harga_total = harga_total2;
        bukti_foto = bukti_foto2;
        no_resi = no_resi2;
        tanggal_mulai = tanggal_mulai2;
        tanggal_selesai = tanggal_selesai2;
        status = status2;
    }

    public String getBukti_foto() {
        return bukti_foto;
    }

    public String getHarga_total() {
        return harga_total;
    }

    public String getId_barang() {
        return id_barang;
    }

    public String getId_pembayaran() {
        return id_pembayaran;
    }

    public String getId_penjual() {
        return id_penjual;
    }

    public String getId_user() {
        return id_user;
    }

    public String getJumlah_barang() {
        return jumlah_barang;
    }

    public String getKurir() {
        return kurir;
    }

    public String getNama_barang() {
        return nama_barang;
    }

    public String getNo_resi() {
        return no_resi;
    }

    public String getStatus() {
        return status;
    }

    public String getTanggal_mulai() {
        return tanggal_mulai;
    }

    public String getTanggal_selesai() {
        return tanggal_selesai;
    }

}
