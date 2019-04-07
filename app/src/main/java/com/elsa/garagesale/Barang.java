package com.elsa.garagesale;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.error.VolleyError;
import com.android.volley.request.StringRequest;
import com.elsa.garagesale.adapter.Adapterulasan;
import com.elsa.garagesale.app.AppController;
import com.elsa.garagesale.data.Dataulasan;
import com.elsa.garagesale.util.Server;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import androidx.appcompat.app.AppCompatActivity;

public class Barang extends AppCompatActivity {
    public static String id_penjual;
    public static int harga_barang3;
    public static int stok_barang;
    List<Dataulasan> listulasan = new ArrayList<Dataulasan>();
    Adapterulasan adapterulasan;
    TextView nama_barang;
    TextView harga_barang;
    EditText stok;
    TextView deskripsi;
    TextView tanggal_masuk;
    TextView stok_preview;
    TextView rating_total;
    TextView nama_toko;
    Spinner ulasan_spinner;
    EditText komentar;
    ListView ulasan;
    ImageView imageview;
    ImageView imageview2;
    Button beli;
    Button kirim;
    String nama_barang2;
    int harga_barang2;
    String stok2;
    String deskripsi2;
    String tanggal_masuk2;
    String stok_preview2;
    String ulasan_spinner2;
    String komentar2;
    float rating_total2;
    int id_gambar = 100;

    public static void justifyListViewHeightBasedOnChildren(ListView listView) {

        ListAdapter adapter = listView.getAdapter();

        if (adapter == null) {
            return;
        }
        ViewGroup vg = listView;
        int totalHeight = 0;
        for (int i = 0; i < adapter.getCount(); i++) {
            View listItem = adapter.getView(i, null, vg);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }

        ViewGroup.LayoutParams par = listView.getLayoutParams();
        par.height = totalHeight + (listView.getDividerHeight() * (adapter.getCount() - 1));
        listView.setLayoutParams(par);
        listView.requestLayout();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.barang);
        nama_toko = findViewById(R.id.nama_toko);
        nama_barang = findViewById(R.id.nama_barang);
        harga_barang = findViewById(R.id.harga_barang);
        stok_preview = findViewById(R.id.stok_preview);
        tanggal_masuk = findViewById(R.id.tanggal_masuk);
        rating_total = findViewById(R.id.rating_total);
        deskripsi = findViewById(R.id.deskripsi);
        stok = findViewById(R.id.stok);
        komentar = findViewById(R.id.ulasan);
        ulasan = findViewById(R.id.listview_ulasan);
        ulasan_spinner = findViewById(R.id.ulasan_spinner);
        beli = findViewById(R.id.beli);
        kirim = findViewById(R.id.kirim);
        imageview = findViewById(R.id.imageView);
        imageview2 = findViewById(R.id.imageView2);
        harga_barang3 = MainActivity.harga_barang;
        stok_barang = Integer.parseInt(stok.getText().toString());
        handlekoneksilistulasan(Barang.this, Server.URL + "ulasan.php");
        String[] items = new String[]{"Rating 1", "Rating 2", "Rating 3", "Rating 4", "Rating 5"};
        String[] items2 = new String[]{"1", "2", "3", "4", "5"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, items);
        ulasan_spinner.setAdapter(adapter);
        handlekoneksibarang(Barang.this, Server.URL + "barang.php");
        adapterulasan = new Adapterulasan(Barang.this, listulasan);
        ulasan.setAdapter(adapterulasan);
        beli.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (MainActivity.level.equals("-1") || MainActivity.level.equals("1")) {
                    startActivity(new Intent(Barang.this, Login.class));
                } else {
                    handlekoneksitambahkeranjang(Barang.this, Server.URL + "barang.php");
                }
            }
        });
        stok.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!s.toString().equals("")) {
                    stok_barang = Integer.parseInt(s.toString());
                    int total = harga_barang2 * stok_barang;
                    harga_barang.setText(Integer.toString(total));
                    harga_barang3 = total;
                } else {
                    harga_barang.setText("0");
                    harga_barang3 = 0;
                    stok_barang = 0;
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        imageview2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BitmapDrawable bitmap = (BitmapDrawable) imageview2.getDrawable();
                imageview.setImageBitmap(bitmap.getBitmap());
            }
        });
        kirim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (MainActivity.level.equals("0")) {
                    ulasan_spinner2 = items2[ulasan_spinner.getSelectedItemPosition()];
                    komentar2 = komentar.getText().toString();
                    handlekoneksiaddulasan(Barang.this, Server.URL + "ulasan.php");
                } else {
                    startActivity(new Intent(Barang.this, Login.class));
                }
            }
        });
    }

    public void tambahgambar(String url) {
        int widthgambar = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 90, getResources().getDisplayMetrics());
        int heightgambar = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 117, getResources().getDisplayMetrics());
        ImageView list_gambar = new ImageView(this);
        list_gambar.setId(id_gambar);
        list_gambar.setImageResource(R.drawable.gallery);
        list_gambar.setTag(0);
        LinearLayout linearLayout = findViewById(R.id.list_gambar);
        LinearLayout.LayoutParams buttonlayout = new LinearLayout.LayoutParams(widthgambar, ViewGroup.LayoutParams.MATCH_PARENT);
        linearLayout.addView(list_gambar, buttonlayout);
        ImageView tampilkan_gambar = findViewById(id_gambar);
        Picasso.with(Barang.this).load(url).resize(500, 500).error(R.drawable.gallery).into(tampilkan_gambar);
        id_gambar++;
        list_gambar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BitmapDrawable bitmap = (BitmapDrawable) list_gambar.getDrawable();
                imageview.setImageBitmap(bitmap.getBitmap());
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
                        id_penjual = jObj.getString("id_penjual");
                        nama_barang.setText(jObj.getString("nama_barang"));
                        deskripsi.setText(jObj.getString("deskripsi"));
                        stok_preview.setText("stok : " + jObj.getString("stok"));
                        rating_total.setText("rating " + jObj.getString("rating_total"));
                        rating_total2 = Float.parseFloat(jObj.getString("rating_total"));
                        tanggal_masuk.setText("tanggal " + jObj.getString("tanggal_masuk"));
                        harga_barang.setText(jObj.getString("harga"));
                        harga_barang2 = Integer.parseInt(jObj.getString("harga"));
                        nama_toko.setText("nama toko : " + jObj.getString("nama_lengkap"));
                        Picasso.with(Barang.this).load(Server.URL + "gambar_barang/" + jObj.getString("gambar_preview")).resize(300, 300).error(R.drawable.gallery).into(imageview);
                        Picasso.with(Barang.this).load(Server.URL + "gambar_barang/" + jObj.getString("gambar_preview")).resize(300, 300).error(R.drawable.gallery).into(imageview2);
                        JSONArray hasil = jObj.getJSONArray("gambar");
                        for (int i = 0; i < hasil.length(); i++) {
                            JSONObject hasil2 = hasil.getJSONObject(i);
                            tambahgambar(Server.URL + "gambar_barang/" + hasil2.getString("gambar"));
                        }
                    } else {
                        Toast.makeText(kelasini, "username atau password salah", Toast.LENGTH_LONG).show();
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
                params.put("id_barang", MainActivity.id_barang);
                return params;
            }

        };
        AppController.getInstance().addToRequestQueue(strReq, "json_obj_req");
    }

    public void handlekoneksilistulasan(final Context kelasini, String url) {
        listulasan.clear();
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
                            String nama_lengkap = hasil2.getString("nama_lengkap");
                            String rating = hasil2.getString("rating");
                            String tanggal_masuk = hasil2.getString("tanggal_mulai");
                            String komentar = hasil2.getString("komentar");
                            String foto_profil = hasil2.getString("foto_profil");
                            listulasan.add(new Dataulasan(nama_lengkap, rating, tanggal_masuk, komentar, foto_profil));

                        }
                        adapterulasan.notifyDataSetChanged();
                        justifyListViewHeightBasedOnChildren(ulasan);
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
                params.put("id_barang", MainActivity.id_barang);
                return params;
            }

        };
        AppController.getInstance().addToRequestQueue(strReq, "json_obj_req");
    }

    public void handlekoneksitambahkeranjang(final Context kelasini, String url) {

        StringRequest strReq = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                try {

                    JSONObject jObj = new JSONObject(response);
                    int success = jObj.getInt("success");

                    if (success == 2) {
                        startActivity(new Intent(Barang.this, Keranjang_barang.class));
                    } else if (success == 4) {
                        Toast.makeText(kelasini, "jumlah barang melebihi stok", Toast.LENGTH_LONG).show();
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
                params.put("id_user", MainActivity.id_user);
                params.put("id_barang", MainActivity.id_barang);
                params.put("jumlah_barang", Integer.toString(stok_barang));
                params.put("harga_total", Integer.toString(harga_barang3));
                return params;
            }

        };
        AppController.getInstance().addToRequestQueue(strReq, "json_obj_req");
    }

    public void handlekoneksiaddulasan(final Context kelasini, String url) {

        StringRequest strReq = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                try {

                    JSONObject jObj = new JSONObject(response);
                    int success = jObj.getInt("success");

                    if (success == 2) {
                        handlekoneksilistulasan(Barang.this, Server.URL + "ulasan.php");
                        handlekoneksibarang(Barang.this, Server.URL + "barang.php");
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
                params.put("type", "insert");
                params.put("id_barang", MainActivity.id_barang);
                params.put("id_user", MainActivity.id_user);
                params.put("rating", ulasan_spinner2);
                params.put("komentar", komentar2);
                return params;
            }

        };
        AppController.getInstance().addToRequestQueue(strReq, "json_obj_req");
    }


}
