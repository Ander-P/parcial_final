package com.example.parcialtercercorte.fragments;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.parcialtercercorte.R;
import com.example.parcialtercercorte.adaptadores.CharacterAdapter;
import com.example.parcialtercercorte.clases.Character;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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
    private CharacterAdapter usuarioAdaptador;

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

        listaUsuario = new ArrayList<>();
        // Set up the adapter
         usuarioAdaptador = new CharacterAdapter(listaUsuario, new CharacterAdapter.OnItemClickListener() {
            @Override
            public void OnItemClick(Character item) {
                // Handle item click, e.g., show a description or navigate to another fragment/activity
            }
        });

        // Set up RecyclerView
        rcv_characters.setLayoutManager(new LinearLayoutManager(getContext()));
        rcv_characters.setAdapter(usuarioAdaptador);

        fetchDataFromApi();

        return view;
    }

    private void fetchDataFromApi() {
        String url = "https://nova-dev-420721.uc.r.appspot.com/marvel/characters";

        RequestQueue queue = Volley.newRequestQueue(getContext());

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray charactersArray = response.getJSONArray("data");
                            System.out.println("Arreglo pjs    " + charactersArray);
                            for (int i = 0; i < charactersArray.length(); i++) {
                                JSONObject characterObject = charactersArray.getJSONObject(i);

                                String id = characterObject.getString("id");
                                String name = characterObject.getString("name");
                                String description = characterObject.getString("description");
                                JSONObject thumbnail = characterObject.getJSONObject("thumbnail");
                                String imageUrl = thumbnail.getString("path") + "." + thumbnail.getString("extension");

                                listaUsuario.add(new Character(imageUrl, name, description, id));
                            }

                            // Notify adapter about data changes
                            usuarioAdaptador.notifyDataSetChanged();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });

        queue.add(jsonObjectRequest);
    }

}
