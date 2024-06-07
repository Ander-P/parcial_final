package com.example.parcialtercercorte;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import com.example.parcialtercercorte.fragments.HomeRecyclerViewFragment;
import com.example.parcialtercercorte.fragments.RequestFragment;
import com.example.parcialtercercorte.fragments.SettingFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class inicio extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;
    HomeRecyclerViewFragment HomeFragment = new HomeRecyclerViewFragment();
    RequestFragment RequestFragment = new RequestFragment();
    SettingFragment SettingFragment = new SettingFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio);

        bottomNavigationView = findViewById(R.id.bottomNavigationView);

        // Load the default fragment
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, HomeFragment).commit();

        bottomNavigationView.setOnItemSelectedListener(menuItem -> {
            int id = menuItem.getItemId();
            if (id == R.id.home_fragment) {
                loadFragment(HomeFragment);
                return true;
            } else if (id == R.id.setting_fragment) {
                loadFragment(SettingFragment);
                return true;
            } else if (id == R.id.request_fragment) {
                loadFragment(RequestFragment);
                return true;
            } else {
                return false;
            }
        });
    }

    private void loadFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment).commit();
    }
}
