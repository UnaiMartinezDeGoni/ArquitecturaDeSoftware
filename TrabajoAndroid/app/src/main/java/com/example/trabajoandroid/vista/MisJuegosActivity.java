package com.example.trabajoandroid.vista;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.trabajoandroid.LocalTimeAdapter;
import com.example.trabajoandroid.R;
import com.example.trabajoandroid.modelo.Circuito;
import com.example.trabajoandroid.modelo.Juego;
import com.example.trabajoandroid.modelo.Usuario;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.IOException;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MisJuegosActivity extends AppCompatActivity {

    private ListView listView;
    private final String APPLICATION_ID ="EHUFM0VK5GnLAD601JX7T3sEfDSQ4tDkEPlZFqwH";
    private final String REST_API_KEY ="eQNek326ON4ELaQdzCbRKt0dDQYSZKQlie504BWb";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mis_juegos);

        listView = findViewById(R.id.listViewJuegos);
        String usuarioNombre = getIntent().getStringExtra("usuario");
        Log.d("Nombre usuario", usuarioNombre);
        obtenerUsuarioYJuegos(usuarioNombre);
    }

    private void obtenerUsuarioYJuegos(String objectUsuario) {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Handler handler = new Handler(Looper.getMainLooper());

        executor.execute(() -> {
            String url = "https://parseapi.back4app.com/classes/Usuario?where={\"objectId\":\"" + objectUsuario + "\"}";
            OkHttpClient client = new OkHttpClient();

            Request request = new Request.Builder()
                    .url(url)
                    .addHeader("X-Parse-Application-Id", APPLICATION_ID)
                    .addHeader("X-Parse-REST-API-Key", REST_API_KEY)
                    .addHeader("Content-Type", "application/json")
                    .build();

            try (Response response = client.newCall(request).execute()) {
                if (response.isSuccessful() && response.body() != null) {
                    String json = response.body().string();

                    Gson gson = new Gson();
                    UsuarioResponse usuarioResponse = gson.fromJson(json, UsuarioResponse.class);

                    if (usuarioResponse.results != null && !usuarioResponse.results.isEmpty()) {
                        Usuario usuario = usuarioResponse.results.get(0);
                        cargarJuegosDelUsuario(usuario);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    private void cargarJuegosDelUsuario(Usuario usuario) {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Handler handler = new Handler(Looper.getMainLooper());

        executor.execute(() -> {
            List<String> detalles = new ArrayList<>();

            for (String idJuego : usuario.getJuegosRealizados()) {
                Juego juego = obtenerJuegoPorId(idJuego);
                Log.d("juego", juego.toString());
                if (juego != null) {
                    Circuito circuito = obtenerCircuitoPorId(juego.getCircuito());
                    assert circuito != null;
                    String detalle = "Circuito: " + circuito.getNombre()
                            + " | Dificultad: " + juego.getDificultad()
                            + " | Tiempo: " + String.format("%.2f", juego.getTime()) + " min";
                    detalles.add(detalle);
                } else {
                    detalles.add("Juego no encontrado (ID: " + idJuego + ")");
                }
            }

            handler.post(() -> {
                ArrayAdapter<String> adapter = new ArrayAdapter<>(MisJuegosActivity.this,
                        android.R.layout.simple_list_item_1, detalles);
                listView.setAdapter(adapter);
            });
        });
    }

    private Juego obtenerJuegoPorId(String objectId) {
        String API_URL = "https://parseapi.back4app.com/classes/Juego?where={\"objectId\":\"" + objectId + "\"}";
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url(API_URL)
                .addHeader("X-Parse-Application-Id", APPLICATION_ID)
                .addHeader("X-Parse-REST-API-Key", REST_API_KEY)
                .addHeader("Content-Type", "application/json")
                .get()
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (response.isSuccessful() && response.body() != null) {
                String json = response.body().string();
                Log.d("Juego json:", json);

                Gson gson = new Gson();
                JuegoResponse juegoResponse = gson.fromJson(json, JuegoResponse.class);
                Log.d("JuegoResponse: ", juegoResponse.toString());

                if (juegoResponse.results != null && !juegoResponse.results.isEmpty()) {
                    Juego juego = juegoResponse.results.get(0);
                    Log.d("Juego: ", String.valueOf(juego));
                    return juego;
                }

            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    private Circuito obtenerCircuitoPorId(String objectId){
        String API_URL = "https://parseapi.back4app.com/classes/Circuito?where={\"objectId\":\"" + objectId + "\"}";
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url(API_URL)
                .addHeader("X-Parse-Application-Id", APPLICATION_ID)
                .addHeader("X-Parse-REST-API-Key", REST_API_KEY)
                .addHeader("Content-Type", "application/json")
                .get()
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (response.isSuccessful() && response.body() != null) {
                String json = response.body().string();
                Log.d("Circuito json:", json);

                Gson gson = new Gson();
                CircuitoResponse circuitoResponse = gson.fromJson(json, CircuitoResponse.class);
                Log.d("CircuitoResponse: ", circuitoResponse.toString());

                if (circuitoResponse.results != null && !circuitoResponse.results.isEmpty()) {
                    Circuito circuito = circuitoResponse.results.get(0);
                    Log.d("Circuito: ", String.valueOf(circuito));
                    return circuito;
                }

            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }


    private static class UsuarioResponse {
        List<Usuario> results;
    }

    private static class JuegoResponse {
        List<Juego> results;
    }

    private static class CircuitoResponse {
        List<Circuito> results;
    }
}
