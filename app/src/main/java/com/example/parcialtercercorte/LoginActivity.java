package com.example.parcialtercercorte;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

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

        edt_login_user =findViewById(R.id.edt_login_user);
        edt_login_password =findViewById(R.id.edt_login_password);
        btn_login =findViewById(R.id.btn_login);
        btn_register =findViewById(R.id.btn_register);

        sharedPreferences = getSharedPreferences( SHARED_PREF_NAME, mode_private);

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

                //obtenemos los datos ingresados / convertimos a string
                //eliminamos espacios al comienzo y final
                String info_edt_user = edt_login_user.getText().toString().trim();
                String info_edt_password = edt_login_password.getText().toString().trim();

                //validamos campos vacios
                if (info_edt_user.isEmpty() || info_edt_password.isEmpty()){
                    Toast.makeText(LoginActivity.this, "Rellena los cmapos vacios", Toast.LENGTH_SHORT).show();
                }else{
                    String savedUser = sharedPreferences.getString(KEY_USER, info_edt_user);
                    String savedPassword = sharedPreferences.getString(KEY_PASSWORD, info_edt_password);

                    //verificamos si hay datos en el sharedPreference
                    //y si son iguales a los datos ingresados
                    if (savedUser != null && savedPassword != null && info_edt_user.equals(savedUser) && info_edt_password.equals(savedPassword)){

                        Toast.makeText(LoginActivity.this, "estas nitido", Toast.LENGTH_SHORT).show();
                        /*Intent intent = new Intent(LoginActivity.this, );
                        startActivity(intent);*/
                    }else {
                        //Usuario/contrasena incorrecta
                        Toast.makeText(LoginActivity.this, "usuario o contraseña invalida", Toast.LENGTH_SHORT).show();
                    }

                }
            }
        });
    }
}