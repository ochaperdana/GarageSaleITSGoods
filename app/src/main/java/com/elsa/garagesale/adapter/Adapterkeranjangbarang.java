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
import com.elsa.garagesale.data.Datakeranjangbarang;
import com.elsa.garagesale.util.Server;
import com.squareup.picasso.Picasso;

import java.util.List;

import androidx.appcompat.app.AppCompatActivity;

public class Adapterkeranjangbarang extends BaseAdapter {
    public AppCompatActivity activity;
    public Context context;
    private LayoutInflater inflater;
    private List<Datakeranjangbarang> items;

    public Adapterkeranjangbarang(AppCompatActivity activity2, List<Datakeranjangbarang> items2) {
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
            view = inflater.inflate(R.layout.list_keranjang_barang, null);

        TextView judul_barang = view.findViewById(R.id.judul_barang);
        TextView harga_barang = view.findViewById(R.id.harga_barang);
        TextView jumlah = view.findViewById(R.id.jumlah);
        ImageView gambar_barang = view.findViewById(R.id.gambar_barang);
        Datakeranjangbarang data = items.get(position);
        Picasso.with(activity).load(Server.URL + "gambar_barang/" + data.getGambar_preview()).resize(300, 300).error(R.drawable.gallery).into(gambar_barang);
        judul_barang.setText(data.getNama_barang());
        harga_barang.setText("Rp " + data.getHarga_total());
        jumlah.setText("Jumlah " + data.getJumlah_barang());

        return view;
    }

}