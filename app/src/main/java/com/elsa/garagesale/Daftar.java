package com.elsa.garagesale;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
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

import androidx.appcompat.app.AppCompatActivity;


public class Daftar extends AppCompatActivity implements IPickResult {

    EditText nama_lengkap;
    EditText username;
    EditText password;
    EditText konf_password;
    EditText alamat;
    EditText nomor_telpon;
    EditText provinsi;
    EditText kota;
    EditText kode_pos;
    ImageView imageView;
    Spinner level;
    Button daftar;

    EditText nomor_rekening;

    String nama_lengkap2;
    String username2;
    String password2;
    String konf_password2;
    String alamat2;
    String nomor_telpon2;
    String provinsi2;
    String kota2;
    String kode_pos2;
    String level2;
    String imagepath;
    String nomor_rekening2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.daftar);
        imageView = findViewById(R.id.imageView);
        nama_lengkap = findViewById(R.id.nama_lengkap);
        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        konf_password = findViewById(R.id.konf_password);
        alamat = findViewById(R.id.alamat);
        nomor_telpon = findViewById(R.id.nomor_telpon);
        provinsi = findViewById(R.id.provinsi);
        kota = findViewById(R.id.kota);
        kode_pos = findViewById(R.id.kodepos);
        daftar = findViewById(R.id.daftar);

        level = findViewById(R.id.level);
        nomor_rekening = findViewById(R.id.nomor_rekening);


        String[] items = new String[]{"Pembeli", "Penjual"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, items);
        level.setAdapter(adapter);

        daftar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (password.getText().toString().equals(konf_password.getText().toString())) {
                    nama_lengkap2 = nama_lengkap.getText().toString();
                    username2 = username.getText().toString();
                    password2 = password.getText().toString();
                    alamat2 = alamat.getText().toString();
                    nomor_telpon2 = nomor_telpon.getText().toString();
                    provinsi2 = provinsi.getText().toString();
                    kota2 = kota.getText().toString();
                    kode_pos2 = kode_pos.getText().toString();
                    nomor_rekening2 = nomor_rekening.getText().toString();
                    if (level.getSelectedItem().toString().equals("Pembeli")) {
                        level2 = "0";
                    } else if (level.getSelectedItem().toString().equals("Penjual")) {
                        level2 = "1";
                    }
                    handlekoneksidaftar(Daftar.this, Server.URL + "daftar.php");
                } else {
                    Toast.makeText(Daftar.this, "konfirmasi password tidak sama", Toast.LENGTH_SHORT).show();
                }
            }
        });

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PickImageDialog.build(new PickSetup()).show(Daftar.this);
            }
        });

    }

    @Override
    public void onPickResult(PickResult r) {
        if (r.getError() == null) {
            imageView.setImageBitmap(r.getBitmap());
            saveBitmapToFile(new File(r.getPath()), r.getBitmap());
            imagepath = r.getPath();

        } else {
            Toast.makeText(Daftar.this, r.getError().toString(), Toast.LENGTH_SHORT).show();
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

    public void handlekoneksidaftar(final Context kelasini, String url) {

        SimpleMultiPartRequest strReq = new SimpleMultiPartRequest(Request.Method.POST, url, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                try {

                    JSONObject jObj = new JSONObject(response);
                    int success = jObj.getInt("success");

                    if (success == 1) {

                        String id_user = jObj.getString("id_user");
                        String foto_profil = jObj.getString("foto_profil");
                        SharedPreferences.Editor editor = getSharedPreferences("user_login", MODE_PRIVATE).edit();
                        editor.putString("id_user", id_user);
                        editor.putString("nama_lengkap", nama_lengkap2);
                        editor.putString("username", username2);
                        editor.putString("password", password2);
                        editor.putString("level", level2);
                        editor.putString("foto_profil", foto_profil);
                        editor.putString("alamat", "Provinsi : " + provinsi2 + "\nKota : " + kota2 + "\nAlamat : " + alamat2 + "\nKode pos : " + kode_pos2);
                        editor.apply();
                        MainActivity.id_user = id_user;
                        MainActivity.nama_lengkap = nama_lengkap2;
                        MainActivity.username = username2;
                        MainActivity.password = password2;
                        MainActivity.level = level2;
                        MainActivity.foto_profil = foto_profil;
                        MainActivity.alamat = "Provinsi : " + provinsi2 + "\nKota : " + kota2 + "\nAlamat : " + alamat2 + "\nKode pos : " + kode_pos2;
                        if (level2.equals("0")) {
                            startActivity(new Intent(Daftar.this, Menu_pembeli.class));
                            finishAffinity();
                        } else if (level2.equals("1")) {
                            startActivity(new Intent(Daftar.this, Menu_penjual.class));
                            finishAffinity();
                        }
                    } else {
                        Toast.makeText(kelasini, "username sudah terdaftar", Toast.LENGTH_LONG).show();
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
        strReq.addStringParam("username", username2);
        strReq.addStringParam("password", password2);
        strReq.addStringParam("nama_lengkap", nama_lengkap2);
        strReq.addStringParam("alamat", alamat2);
        strReq.addStringParam("nomor_telpon", nomor_telpon2);
        strReq.addStringParam("provinsi", provinsi2);
        strReq.addStringParam("kota", kota2);
        strReq.addStringParam("kode_pos", kode_pos2);
        strReq.addStringParam("nomor_rekening", nomor_rekening2);
        strReq.addStringParam("level", level2);
        strReq.addFile("gambar", imagepath);
        AppController.getInstance().addToRequestQueue(strReq, "json_obj_req");
    }

}
