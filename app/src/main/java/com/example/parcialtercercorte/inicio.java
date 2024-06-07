package com.example.parcialtercercorte;

import android.content.Intent;
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
import com.example.parcialtercercorte.clases.Character;
import com.example.parcialtercercorte.adaptadores.CharacterAdapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class inicio extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;
    HomeRecyclerViewFragment HomeFragment = new HomeRecyclerViewFragment();
    RequestFragment RequestFragment = new RequestFragment();
    SettingFragment SettingFragment = new SettingFragment();
    RecyclerView rcv_characters;
    List<Character> listaUsuario = new ArrayList<>();

    private void description(Character item) {
        Intent intent = new Intent(this, LoginActivity.class);
        intent.putExtra("Usuario", item);
        startActivity(intent);
    }
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


        Character usuario1 = new Character("https://rickandmortyapi.com/api/character/avatar/72.jpeg",
                "Miguel","Movil", "1");
        Character usuario2 = new Character("https://rickandmortyapi.com/api/character/avatar/120.jpeg",
                "Camilo","Ingles", "2");
        Character usuario3 = new Character("https://rickandmortyapi.com/api/character/avatar/190.jpeg",
                "Caleb","IA", "3");
        Character usuario4 = new Character("https://rickandmortyapi.com/api/character/avatar/241.jpeg",
                "Anthony","Calculo", "4");

        listaUsuario.add(usuario1);
        listaUsuario.add(usuario2);
        listaUsuario.add(usuario3);
        listaUsuario.add(usuario4);
        listaUsuario.add(usuario1);
        listaUsuario.add(usuario2);
        listaUsuario.add(usuario3);
        listaUsuario.add(usuario4);

        CharacterAdapter usuarioAdaptador = new CharacterAdapter(listaUsuario,new CharacterAdapter.OnItemClickListener() {
            @Override
            public void OnItemClick(Character item) {
                description(item);
            }
        });

        rcv_characters.setLayoutManager(new LinearLayoutManager(this));
        rcv_characters.setAdapter(usuarioAdaptador);

    }

    private void loadFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment).commit();
    }

}