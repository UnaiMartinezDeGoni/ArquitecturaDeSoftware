package com.example.trabajoandroid.vista;

import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;

import android.content.Intent;
import com.example.trabajoandroid.vista.MapaActivity;


import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.trabajoandroid.R;
import com.example.trabajoandroid.modelo.Circuito;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class CircuitosActivity extends AppCompatActivity {
    private ListView listView;
    private CircuitoAdapter circuitoAdapter;
    private ArrayList<Circuito> circuitosList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_circuitos);

        listView = findViewById(R.id.listViewCircuitos);

        // Obtener el tipo de dificultad desde el Intent
        String dificultad = getIntent().getStringExtra("dificultad");

        // Comprobar que el valor de dificultad no es null
        if (dificultad == null) {
            Toast.makeText(this, "No se ha recibido la dificultad", Toast.LENGTH_SHORT).show();
            return;
        }

        // Ejecutar AsyncTask para obtener los circuitos
        new ObtenerCircuitosTask().execute(dificultad);
    }

    // AsyncTask para obtener los circuitos
    private class ObtenerCircuitosTask extends AsyncTask<String, Void, ArrayList<Circuito>> {

        @Override
        protected ArrayList<Circuito> doInBackground(String... params) {
            // Llamar a la función que obtiene los circuitos
            try {
                return obtenerCircuitosPorDificultad(params[0]);
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(ArrayList<Circuito> result) {
            super.onPostExecute(result);
            if (result != null) {
                circuitosList = result;
                circuitoAdapter = new CircuitoAdapter(CircuitosActivity.this, circuitosList);
                listView.setAdapter(circuitoAdapter);

                // ← AQUÍ: listener para abrir el mapa al pulsar un circuito
                listView.setOnItemClickListener((parent, view, position, id) -> {
                    Circuito seleccionado = circuitosList.get(position);

                    // En tu modelo Circuito podrías tener un ID numérico o derivar uno
                    // Por simplicidad, usamos position+1 como circuitoId
                    int circuitoId = position + 1;

                    Intent intent = new Intent(CircuitosActivity.this, MapaActivity.class);
                    intent.putExtra("CIRCUITO_ID", circuitoId);

                    startActivity(intent);
                });

            } else {
                Toast.makeText(CircuitosActivity.this,
                        "Error al cargar los circuitos. Intenta más tarde.", Toast.LENGTH_SHORT).show();
            }
        }


        private ArrayList<Circuito> obtenerCircuitosPorDificultad(String filtroDificultad) throws IOException {
            ArrayList<Circuito> circuitos = new ArrayList<>();

            String url;
            // Verifica si el filtro de dificultad es "todos", en ese caso, obtenemos todos los circuitos sin filtro
            if ("Todos".equals(filtroDificultad)) {
                url = "https://parseapi.back4app.com/classes/Circuito";  // URL sin filtro de dificultad
            } else {
                // Si no es "todos", filtrar por la dificultad específica
                url = "https://parseapi.back4app.com/classes/Circuito?where={\"dificultad\":\"" + filtroDificultad + "\"}";
            }

            // Cliente HTTP y solicitud
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                    .url(url)
                    .addHeader("X-Parse-Application-Id", "EHUFM0VK5GnLAD601JX7T3sEfDSQ4tDkEPlZFqwH") // Tu Application ID
                    .addHeader("X-Parse-REST-API-Key", "eQNek326ON4ELaQdzCbRKt0dDQYSZKQlie504BWb") // Tu REST API Key
                    .get()
                    .build();

            Response response = client.newCall(request).execute();

            if (response.isSuccessful()) {
                String responseBody = response.body().string();

                // Convertir la respuesta JSON en un objeto JSON
                JsonObject json = JsonParser.parseString(responseBody).getAsJsonObject();
                JsonArray results = json.getAsJsonArray("results");

                // Iterar sobre los resultados
                for (int i = 0; i < results.size(); i++) {
                    JsonObject circuitoJson = results.get(i).getAsJsonObject();

                    // Obtener los datos básicos del circuito
                    String nombre = circuitoJson.get("nombre").getAsString();
                    String dificultadCircuito = circuitoJson.get("dificultad").getAsString();

                    // Obtener los checkpoints y listaEjercicios
                    JsonArray checkpointsArray = circuitoJson.getAsJsonArray("checkpoints");
                    List<String> checkpoints = new ArrayList<>();
                    for (int j = 0; j < checkpointsArray.size(); j++) {
                        checkpoints.add(checkpointsArray.get(j).getAsString());
                    }

                    JsonArray listaEjerciciosArray = circuitoJson.getAsJsonArray("listaEjercicios");
                    List<String> listaEjercicios = new ArrayList<>();
                    for (int j = 0; j < listaEjerciciosArray.size(); j++) {
                        listaEjercicios.add(listaEjerciciosArray.get(j).getAsString());
                    }

                    // Crear un objeto Circuito con los datos extraídos
                    Circuito circuito = new Circuito(nombre, dificultadCircuito, checkpoints, listaEjercicios);
                    circuitos.add(circuito);
                }
            }

            return circuitos;
        }
    }
}