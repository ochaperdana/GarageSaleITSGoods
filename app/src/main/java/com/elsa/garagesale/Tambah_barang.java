package com.elsa.garagesale;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.error.VolleyError;
import com.android.volley.request.SimpleMultiPartRequest;
import com.elsa.garagesale.app.AppController;
import com.elsa.garagesale.util.Server;
import com.vansuita.pickimage.bean.PickResult;
import com.vansuita.pickimage.bundle.PickSetup;
import com.vansuita.pickimage.dialog.PickImageDialog;
import com.vansuita.pickimage.listeners.IPickResult;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;


public class Tambah_barang extends AppCompatActivity implements IPickResult {

    EditText nama_barang;
    EditText deskripsi;
    EditText stok;
    EditText harga_barang;
    EditText tanggal_masuk;
    Spinner rating;
    ImageView imageView;
    Button add_barang;


    String nama_barang2;
    String deskripsi2;
    String stok2;
    String harga_barang2;
    String rating2;
    String tanggal_masuk2;
    String imagepath;

    int id_list_gambar = 0;
    int statuslistgambar = 0;
    int statuslistgambarid = 0;
    List<String> imagepathlist = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tambah_barang);
        nama_barang = findViewById(R.id.nama_barang);
        deskripsi = findViewById(R.id.deskripsi);
        stok = findViewById(R.id.stok);
        harga_barang = findViewById(R.id.harga_barang);
        imageView = findViewById(R.id.imageView);
        add_barang = findViewById(R.id.add_barang);

        tambahgambar();
        add_barang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nama_barang2 = nama_barang.getText().toString();
                deskripsi2 = deskripsi.getText().toString();
                harga_barang2 = harga_barang.getText().toString();
                stok2 = stok.getText().toString();
                handlekoneksibarang(Tambah_barang.this, Server.URL + "tambah_barang.php");

            }
        });

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                statuslistgambar = 0;
                PickImageDialog.build(new PickSetup().setWidth(300).setHeight(300)).show(Tambah_barang.this);
            }
        });
    }

    public String getURLForResource(int resourceId) {
        return Uri.parse("android.resource://" + R.class.getPackage().getName() + "/" + resourceId).toString();
    }

    public void tambahgambar() {
        int widthgambar = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 106, getResources().getDisplayMetrics());
        int heightgambar = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 117, getResources().getDisplayMetrics());
        ImageView list_gambar = new ImageView(this);
        list_gambar.setId(id_list_gambar);
        list_gambar.setImageResource(R.drawable.gallery);
        list_gambar.setTag(0);
        LinearLayout linearLayout = findViewById(R.id.imageviewlayout);
        LayoutParams buttonlayout = new LayoutParams(widthgambar, heightgambar);
        linearLayout.addView(list_gambar, buttonlayout);
        list_gambar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ((int) list_gambar.getTag() == 0) {
                    statuslistgambar = 1;
                } else if ((int) list_gambar.getTag() == 1) {
                    statuslistgambar = 2;
                    statuslistgambarid = list_gambar.getId();
                }
                PickImageDialog.build(new PickSetup().setWidth(300).setHeight(300)).show(Tambah_barang.this);
            }
        });
    }

    @Override
    public void onPickResult(PickResult r) {
        if (r.getError() == null) {
            if (statuslistgambar == 0) {
                imageView.setImageBitmap(r.getBitmap());
                saveBitmapToFile(new File(r.getPath()), r.getBitmap());
                imagepath = r.getPath();
            } else if (statuslistgambar == 1) {
                ImageView viewgambar = findViewById(id_list_gambar);
                viewgambar.setImageBitmap(r.getBitmap());
                saveBitmapToFile(new File(r.getPath()), r.getBitmap());
                viewgambar.setTag(1);
                imagepathlist.add(r.getPath());
                id_list_gambar++;
                tambahgambar();
            } else if (statuslistgambar == 2) {
                ImageView viewgambar = findViewById(statuslistgambarid);
                viewgambar.setImageBitmap(r.getBitmap());
                saveBitmapToFile(new File(r.getPath()), r.getBitmap());
                imagepathlist.set(statuslistgambarid, r.getPath());
            }
        } else {
            Toast.makeText(Tambah_barang.this, r.getError().toString(), Toast.LENGTH_SHORT).show();
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

    public void handlekoneksibarang(final Context kelasini, String url) {

        SimpleMultiPartRequest strReq = new SimpleMultiPartRequest(Request.Method.POST, url, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                try {

                    JSONObject jObj = new JSONObject(response);
                    int success = jObj.getInt("success");

                    if (success == 1) {
                        Toast.makeText(kelasini, "berhasil tambah barang", Toast.LENGTH_LONG).show();
                        startActivity(new Intent(Tambah_barang.this, Menu_penjual.class));
                        finish();
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
        strReq.addStringParam("type", "view");
        strReq.addStringParam("id_penjual", MainActivity.id_user);
        strReq.addStringParam("nama_barang", nama_barang2);
        strReq.addStringParam("deskripsi", deskripsi2);
        strReq.addStringParam("stok", stok2);
        strReq.addStringParam("harga", harga_barang2);
        if (imagepathlist.size() > 0) {
            strReq.addStringParam("total_gambar", Integer.toString(imagepathlist.size()));
        }
        strReq.addFile("gambar_preview", imagepath);
        for (int i = 0; i < imagepathlist.size(); i++) {
            strReq.addFile("list_gambar" + Integer.toString(i), imagepathlist.get(i));
        }
        AppController.getInstance().addToRequestQueue(strReq, "json_obj_req");
    }
}
