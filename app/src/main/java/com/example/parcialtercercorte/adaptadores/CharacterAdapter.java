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

import java.util.List;

public class CharacterAdapter extends RecyclerView.Adapter<CharacterAdapter.ViewHolder>{

    private List<Character> characters;
    final CharacterAdapter.OnItemClickListener listener;

    TextView txt_name_character, txt_id_character;
    ImageView img_character;

    public interface OnItemClickListener{
        void OnItemClick(Character item);
    }
    public CharacterAdapter(List<Character> characters, CharacterAdapter.OnItemClickListener listener) {
        this.characters = characters;
        this.listener = listener;
    }

    @NonNull
    @Override
    public CharacterAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_characters, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CharacterAdapter.ViewHolder holder, int position) {
        Character character = characters.get(position);
        holder.bind(character);
    }

    @Override
    public int getItemCount() {
        return characters.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txt_name_character = itemView.findViewById(R.id.txt_name_character);
            txt_id_character = itemView.findViewById(R.id.txt_id_character);
            img_character = itemView.findViewById(R.id.img_character);
        }

        public void bind(Character dato){
            txt_name_character.setText(dato.getNombre());
            txt_id_character.setText(dato.getId());
            //Imagen libreria
            Picasso.get().load(dato.getImagen()).into(img_character);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    itemView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            listener.OnItemClick(dato);
                        }
                    });
                }
            });

        }

    }

}
