package com.elsa.garagesale;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
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
import com.elsa.garagesale.adapter.Adapterkeranjangbarang;
import com.elsa.garagesale.app.AppController;
import com.elsa.garagesale.data.Datakeranjangbarang;
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

public class Keranjang_barang extends AppCompatActivity {

    public static String nama_barang;
    public static String id_penjual;
    public static String id_barang;
    public static String id_keranjang;
    public static String harga_barang;
    public static String jumlah;
    public static String gambar;
    ListView listview;
    SwipeRefreshLayout swipeRefreshLayout;
    List<Datakeranjangbarang> listkeranjangbarang = new ArrayList<Datakeranjangbarang>();
    Adapterkeranjangbarang adapterkeranjangbarang;
    Button tambah_barang;

    TextView status;
    TextView list_barang;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.keranjang_barang);
        listview = findViewById(R.id.listview);

        swipeRefreshLayout = findViewById(R.id.swiperefresh);
        handlekoneksikeranjangbarang(Keranjang_barang.this, Server.URL + "list_keranjang_barang.php");
        adapterkeranjangbarang = new Adapterkeranjangbarang(Keranjang_barang.this, listkeranjangbarang);
        AlertDialog.Builder alert = new AlertDialog.Builder(Keranjang_barang.this);
        listview.setAdapter(adapterkeranjangbarang);
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                nama_barang = listkeranjangbarang.get(position).getNama_barang();
                id_barang = listkeranjangbarang.get(position).getId_barang();
                id_penjual = listkeranjangbarang.get(position).getId_penjual();
                id_keranjang = listkeranjangbarang.get(position).getId_keranjang();
                harga_barang = listkeranjangbarang.get(position).getHarga_total();
                jumlah = listkeranjangbarang.get(position).getJumlah_barang();
                gambar = listkeranjangbarang.get(position).getGambar_preview();
                startActivity(new Intent(Keranjang_barang.this, Pilih_kurir.class));
            }
        });

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
                        handlekoneksihapuskeranjangbarang(Keranjang_barang.this, Server.URL + "list_keranjang_barang.php");
                    }
                });
                alert.setCancelable(true);
                alert.create().show();
                return true;
            }
        });

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                handlekoneksikeranjangbarang(Keranjang_barang.this, Server.URL + "list_keranjang_barang.php");
            }
        });

    }

    public void handlekoneksikeranjangbarang(final Context kelasini, String url) {
        listkeranjangbarang.clear();
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
                            String id_keranjang = hasil2.getString("id_keranjang");
                            String id_user = hasil2.getString("id_user");
                            String id_barang = hasil2.getString("id_barang");
                            String jumlah_barang = hasil2.getString("jumlah_barang");
                            String harga_total = hasil2.getString("harga_total");
                            String nama_barang = hasil2.getString("nama_barang");
                            String gambar_preview = hasil2.getString("gambar_preview");
                            String id_penjual = hasil2.getString("id_penjual");
                            listkeranjangbarang.add(new Datakeranjangbarang(id_keranjang, id_user, id_barang, jumlah_barang, harga_total, nama_barang, gambar_preview, id_penjual));

                        }
                        swipeRefreshLayout.setRefreshing(false);
                        adapterkeranjangbarang.notifyDataSetChanged();
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
                params.put("type", "view");
                params.put("id_user", MainActivity.id_user);
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

                    JSONObject jObj = new JSONObject(response);

                    int success = jObj.getInt("success");
                    if (success == 2) {
                        Toast.makeText(kelasini, "berhasil hapus barang", Toast.LENGTH_LONG).show();
                        handlekoneksikeranjangbarang(Keranjang_barang.this, Server.URL + "list_keranjang_barang.php");
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


}