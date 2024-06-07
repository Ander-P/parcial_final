package com.example.parcialtercercorte;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
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

import java.util.regex.Pattern;

public class SignInActivity extends AppCompatActivity {

    EditText edt_signin_user, edt_signin_password, edt_signin_email, edt_signin_birthday;
    Button btn_sign_in;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_sign_in);

        edt_signin_user = findViewById(R.id.edt_signin_user);
        edt_signin_password = findViewById(R.id.edt_signin_password);
        edt_signin_email = findViewById(R.id.edt_signin_email);
        edt_signin_birthday = findViewById(R.id.edt_signin_birthday);
        btn_sign_in = findViewById(R.id.btn_sign_in);

        btn_sign_in.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SuspiciousIndentation")
            @Override
            public void onClick(View view) {
                String info_edt_user = edt_signin_user.getText().toString().trim();
                String info_edt_password = edt_signin_password.getText().toString().trim();
                String info_edt_email = edt_signin_email.getText().toString().trim();
                String info_edt_birthday = edt_signin_birthday.getText().toString().trim();

                if (!validarCampo(info_edt_user) || info_edt_user.equals("")) {
                    Toast.makeText(SignInActivity.this, "userName no permitido", Toast.LENGTH_SHORT).show();
                } else if (!validarCampo(info_edt_password) || info_edt_password.equals("")) {
                    Toast.makeText(SignInActivity.this, "password no permitida", Toast.LENGTH_SHORT).show();
                } else if (info_edt_email.equals("")) {
                    Toast.makeText(SignInActivity.this, "email no permitido", Toast.LENGTH_SHORT).show();
                } else {
                    // Llamar al método registerUser para registrar al usuario
                    registerUser(info_edt_email, info_edt_password, info_edt_user, info_edt_birthday);
                }
            }
        });
    }

    public boolean validarCampo(String campo) {
        boolean isValid = true;
        for (int i = 0; i < campo.length(); i++) {
            char c = campo.charAt(i);
            if (!((c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z') || (c >= '0' && c <= '9'))) {
                isValid = false;
                break;
            }
        }
        return isValid;
    }

    public boolean validarEmail(String email) {
        String emailPattern = "[a-zA-Z0-9._-]"; // Estructura de email
        return Pattern.matches(emailPattern, email); // Intenta coincidir la estructura con el email ingresado
    }

    private void registerUser(String email, String password, String userName, String birthday) {
        String url = "https://nova-dev-420721.uc.r.appspot.com/auth/";
        RequestQueue queue = Volley.newRequestQueue(this);

        JSONObject requestBody = new JSONObject();
        try {
            requestBody.put("email", email);
            requestBody.put("password", password);
            requestBody.put("userName", userName);
            requestBody.put("birthday", birthday);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, requestBody,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("SignInActivity", "Respuesta de la API: " + response.toString());
                        try {
                            String msg =  response.getString("msg");
                            Toast.makeText(SignInActivity.this, msg, Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(SignInActivity.this, LoginActivity.class);
                            startActivity(intent);
                            finish();
                        } catch (JSONException e) {
                            Log.e("SignIn Error", "Error en la respuesta", e);
                            Toast.makeText(SignInActivity.this, "Error en la respuesta", Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("SignInActivity", "Error en el registro: " + error);
                Toast.makeText(SignInActivity.this, "Error en el registro: " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        queue.add(jsonObjectRequest);
    }
}
