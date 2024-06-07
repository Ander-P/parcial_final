package com.example.parcialtercercorte.fragments;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.parcialtercercorte.R;
import com.example.parcialtercercorte.adaptadores.CharacterAdapter;
import com.example.parcialtercercorte.clases.Character;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeRecyclerViewFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeRecyclerViewFragment extends Fragment {

    // Parameters
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;

    private RecyclerView rcv_characters;
    private List<Character> listaUsuario;

    public HomeRecyclerViewFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeRecyclerViewFragment.
     */
    public static HomeRecyclerViewFragment newInstance(String param1, String param2) {
        HomeRecyclerViewFragment fragment = new HomeRecyclerViewFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home_recycler_view, container, false);

        // Initialize RecyclerView
        rcv_characters = view.findViewById(R.id.rcv_characters);

        // Initialize data
        listaUsuario = new ArrayList<>();
        listaUsuario.add(new Character("https://rickandmortyapi.com/api/character/avatar/72.jpeg", "Miguel", "Movil", "1"));
        listaUsuario.add(new Character("https://rickandmortyapi.com/api/character/avatar/120.jpeg", "Camilo", "Ingles", "2"));
        listaUsuario.add(new Character("https://rickandmortyapi.com/api/character/avatar/190.jpeg", "Caleb", "IA", "3"));
        listaUsuario.add(new Character("https://rickandmortyapi.com/api/character/avatar/241.jpeg", "Anthony", "Calculo", "4"));

        // Set up the adapter
        CharacterAdapter usuarioAdaptador = new CharacterAdapter(listaUsuario, new CharacterAdapter.OnItemClickListener() {
            @Override
            public void OnItemClick(Character item) {
                // Handle item click, e.g., show a description or navigate to another fragment/activity
            }
        });

        // Set up RecyclerView
        rcv_characters.setLayoutManager(new LinearLayoutManager(getContext()));
        rcv_characters.setAdapter(usuarioAdaptador);

        return view;
    }
}
