package com.elsa.garagesale;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.error.VolleyError;
import com.android.volley.request.StringRequest;
import com.elsa.garagesale.app.AppController;
import com.elsa.garagesale.util.Server;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    public static String id_user = "";
    public static String username = "";
    public static String password = "";
    public static String nama_lengkap = "";
    //-1 = belum login
    //0 = pembeli
    //1 = penjual
    public static String level = "-1";
    public static String id_barang = "";
    public static String foto_profil = "";
    public static String alamat = "";
    public static int harga_barang;
    Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SharedPreferences prefs = getSharedPreferences("user_login", MODE_PRIVATE);
        id_user = prefs.getString("id_user", "");
        username = prefs.getString("username", "");
        password = prefs.getString("password", "");
        nama_lengkap = prefs.getString("nama_lengkap", "");
        level = prefs.getString("level", "-1");
        foto_profil = prefs.getString("foto_profil", "");
        alamat = prefs.getString("alamat", "");
        jalan();
    }

    public void jalan() {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                handlekoneksilogin(MainActivity.this, Server.URL + "login.php");
            }
        }, 500);
    }


    public void handlekoneksilogin(final Context kelasini, String url) {

        StringRequest strReq = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                try {

                    JSONObject jObj = new JSONObject(response);
                    int success = jObj.getInt("success");

                    if (success == 1) {
                        String id_user = jObj.getString("id_user");
                        String nama_lengkap = jObj.getString("nama_lengkap");
                        String username = jObj.getString("username");
                        String password = jObj.getString("password");
                        String level = jObj.getString("level");
                        String foto_profil = jObj.getString("foto_profil");

                        if (level.equals("0")) {
                            startActivity(new Intent(MainActivity.this, Menu_pembeli.class));
                            finish();
                        } else if (level.equals("1")) {
                            startActivity(new Intent(MainActivity.this, Menu_penjual.class));
                            finish();
                        }

                    } else {
                        SharedPreferences.Editor editor = getSharedPreferences("user_login", MODE_PRIVATE).edit();
                        editor.putString("id_user", "");
                        editor.putString("nama_lengkap", "");
                        editor.putString("username", "");
                        editor.putString("password", "");
                        editor.putString("level", "-1");
                        editor.putString("foto_profil", "");
                        editor.putString("alamat", "");
                        editor.apply();
                        startActivity(new Intent(MainActivity.this, Menu_belum_login.class));
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
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("type", "view");
                params.put("username", username);
                params.put("password", password);
                return params;
            }

        };
        AppController.getInstance().addToRequestQueue(strReq, "json_obj_req");
    }

}