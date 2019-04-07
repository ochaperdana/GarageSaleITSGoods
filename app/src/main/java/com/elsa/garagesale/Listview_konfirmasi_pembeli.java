package com.elsa.garagesale;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.error.VolleyError;
import com.android.volley.request.StringRequest;
import com.elsa.garagesale.adapter.Adapterlistkonfirmasipembeli;
import com.elsa.garagesale.app.AppController;
import com.elsa.garagesale.data.Datalistkonfirmasipembeli;
import com.elsa.garagesale.util.Server;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

public class Listview_konfirmasi_pembeli extends AppCompatActivity {

    public static String nama_barang;
    public static String id_penjual;
    public static String id_barang;
    public static String id_keranjang;
    public static String harga_barang;
    public static String jumlah;
    public static String gambar;
    ListView listview;
    SwipeRefreshLayout swipeRefreshLayout;
    List<Datalistkonfirmasipembeli> listkonfirmasipembeli = new ArrayList<Datalistkonfirmasipembeli>();
    Adapterlistkonfirmasipembeli adapterlistkonfirmasipembeli;
    Button tambah_barang;

    TextView status;
    TextView list_barang;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.listview_konfirmasi_pembeli);
        listview = findViewById(R.id.listview);

        swipeRefreshLayout = findViewById(R.id.swiperefresh);
        handlekoneksikonfirmasiuser(Listview_konfirmasi_pembeli.this, Server.URL + "list_konfirmasi_barang.php");
        adapterlistkonfirmasipembeli = new Adapterlistkonfirmasipembeli(Listview_konfirmasi_pembeli.this, listkonfirmasipembeli);
        //AlertDialog.Builder alert  = new AlertDialog.Builder(Listview_konfirmasi_pembeli.this);
        listview.setAdapter(adapterlistkonfirmasipembeli);
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (listkonfirmasipembeli.get(position).getStatus().equals("0")) {
                    Pilih_kurir.id_pembayaran = listkonfirmasipembeli.get(position).getId_pembayaran();
                    startActivity(new Intent(Listview_konfirmasi_pembeli.this, Konfirmasi_pembeli.class));
                }
            }
        });
/*
        listview.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                alert.setMessage("Hapus?");
                alert.setTitle("Garage Sale");
                alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        id_keranjang = listkeranjangbarang.get(position).getId_keranjang();
                        handlekoneksihapuskeranjangbarang(Listview_konfirmasi_pembeli.this, Server.URL + "list_keranjang_barang.php");
                    }
                });
                alert.setCancelable(true);
                alert.create().show();
                return true;
            }
        });
*/
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                handlekoneksikonfirmasiuser(Listview_konfirmasi_pembeli.this, Server.URL + "list_konfirmasi_barang.php");
            }
        });

    }

    public void handlekoneksikonfirmasiuser(final Context kelasini, String url) {
        listkonfirmasipembeli.clear();
        StringRequest strReq = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                try {

                    JSONObject jObj = new JSONObject(response);

                    int success = jObj.getInt("success");
                    JSONArray hasil = jObj.getJSONArray("hasil");
                    if (success == 1) {
                        for (int i = 0; i < hasil.length(); i++) {

                            JSONObject hasil2 = hasil.getJSONObject(i);
                            String nama_barang = hasil2.getString("nama_barang");
                            String id_pembayaran = hasil2.getString("id_pembayaran");
                            String id_penjual = hasil2.getString("id_penjual");
                            String id_user = hasil2.getString("id_user");
                            String id_barang = hasil2.getString("id_barang");
                            String kurir = hasil2.getString("kurir");
                            String jumlah_barang = hasil2.getString("jumlah_barang");
                            String harga_total = hasil2.getString("harga_total");
                            String bukti_foto = hasil2.getString("bukti_foto");
                            String no_resi = hasil2.getString("no_resi");
                            String tanggal_mulai = hasil2.getString("tanggal_mulai");
                            String tanggal_selesai = hasil2.getString("tanggal_selesai");
                            String status = hasil2.getString("status");
                            listkonfirmasipembeli.add(new Datalistkonfirmasipembeli(nama_barang, id_pembayaran, id_penjual, id_user, id_barang, kurir, jumlah_barang, harga_total, bukti_foto, no_resi, tanggal_mulai, tanggal_selesai, status));

                        }
                        swipeRefreshLayout.setRefreshing(false);
                        adapterlistkonfirmasipembeli.notifyDataSetChanged();
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
                params.put("type", "user_view");
                params.put("id_user", MainActivity.id_user);
                return params;
            }

        };
        AppController.getInstance().addToRequestQueue(strReq, "json_obj_req");
    }

    /*
    public void handlekoneksihapuskeranjangbarang(final Context kelasini, String url) {
        StringRequest strReq = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                try {
                    Toast.makeText(kelasini, response, Toast.LENGTH_SHORT).show();
                    JSONObject jObj = new JSONObject(response);

                    int success = jObj.getInt("success");
                    if (success == 2) {
                        Toast.makeText(kelasini, "berhasil hapus barang", Toast.LENGTH_LONG).show();
                        handlekoneksikeranjangbarang(Listview_konfirmasi_pembeli.this, Server.URL + "list_keranjang_barang.php");
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
                params.put("type", "hapus");
                params.put("id_keranjang", id_keranjang);
                return params;
            }

        };
        AppController.getInstance().addToRequestQueue(strReq, "json_obj_req");
    }
*/

}