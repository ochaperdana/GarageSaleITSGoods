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
import com.elsa.garagesale.data.Dataulasan;
import com.elsa.garagesale.util.Server;
import com.squareup.picasso.Picasso;

import java.util.List;

import androidx.appcompat.app.AppCompatActivity;

public class Adapterulasan extends BaseAdapter {
    public AppCompatActivity activity;
    public Context context;
    private LayoutInflater inflater;
    private List<Dataulasan> items;

    public Adapterulasan(AppCompatActivity activity2, List<Dataulasan> items2) {
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
            view = inflater.inflate(R.layout.list_ulasan, null);

        TextView nama_lengkap = view.findViewById(R.id.nama_lengkap);
        TextView komentar = view.findViewById(R.id.komentar);
        TextView rating_total = view.findViewById(R.id.rating_total);
        TextView tanggal_masuk = view.findViewById(R.id.tanggal_masuk);
        ImageView gambar_barang = view.findViewById(R.id.gambar_barang);
        Dataulasan data = items.get(position);
        Picasso.with(activity).load(Server.URL + "foto_profil/" + data.getFoto_profil()).resize(300, 300).error(R.drawable.gallery).into(gambar_barang);
        nama_lengkap.setText(data.getNama_lengkap());
        komentar.setText(data.getKomentar());
        rating_total.setText("rating " + data.getRating());
        tanggal_masuk.setText(data.getTanggal_masuk());

        return view;
    }

}