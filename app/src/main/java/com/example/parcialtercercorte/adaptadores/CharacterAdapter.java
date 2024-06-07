package com.example.parcialtercercorte.adaptadores;

import com.example.parcialtercercorte.clases.Character;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.parcialtercercorte.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class CharacterAdapter extends RecyclerView.Adapter<CharacterAdapter.ViewHolder> {

    private List<Character> characters;
    private List<Character> characterListFull; // Lista completa para soportar el filtrado
    private final OnItemClickListener listener;

    public interface OnItemClickListener {
        void OnItemClick(Character item);
    }

    public CharacterAdapter(List<Character> characters, OnItemClickListener listener) {
        this.characters = characters;
        this.characterListFull = new ArrayList<>(characters); // Inicializa la lista completa
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_characters, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Character character = characters.get(position);
        holder.bind(character, listener);
    }

    @Override
    public int getItemCount() {
        return characters.size();
    }

    public void filter(String text) {
        characters.clear();
        if (text.isEmpty()) {
            characters.addAll(characterListFull);
        } else {
            text = text.toLowerCase();
            for (Character character : characterListFull) {
                if (character.getNombre().toLowerCase().contains(text)) {
                    characters.add(character);
                }
            }
        }
        notifyDataSetChanged();
    }



    public class ViewHolder extends RecyclerView.ViewHolder {
        private final ImageView img_character;
        private final TextView txt_name_character;
        private final TextView txt_id_character;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            img_character = itemView.findViewById(R.id.img_character);
            txt_name_character = itemView.findViewById(R.id.txt_name_character);
            txt_id_character = itemView.findViewById(R.id.txt_id_character);
        }

        public void bind(final Character dato, final OnItemClickListener listener) {
            txt_name_character.setText(dato.getNombre());
            txt_id_character.setText(dato.getId());
            Picasso.get().load(dato.getImagen()).into(img_character);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.OnItemClick(dato);
                }
            });
        }
    }
}
