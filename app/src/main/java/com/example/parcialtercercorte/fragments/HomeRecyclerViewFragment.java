package com.example.parcialtercercorte.fragments;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
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

public class HomeRecyclerViewFragment extends Fragment {

    private RecyclerView rcv_characters;
    private List<Character> listaUsuario;
    private CharacterAdapter usuarioAdaptador;

    public HomeRecyclerViewFragment() {
        // Required empty public constructor
    }

    public static HomeRecyclerViewFragment newInstance() {
        HomeRecyclerViewFragment fragment = new HomeRecyclerViewFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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

        // Cambiar JsonObjectRequest a JsonArrayRequest
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.d("API Response", "Respuesta de la API: " + response.toString());
                        try {
                            // Directamente procesar el JSONArray
                            for (int i = 0; i < response.length(); i++) {
                                JSONObject characterObject = response.getJSONObject(i);

                                String id = characterObject.getString("id");
                                String name = characterObject.getString("name");
                                String description = characterObject.optString("description", "No description available");
                                JSONObject thumbnail = characterObject.getJSONObject("thumbnail");
                                String imageUrl = thumbnail.getString("path") + "." + thumbnail.getString("extension");
                                Log.d("imageUrl", "IMAGEN: " + imageUrl);
                                listaUsuario.add(new Character(imageUrl, name, description, id));
                            }

                            // Notificar al adaptador sobre los cambios de datos
                            usuarioAdaptador.notifyDataSetChanged();
                        } catch (JSONException e) {
                            Log.e("JSON Error", "Error procesando JSON", e);
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("API Error", "Error al obtener datos de la API: " + error.getMessage());
            }
        });

        queue.add(jsonArrayRequest);
    }

}
