package com.elsa.garagesale;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.error.VolleyError;
import com.android.volley.request.SimpleMultiPartRequest;
import com.android.volley.request.StringRequest;
import com.elsa.garagesale.app.AppController;
import com.elsa.garagesale.util.Server;
import com.squareup.picasso.Picasso;
import com.vansuita.pickimage.bean.PickResult;
import com.vansuita.pickimage.bundle.PickSetup;
import com.vansuita.pickimage.dialog.PickImageDialog;
import com.vansuita.pickimage.listeners.IPickResult;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.Map;

import androidx.appcompat.app.AppCompatActivity;

public class Konfirmasi_pembeli extends AppCompatActivity implements IPickResult {
    public static String id_penjual;
    public static int harga_barang3;
    public static int stok_barang;
    TextView nama_barang;
    TextView harga_barang;
    TextView kurir;
    TextView jumlah;
    ImageView imageview;
    TextView alamat;
    Button konfirmasi;
    String nama_barang2;
    int harga_barang2;
    String stok2;
    String deskripsi2;
    String tanggal_masuk2;
    String jumlah2;
    int rating_total2;
    int id_gambar = 100;
    String imagepath = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.konfirmasi_pembeli);
        nama_barang = findViewById(R.id.nama_barang);
        harga_barang = findViewById(R.id.harga_barang);
        jumlah = findViewById(R.id.jumlah);
        kurir = findViewById(R.id.kurir);
        konfirmasi = findViewById(R.id.konfirmasi);
        imageview = findViewById(R.id.imageView);
        handlekoneksibarang(Konfirmasi_pembeli.this, Server.URL + "pembayaran.php");
        konfirmasi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (imagepath == "") {
                    Toast.makeText(Konfirmasi_pembeli.this, "tap gambar dan upload bukti pembayaran", Toast.LENGTH_SHORT).show();
                } else {
                    handlekoneksikonfirmasi(Konfirmasi_pembeli.this, Server.URL + "pembayaran.php");
                }
            }
        });
        imageview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PickImageDialog.build(new PickSetup()).show(Konfirmasi_pembeli.this);
            }
        });
    }

    public void handlekoneksibarang(final Context kelasini, String url) {

        StringRequest strReq = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                try {

                    JSONObject jObj = new JSONObject(response);
                    int success = jObj.getInt("success");

                    if (success == 1) {
                        JSONArray hasil = jObj.getJSONArray("hasil");
                        for (int i = 0; i < hasil.length(); i++) {
                            JSONObject hasil2 = hasil.getJSONObject(i);
                            nama_barang.setText(hasil2.getString("nama_barang"));
                            harga_barang.setText("Rp " + hasil2.getString("harga_total"));
                            jumlah.setText("Jumlah " + hasil2.getString("jumlah_barang"));
                            kurir.setText(hasil2.getString("kurir"));
                            if (hasil2.getString("status").equals("1")) {
                                Picasso.with(kelasini).load(Server.URL + "bukti_transfer/" + MainActivity.foto_profil).resize(300, 300).error(R.drawable.gallery).into(imageview);
                            }
                        }
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
                params.put("id_pembayaran", Pilih_kurir.id_pembayaran);
                return params;
            }

        };
        AppController.getInstance().addToRequestQueue(strReq, "json_obj_req");
    }

    @Override
    public void onPickResult(PickResult r) {
        if (r.getError() == null) {
            imageview.setImageBitmap(r.getBitmap());
            saveBitmapToFile(new File(r.getPath()), r.getBitmap());
            imagepath = r.getPath();

        } else {
            Toast.makeText(Konfirmasi_pembeli.this, r.getError().toString(), Toast.LENGTH_SHORT).show();
            //Handle possible errors
            //TODO: do what you have to do with r.getError();
        }
    }

    public void saveBitmapToFile(File file, Bitmap bitmapku) {
        try {
            // here i override the original image file
            FileOutputStream outputStream = new FileOutputStream(file);
            bitmapku.compress(Bitmap.CompressFormat.PNG, 100, outputStream);

        } catch (Exception e) {

        }
    }

    public void handlekoneksikonfirmasi(final Context kelasini, String url) {

        SimpleMultiPartRequest strReq = new SimpleMultiPartRequest(Request.Method.POST, url, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                try {

                    JSONObject jObj = new JSONObject(response);
                    int success = jObj.getInt("success");

                    if (success == 3) {
                        startActivity(new Intent(kelasini, Menu_pembeli.class));
                        finishAffinity();
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
        });
        strReq.addStringParam("type", "update_pembeli");
        strReq.addStringParam("id_pembayaran", Pilih_kurir.id_pembayaran);
        strReq.addFile("bukti_foto", imagepath);
        AppController.getInstance().addToRequestQueue(strReq, "json_obj_req");
    }

}
