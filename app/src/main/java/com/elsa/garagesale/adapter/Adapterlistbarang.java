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
import com.elsa.garagesale.data.Datalistbarang;
import com.elsa.garagesale.util.Server;
import com.squareup.picasso.Picasso;

import java.util.List;

import androidx.appcompat.app.AppCompatActivity;

public class Adapterlistbarang extends BaseAdapter {
    public AppCompatActivity activity;
    public Context context;
    private LayoutInflater inflater;
    private List<Datalistbarang> items;

    public Adapterlistbarang(AppCompatActivity activity2, List<Datalistbarang> items2) {
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
            view = inflater.inflate(R.layout.list_barang, null);

        TextView judul_barang = view.findViewById(R.id.judul_barang);
        TextView harga_barang = view.findViewById(R.id.harga_barang);
        TextView rating_total = view.findViewById(R.id.rating_total);
        TextView tanggal_masuk = view.findViewById(R.id.tanggal_masuk);
        ImageView gambar_barang = view.findViewById(R.id.gambar_barang);
        Datalistbarang data = items.get(position);
        Picasso.with(activity).load(Server.URL + "gambar_barang/" + data.getGambar_preview()).resize(300, 300).error(R.drawable.gallery).into(gambar_barang);
        judul_barang.setText(data.getNama_barang());
        harga_barang.setText("Rp " + Integer.toString(data.getHarga()));
        rating_total.setText("rating " + data.getRating_total());
        tanggal_masuk.setText(data.getTanggal_masuk());

        return view;
    }

}