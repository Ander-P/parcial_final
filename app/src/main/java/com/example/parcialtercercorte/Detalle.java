package com.example.parcialtercercorte;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import com.example.parcialtercercorte.clases.Character;
import com.squareup.picasso.Picasso;

public class Detalle extends AppCompatActivity {

    TextView txt_name_character, txt_id_character, txt_description_character;
    ImageView img_character;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_detalle);

        Character dato = (Character) getIntent().getSerializableExtra("Usuario");

        txt_name_character = findViewById(R.id.txt_name_character);
        txt_id_character = findViewById(R.id.txt_id_character);
        txt_description_character = findViewById(R.id.txt_description_character);
        img_character = findViewById(R.id.img_character);

        txt_name_character.setText(dato.getNombre());
        txt_id_character.setText(dato.getId());
        txt_description_character.setText(dato.getDescripcion());
        Picasso.get().load(dato.getImagen()).into(img_character);

    }
}