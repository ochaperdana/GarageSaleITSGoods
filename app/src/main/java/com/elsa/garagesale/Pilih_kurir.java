package com.elsa.garagesale;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.error.VolleyError;
import com.android.volley.request.StringRequest;
import com.elsa.garagesale.app.AppController;
import com.elsa.garagesale.util.Server;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import androidx.appcompat.app.AppCompatActivity;

public class Pilih_kurir extends AppCompatActivity {
    public static String id_penjual;
    public static int harga_barang3;
    public static int stok_barang;
    public static String id_pembayaran;
    TextView nama_barang;
    TextView harga_barang;
    TextView jumlah;
    ImageView imageview;
    Spinner kurir;
    TextView alamat;
    Button beli;
    String nama_barang2;
    int harga_barang2;
    String stok2;
    String deskripsi2;
    String tanggal_masuk2;
    String jumlah2;
    int rating_total2;
    int id_gambar = 100;
    String kurir2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pilih_kurir);
        nama_barang = findViewById(R.id.nama_barang);
        harga_barang = findViewById(R.id.harga_barang);
        jumlah = findViewById(R.id.jumlah);
        kurir = findViewById(R.id.kurir);
        alamat = findViewById(R.id.alamat);
        beli = findViewById(R.id.beli);
        imageview = findViewById(R.id.imageView);
        nama_barang.setText(Keranjang_barang.nama_barang);
        harga_barang.setText("Rp . " + Keranjang_barang.harga_barang);
        jumlah.setText("Jumlah : " + Keranjang_barang.jumlah);
        alamat.setText(MainActivity.alamat);
        harga_barang3 = MainActivity.harga_barang;

        String[] items = new String[]{"Jne", "Tiki"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, items);
        kurir.setAdapter(adapter);
        Picasso.with(Pilih_kurir.this).load(Server.URL + "gambar_barang/" + Keranjang_barang.gambar).resize(300, 300).error(R.drawable.gallery).into(imageview);
        beli.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handlekoneksikonfirmasi(Pilih_kurir.this, Server.URL + "pembayaran.php");
            }
        });
        kurir.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    kurir2 = "Jne";
                } else if (position == 1) {
                    kurir2 = "Tiki";
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    public void handlekoneksikonfirmasi(final Context kelasini, String url) {

        StringRequest strReq = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {

                try {

                    JSONObject jObj = new JSONObject(response);
                    int success = jObj.getInt("success");

                    if (success == 2) {
                        id_pembayaran = jObj.getString("id_pembayaran");
                        handlekoneksihapuskeranjangbarang(Pilih_kurir.this, Server.URL + "list_keranjang_barang.php");
                    } else {
                        Toast.makeText(kelasini, "gagal", Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    // JSON error
                    Toast.makeText(kelasini, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(kelasini, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("type", "insert");
                params.put("id_penjual", Keranjang_barang.id_penjual);
                params.put("id_user", MainActivity.id_user);
                params.put("id_barang", Keranjang_barang.id_barang);
                params.put("kurir", kurir2);
                params.put("jumlah_barang", Keranjang_barang.jumlah);
                params.put("harga_total", Keranjang_barang.harga_barang);
                return params;
            }

        };
        AppController.getInstance().addToRequestQueue(strReq, "json_obj_req");
    }

    public void handlekoneksihapuskeranjangbarang(final Context kelasini, String url) {
        StringRequest strReq = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                try {
                    Toast.makeText(kelasini, response, Toast.LENGTH_SHORT).show();
                    JSONObject jObj = new JSONObject(response);

                    int success = jObj.getInt("success");
                    if (success == 2) {
                        startActivity(new Intent(Pilih_kurir.this, Konfirmasi_pembeli.class));
                        finish();
                    } else {
                        Toast.makeText(kelasini, "gagal", Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    // JSON error
                    //Toast.makeText(kelasini, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(kelasini, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("type", "hapus_konfirmasi");
                params.put("id_keranjang", Keranjang_barang.id_keranjang);
                return params;
            }

        };
        AppController.getInstance().addToRequestQueue(strReq, "json_obj_req");
    }


}
