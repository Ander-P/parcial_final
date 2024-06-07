package com.example.parcialtercercorte;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity {

    EditText edt_login_user, edt_login_password;
    Button btn_login, btn_register;
    SharedPreferences sharedPreferences;
    public static final String SHARED_PREF_NAME = "userData";
    public static final String KEY_USER = "user"; //clave para usuario
    public static final String KEY_PASSWORD = "password"; //clave para password
    private static final int mode_private = Context.MODE_PRIVATE;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);

        edt_login_user = findViewById(R.id.edt_login_user);
        edt_login_password = findViewById(R.id.edt_login_password);
        btn_login = findViewById(R.id.btn_login);
        btn_register = findViewById(R.id.btn_register);

        sharedPreferences = getSharedPreferences(SHARED_PREF_NAME, mode_private);

        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, SignInActivity.class);
                startActivity(intent);
            }
        });

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String info_edt_user = edt_login_user.getText().toString().trim();
                String info_edt_password = edt_login_password.getText().toString().trim();

                if (info_edt_user.isEmpty() || info_edt_password.isEmpty()) {
                    Toast.makeText(LoginActivity.this, "Rellena los campos vacíos", Toast.LENGTH_SHORT).show();
                } else {
                    loginUser(info_edt_user, info_edt_password);
                }
            }
        });
    }

    private void loginUser(String email, String password) {
        String url = "https://nova-dev-420721.uc.r.appspot.com/auth/login";
        RequestQueue queue = Volley.newRequestQueue(this);

        JSONObject requestBody = new JSONObject();
        try {
            requestBody.put("email", email);
            requestBody.put("password", password);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, requestBody,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("LoginActivity", "Respuesta de la API: " + response.toString());
                        try {
                            String email = response.getString("email");
                            String userName = response.getString("userName");
                            String password = response.getString("password");
                            String birthday = response.getString("birthday");

                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putString("email", email);
                            editor.putString("userName", userName);
                            editor.putString("password", password);
                            editor.putString("birthday", birthday);
                            editor.apply();

                            Toast.makeText(LoginActivity.this, "Login exitoso", Toast.LENGTH_SHORT).show();
                            Log.d("LoginActivity", "Login exitoso");
                            Intent intent = new Intent(LoginActivity.this, inicio.class);
                            startActivity(intent);
                            finish();
                        } catch (JSONException e) {
                            Log.e("LoginActivity", "Error en la respuesta", e);
                            Toast.makeText(LoginActivity.this, "Error en la respuesta", Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("LoginActivity", "Error en el login: " + error.getMessage());
                Toast.makeText(LoginActivity.this, "Error en el login: " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        queue.add(jsonObjectRequest);
    }
}
