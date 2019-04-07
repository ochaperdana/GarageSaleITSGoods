package com.elsa.garagesale;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

public class Login extends AppCompatActivity {
    EditText username;
    EditText password;
    Button login;
    Button daftar;
    String username2;
    String password2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        login = findViewById(R.id.login);
        daftar = findViewById(R.id.daftar);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                username2 = username.getText().toString();
                password2 = password.getText().toString();
                handlekoneksilogin(Login.this, Server.URL + "login.php");
            }
        });

        daftar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Login.this, Daftar.class));
            }
        });
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
                        String alamat = jObj.getString("alamat");
                        SharedPreferences.Editor editor = getSharedPreferences("user_login", MODE_PRIVATE).edit();
                        editor.putString("id_user", id_user);
                        editor.putString("nama_lengkap", nama_lengkap);
                        editor.putString("username", username);
                        editor.putString("password", password);
                        editor.putString("level", level);
                        editor.putString("foto_profil", foto_profil);
                        editor.putString("alamat", alamat);
                        editor.apply();
                        MainActivity.id_user = id_user;
                        MainActivity.nama_lengkap = nama_lengkap;
                        MainActivity.username = username;
                        MainActivity.password = password;
                        MainActivity.level = level;
                        MainActivity.foto_profil = foto_profil;
                        MainActivity.alamat = alamat;
                        if (level.equals("0")) {
                            startActivity(new Intent(Login.this, Menu_pembeli.class));
                            finishAffinity();
                        } else if (level.equals("1")) {
                            startActivity(new Intent(Login.this, Menu_penjual.class));
                            finishAffinity();
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
                params.put("username", username2);
                params.put("password", password2);
                return params;
            }

        };
        AppController.getInstance().addToRequestQueue(strReq, "json_obj_req");
    }

}
