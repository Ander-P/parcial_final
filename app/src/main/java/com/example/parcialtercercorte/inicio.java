package com.example.parcialtercercorte;

import android.os.Bundle;
import android.view.MenuItem;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import com.example.parcialtercercorte.fragments.HomeRecyclerViewFragment;
import com.example.parcialtercercorte.fragments.RequestFragment;
import com.example.parcialtercercorte.fragments.SettingFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

public class inicio extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;
    HomeRecyclerViewFragment HomeFragment = new HomeRecyclerViewFragment();
    RequestFragment RequestFragment = new RequestFragment();
    SettingFragment SettingFragment = new SettingFragment();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_inicio);

        bottomNavigationView = findViewById(R.id.bottomNavigationView);

        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, HomeFragment).commit();

        bottomNavigationView.setOnItemSelectedListener(menuItem -> {

            if (menuItem.getItemId() == R.id.home_fragment) {
                loadFragment(HomeFragment);
                return true;
            }
            if (menuItem.getItemId() == R.id.setting_fragment) {
                loadFragment(SettingFragment);
                return true;
            }
            if (menuItem.getItemId() == R.id.request_fragment) {
                loadFragment(RequestFragment);
                return true;
            }

            return false;
        });
    }

    private void loadFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment).commit();
    }

}