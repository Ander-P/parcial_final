package com.example.parcialtercercorte.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.parcialtercercorte.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class RequestFragment extends Fragment {

    private Spinner spinnerComics;
    private ArrayList<String> comicTitles;
    private Button btnSolicitar;

    public static final String KEY_EMAIL = "email";
    public static final String SHARED_PREF_NAME= "userData";
    private static final int mode_private = Context.MODE_PRIVATE;

    public RequestFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        comicTitles = new ArrayList<>();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_request, container, false);

        spinnerComics = view.findViewById(R.id.spinner_comics);
        btnSolicitar = view.findViewById(R.id.btn_solicitar);

        fetchDataFromApi();

        btnSolicitar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String selectedComic = (String) spinnerComics.getSelectedItem();
                sendPostRequest(selectedComic);
            }
        });

        return view;
    }

    private void fetchDataFromApi() {
        String url = "https://nova-dev-420721.uc.r.appspot.com/marvel/comics";
        RequestQueue queue = Volley.newRequestQueue(getContext());

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.d("API Response", "Respuesta de la API: " + response.toString());
                        try {
                            comicTitles.clear(); // Clear the list before adding new data
                            for (int i = 0; i < response.length(); i++) {
                                JSONObject comicObject = response.getJSONObject(i);
                                String title = comicObject.getString("title");
                                comicTitles.add(title);
                            }
                            // Usar el layout personalizado para el adaptador
                            ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), R.layout.spinner_item, comicTitles);
                            adapter.setDropDownViewResource(R.layout.spinner_item);
                            spinnerComics.setAdapter(adapter);
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

    private void sendPostRequest(String comicName) {
        String url = "https://nova-dev-420721.uc.r.appspot.com/solicitude/";
        RequestQueue queue = Volley.newRequestQueue(getContext());

        // Obtener email de SharedPreferences
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(SHARED_PREF_NAME, mode_private);
        String savedEmail = sharedPreferences.getString(KEY_EMAIL,  null);

        // Crear el objeto JSON para la solicitud
        Map<String, String> params = new HashMap<>();
        params.put("email", savedEmail);
        params.put("name", comicName);

        JsonObjectRequest postRequest = new JsonObjectRequest(Request.Method.POST, url, new JSONObject(params),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            String msg = response.getString("msg");
                            Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
                        } catch (JSONException e) {
                            Log.e("JSON Error", "Error procesando JSON", e);
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("API Error", "Error en la solicitud POST: " + error.getMessage());
                        Toast.makeText(getContext(), "Error en la solicitud", Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<>();
                headers.put("Accept", "*/*");
                headers.put("Content-Type", "application/json");
                return headers;
            }
        };

        queue.add(postRequest);
    }
}
