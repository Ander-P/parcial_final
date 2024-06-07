package com.example.parcialtercercorte;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import java.util.regex.Pattern;

public class SignInActivity extends AppCompatActivity {

    EditText edt_signin_user, edt_signin_password, edt_signin_email, edt_signin_birthday;
    Button btn_sign_in;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    public static final String SHARED_PREF_NAME= "userData";
    public static final String KEY_USER = "user";
    public static final String KEY_PASSWORD = "password";
    public static final String KEY_EMAIL = "email";
    public static final String KEY_BIRTHDAY = "birthday";
    private static final int mode_private = Context.MODE_PRIVATE;
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

        sharedPreferences = getSharedPreferences(SHARED_PREF_NAME, mode_private);
        editor = sharedPreferences.edit();


        btn_sign_in.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SuspiciousIndentation")
            @Override
            public void onClick(View view) {

                String savedUser = sharedPreferences.getString(KEY_USER,  null);
                String savedPassword = sharedPreferences.getString(KEY_PASSWORD,  null);
                String savedEmail = sharedPreferences.getString(KEY_EMAIL,  null);
                String savedBirthday = sharedPreferences.getString(KEY_BIRTHDAY,  null);

                String info_edt_user = edt_signin_user.getText().toString().trim();
                String info_edt_password = edt_signin_password.getText().toString().trim();
                String info_edt_email = edt_signin_email.getText().toString().trim();
                String info_edt_birthday = edt_signin_birthday.getText().toString().trim();

                if (!validarCampo(info_edt_user) || info_edt_user.equals("")){
                    Toast.makeText(SignInActivity.this,"user no permitido", Toast.LENGTH_SHORT).show();
                }else if (!validarCampo(info_edt_password) || info_edt_password.equals("")){
                    Toast.makeText(SignInActivity.this,"password no permitida", Toast.LENGTH_SHORT).show();
                }else if (!validarEmail(info_edt_email) || info_edt_email.equals("")){
                    Toast.makeText(SignInActivity.this,"email no permitido", Toast.LENGTH_SHORT).show();
                }else
                    editor.putString(KEY_USER, info_edt_user);
                    editor.putString(KEY_PASSWORD, info_edt_password);
                    editor.putString(KEY_EMAIL, info_edt_email);
                    editor.putString(KEY_BIRTHDAY, info_edt_birthday);
                    editor.commit();
                    Toast.makeText(SignInActivity.this, "Credenciales guardadas", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(SignInActivity.this, LoginActivity.class);
                    startActivity(intent);

            }
        });

    }
    public boolean validarCampo(String campo){
        boolean isValid = true;
        for (int i=0; i<campo.length(); i++){
            char c = campo.charAt(i);
            if (!((c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z') || (c >= '0' && c <= '9'))){
                isValid = false;
                break;
            }
        }
        return isValid;
    }

    public boolean validarEmail(String email){
        String emailPattern = "[a-zA-Z0-9._-]+@gmail\\.com";//estructura email
        return Pattern.matches(emailPattern, email);//intenta coincidir la estructura con el email ingresado
    }
}