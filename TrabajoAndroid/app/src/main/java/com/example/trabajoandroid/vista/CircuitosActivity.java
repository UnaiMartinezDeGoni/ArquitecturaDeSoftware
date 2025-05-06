package com.example.trabajoandroid.vista;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

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
    private String usuarioNombre;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_circuitos);

        usuarioNombre = getIntent().getStringExtra("usuario_id");

        listView = findViewById(R.id.listViewCircuitos);
        String dificultad = getIntent().getStringExtra("dificultad");
        if (dificultad == null) {
            Toast.makeText(this, "No se ha recibido la dificultad", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        new ObtenerCircuitosTask().execute(dificultad);
    }

    private class ObtenerCircuitosTask extends AsyncTask<String, Void, ArrayList<Circuito>> {
        @Override
        protected ArrayList<Circuito> doInBackground(String... params) {
            try {
                return obtenerCircuitosPorDificultad(params[0]);
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(ArrayList<Circuito> result) {
            if (result == null) {
                Toast.makeText(CircuitosActivity.this,
                        "Error al cargar los circuitos.", Toast.LENGTH_SHORT).show();
                return;
            }
            circuitosList = result;
            circuitoAdapter = new CircuitoAdapter(CircuitosActivity.this, circuitosList);
            listView.setAdapter(circuitoAdapter);

            // Al hacer click, pasa checkpoints y nombre al mapa
            listView.setOnItemClickListener((parent, view, position, id) -> {
                Circuito sel = circuitosList.get(position);
                Intent intent = new Intent(CircuitosActivity.this, MapaActivity.class);
                intent.putStringArrayListExtra("CHECKPOINTS",
                        new ArrayList<>(sel.getCheckpoints()));
                intent.putExtra("CIRCUITO_NOMBRE", sel.getNombre());
                intent.putExtra("usuario_id", usuarioNombre);
                System.out.println(sel.getId());
                intent.putExtra("circuito_id", sel.getId());
                intent.putExtra("circuito_dificultad", sel.getDificultad());
                startActivity(intent);
            });
        }

        private ArrayList<Circuito> obtenerCircuitosPorDificultad(String filtro) throws IOException {
            ArrayList<Circuito> list = new ArrayList<>();
            String url = "https://parseapi.back4app.com/classes/Circuito";
            if (!"Todos".equals(filtro)) {
                url += "?where={\"dificultad\":\"" + filtro + "\"}";
            }

            OkHttpClient client = new OkHttpClient();
            Request req = new Request.Builder()
                    .url(url)
                    .addHeader("X-Parse-Application-Id", "EHUFM0VK5GnLAD601JX7T3sEfDSQ4tDkEPlZFqwH")
                    .addHeader("X-Parse-REST-API-Key", "eQNek326ON4ELaQdzCbRKt0dDQYSZKQlie504BWb")
                    .get().build();

            Response resp = client.newCall(req).execute();
            if (!resp.isSuccessful()) return list;

            JsonObject root = JsonParser.parseString(resp.body().string()).getAsJsonObject();
            JsonArray results = root.getAsJsonArray("results");
            for (int i = 0; i < results.size(); i++) {
                JsonObject obj = results.get(i).getAsJsonObject();
                String id = obj.get("objectId").getAsString();
                String nombre = obj.get("nombre").getAsString();
                String dif = obj.get("dificultad").getAsString();

                JsonArray cps = obj.getAsJsonArray("checkpoints");
                List<String> checkpoints = new ArrayList<>();
                for (int j = 0; j < cps.size(); j++) {
                    checkpoints.add(cps.get(j).getAsString());
                }

                JsonArray lis = obj.getAsJsonArray("listaEjercicios");
                List<String> ejercicios = new ArrayList<>();
                for (int j = 0; j < lis.size(); j++) {
                    ejercicios.add(lis.get(j).getAsString());
                }

                // Crear circuito y asignar el ID
                Circuito circuito = new Circuito(nombre, dif, checkpoints, ejercicios);
                circuito.setId(id);  // <-- asegurate de tener este setter en tu clase Circuito
                list.add(circuito);
            }
            return list;
        }
    }
}
