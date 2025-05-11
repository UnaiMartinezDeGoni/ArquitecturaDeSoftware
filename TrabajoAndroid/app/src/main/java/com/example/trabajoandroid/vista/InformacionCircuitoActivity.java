package com.example.trabajoandroid.vista;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.trabajoandroid.R;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import Bussiness.modelo.Circuito;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class InformacionCircuitoActivity extends AppCompatActivity {
    private TextView textViewInfo;
    private ListView listViewEjercicios;
    private static final String BASE_URL = "https://parseapi.back4app.com/";
    private static final String APP_ID = "EHUFM0VK5GnLAD601JX7T3sEfDSQ4tDkEPlZFqwH";
    private static final String API_KEY = "eQNek326ON4ELaQdzCbRKt0dDQYSZKQlie504BWb";
    private final OkHttpClient client = new OkHttpClient();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_informacion_circuito);

        textViewInfo = findViewById(R.id.textViewInfoCircuito);
        listViewEjercicios = findViewById(R.id.listViewEjercicios);

        String circuitoJson = getIntent().getStringExtra("circuitoJson");
        String tiempo = getIntent().getStringExtra("tiempo");
        Circuito circuito = new Gson().fromJson(circuitoJson, Circuito.class);

        textViewInfo.setText("Nombre: " + circuito.getNombre()
                + "\nDificultad: " + circuito.getDificultad()
                + "\nTiempo: " + tiempo + " minutos");

        List<String> idsEjercicios = circuito.getListaEjercicios();
        List<Map<String, String>> datos = new ArrayList<>();

        obtenerTitulosEjercicios(idsEjercicios, datos);
    }

    private void obtenerTitulosEjercicios(List<String> idsEjercicios, List<Map<String, String>> datos) {
        Handler handler = new Handler(Looper.getMainLooper());

        for (String idEj : idsEjercicios) {
            obtenerEjercicioRelacionado(idEj, datos, handler, idsEjercicios);
        }
    }

    private void obtenerEjercicioRelacionado(String itemEjercicioId, List<Map<String, String>> datos, Handler handler, List<String> idsEjercicios) {
        String where = "{\"objectIdItemEjercicio\":\"" + itemEjercicioId + "\"}";
        String url = "https://parseapi.back4app.com/classes/EjercicioItemEjercicio?where=" + where;

        Request request = new Request.Builder()
                .url(url)
                .addHeader("X-Parse-Application-Id", APP_ID)
                .addHeader("X-Parse-REST-API-Key", API_KEY)
                .get()
                .build();

        new Thread(() -> {
            try {
                Response response = client.newCall(request).execute();

                if (response.isSuccessful()) {
                    String body = response.body().string();
                    Log.d(TAG, "Respuesta de EjercicioItemEjercicio: " + body);

                    JsonObject json = JsonParser.parseString(body).getAsJsonObject();
                    JsonArray results = json.getAsJsonArray("results");

                    if (results.size() > 0) {
                        JsonObject relacion = results.get(0).getAsJsonObject();
                        if (relacion.has("objectIdEjercicio") && !relacion.get("objectIdEjercicio").isJsonNull()) {
                            String ejercicioId = relacion.get("objectIdEjercicio").getAsString();
                            Log.d(TAG, "Ejercicio ID relacionado: " + ejercicioId);
                            obtenerTituloEjercicio(ejercicioId, itemEjercicioId, datos, handler, idsEjercicios);
                        } else {
                            runOnUiThread(() -> Toast.makeText(this, "El campo 'objectIdEjercicio' no está presente o es nulo.", Toast.LENGTH_LONG).show());
                            Log.e(TAG, "Campo 'objectIdEjercicio' faltante o nulo.");
                        }
                    } else {
                        runOnUiThread(() -> Toast.makeText(this, "No se encontró relación con Ejercicio", Toast.LENGTH_LONG).show());
                    }
                } else {
                    Log.e(TAG, "Error en relación: " + response.message());
                }
            } catch (IOException e) {
                Log.e(TAG, "Excepción al obtener relación: " + e.getMessage());
            }
        }).start();
    }

    private void obtenerTituloEjercicio(String idEjercicio, String idItemEjercicio, List<Map<String, String>> datos, Handler handler, List<String> idsEjercicios) {
        String url = BASE_URL + "classes/Ejercicio/" + idEjercicio;

        Request request = new Request.Builder()
                .url(url)
                .addHeader("X-Parse-Application-Id", APP_ID)
                .addHeader("X-Parse-REST-API-Key", API_KEY)
                .get()
                .build();

        new Thread(() -> {
            try {
                Response response = client.newCall(request).execute();

                if (response.isSuccessful()) {
                    String body = response.body().string();
                    Log.d(TAG, "Respuesta de Ejercicio: " + body);

                    JsonObject ejercicio = JsonParser.parseString(body).getAsJsonObject();

                    if (ejercicio.has("nombre")) {
                        String nombre = ejercicio.get("nombre").getAsString();

                        Map<String, String> map = new HashMap<>();
                        map.put("idEjercicio", idEjercicio);
                        map.put("idItemEjercicio", idItemEjercicio); // <-- ahora sí lo tendrás
                        map.put("titulo", nombre);

                        handler.post(() -> {
                            datos.add(map);
                            if (datos.size() == idsEjercicios.size()) {
                                actualizarListaEjercicios(datos);
                            }
                        });
                    } else {
                        Log.e(TAG, "El objeto Ejercicio no contiene el campo 'nombre'.");
                    }
                } else {
                    Log.e(TAG, "Error al obtener Ejercicio: Código " + response.code() + " - " + response.body().string());
                }
            } catch (IOException e) {
                Log.e(TAG, "Excepción al obtener Ejercicio: " + e.getMessage());
            }
        }).start();
    }

    private void actualizarListaEjercicios(List<Map<String, String>> datos) {
        SimpleAdapter adapter = new SimpleAdapter(
                this,
                datos,
                android.R.layout.simple_list_item_1,
                new String[]{"titulo"},
                new int[]{android.R.id.text1}
        );

        listViewEjercicios.setAdapter(adapter);

        listViewEjercicios.setOnItemClickListener((parent, view, position, id) -> {
            String idEjercicio = datos.get(position).get("idEjercicio");
            String idItemEjercicio = datos.get(position).get("idItemEjercicio");
            Intent intent = new Intent(InformacionCircuitoActivity.this, InformacionEjercicioActivity.class);
            intent.putExtra("idEjercicio", idEjercicio);
            intent.putExtra("idItemEjercicio", idItemEjercicio);
            startActivity(intent);
        });
    }
}
