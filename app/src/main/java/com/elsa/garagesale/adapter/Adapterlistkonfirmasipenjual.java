package com.elsa.garagesale.adapter;

/**
 * Created by ibuk on 12/04/2017.
 */

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.elsa.garagesale.R;
import com.elsa.garagesale.data.Datalistkonfirmasipenjual;
import com.elsa.garagesale.util.Server;
import com.squareup.picasso.Picasso;

import java.util.List;

import androidx.appcompat.app.AppCompatActivity;

public class Adapterlistkonfirmasipenjual extends BaseAdapter {
    public AppCompatActivity activity;
    public Context context;
    private LayoutInflater inflater;
    private List<Datalistkonfirmasipenjual> items;

    public Adapterlistkonfirmasipenjual(AppCompatActivity activity2, List<Datalistkonfirmasipenjual> items2) {
        activity = activity2;
        items = items2;
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int location) {
        return items.get(location);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {

        if (inflater == null)
            inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (view == null)
            view = inflater.inflate(R.layout.list_konfirmasi_penjual, null);
        TextView tanggal_mulai = view.findViewById(R.id.tanggal_mulai);
        TextView tanggal_selesai = view.findViewById(R.id.tanggal_selesai);
        TextView nama_user = view.findViewById(R.id.nama_user);
        TextView kode_transaksi = view.findViewById(R.id.kode_transaksi);
        TextView judul_barang = view.findViewById(R.id.judul_barang);
        TextView harga_barang = view.findViewById(R.id.harga_barang);
        TextView jumlah = view.findViewById(R.id.jumlah);
        ImageView gambar_barang = view.findViewById(R.id.gambar_barang);
        TextView status = view.findViewById(R.id.status);
        TextView no_resi = view.findViewById(R.id.no_resi);
        Datalistkonfirmasipenjual data = items.get(position);
        if (!data.getBukti_foto().equals("")) {
            Picasso.with(activity).load(Server.URL + "bukti_transfer/" + data.getBukti_foto()).resize(300, 300).error(R.drawable.gallery).into(gambar_barang);
        }
        nama_user.setText(data.getNama_lengkap());
        judul_barang.setText(data.getNama_barang());
        harga_barang.setText("Rp " + data.getHarga_total());
        jumlah.setText("Jumlah " + data.getJumlah_barang());
        tanggal_mulai.setText(data.getTanggal_mulai());
        if (data.getTanggal_selesai().equals("0000-00-00")) {
            tanggal_selesai.setText("");
        } else {
            tanggal_selesai.setText(data.getTanggal_selesai());
        }
        kode_transaksi.setText(data.getId_pembayaran());
        if (data.getStatus().equals("1")) {
            status.setText("konfirmasi");
            no_resi.setText("");
        } else if (data.getStatus().equals("2")) {
            status.setText("sudah di konfirmasi");
            no_resi.setText(data.getNo_resi());
        } else if (data.getStatus().equals("3")) {
            status.setText("konfirmasi dibatalkan");
            no_resi.setText("");
        }


        return view;
    }

}