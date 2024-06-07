package com.example.parcialtercercorte.fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.parcialtercercorte.LoginActivity;
import com.example.parcialtercercorte.R;
public class SettingFragment extends Fragment {
    public static final String SHARED_PREF_NAME= "userData";
    public static final String KEY_USERNAME = "userName";
    public static final String KEY_PASSWORD = "password";
    public static final String KEY_EMAIL = "email";
    public static final String KEY_BIRTHDAY = "birthday";
    private static final int mode_private = Context.MODE_PRIVATE;
    TextView txt_user_data, txt_password_data, txt_email_data, txt_birthday_data;
    ImageButton buttonIcon;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_setting, container, false);

        txt_user_data = rootView.findViewById(R.id.txt_user_data);
        txt_password_data = rootView.findViewById(R.id.txt_password_data);
        txt_email_data = rootView.findViewById(R.id.txt_email_data);
        txt_birthday_data = rootView.findViewById(R.id.txt_birthday_data);
        buttonIcon = rootView.findViewById(R.id.btn_sign_out);;

        //recuperamos los datos guardados en sharedPreference
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(SHARED_PREF_NAME, mode_private);
        String savedUserName = sharedPreferences.getString(KEY_USERNAME,  null);
        String savedPassword = sharedPreferences.getString(KEY_PASSWORD,  null);
        String savedEmail = sharedPreferences.getString(KEY_EMAIL,  null);
        String savedBirthday = sharedPreferences.getString(KEY_BIRTHDAY,  null);

        //mostramos los datos en cada textView
        txt_user_data.setText(savedUserName);
        txt_password_data.setText(savedPassword);
        txt_email_data.setText(savedEmail);
        txt_birthday_data.setText(savedBirthday);

        buttonIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences sharedPreferences =  getActivity().getSharedPreferences(SHARED_PREF_NAME, mode_private);
                //Borramos los datos guardados en sharedPreference
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.clear();
                editor.apply();
                //sharedPreferences.edit().clear().commit();

                //enviamos inmediatamente al login
                Intent intent = new Intent(getActivity(), LoginActivity.class);
                startActivity(intent);
                getActivity().finish();
            }
        });

        // Inflate the layout for this fragment
        return rootView;
    }
}