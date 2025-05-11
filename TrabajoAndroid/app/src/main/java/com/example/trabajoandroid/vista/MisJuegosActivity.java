package com.example.trabajoandroid.vista;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.trabajoandroid.R;

import Bussiness.CircuitosService;
import Bussiness.modelo.Circuito;
import Bussiness.modelo.Juego;
import Bussiness.modelo.Usuario;
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

import DAO.dao.InventarioCircuitos;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MisJuegosActivity extends AppCompatActivity {
    private ListView listView;
    private final String APPLICATION_ID ="EHUFM0VK5GnLAD601JX7T3sEfDSQ4tDkEPlZFqwH";
    private final String REST_API_KEY ="eQNek326ON4ELaQdzCbRKt0dDQYSZKQlie504BWb";
    private Juego juego;

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
            List<Map<String, String>> datos = new ArrayList<>();
            List<Circuito> circuitos = new ArrayList<>(); // Para guardar circuitos

            for (String idJuego : usuario.getJuegosRealizados()) {
                juego = obtenerJuegoPorId(idJuego);
                if (juego != null) {
                    Circuito circuito = obtenerCircuitoPorId(juego.getCircuito());
                    if (circuito != null) {
                        Map<String, String> map = new HashMap<>();
                        map.put("nombre", circuito.getNombre());
                        datos.add(map);
                        circuitos.add(circuito);
                    }
                }
            }

            handler.post(() -> {
                SimpleAdapter adapter = new SimpleAdapter(
                        MisJuegosActivity.this,
                        datos,
                        android.R.layout.simple_list_item_1,
                        new String[]{"nombre"},
                        new int[]{android.R.id.text1}
                );

                listView.setAdapter(adapter);

                // Manejar clics
                listView.setOnItemClickListener((parent, view, position, id) -> {
                    Circuito circuitoSeleccionado = circuitos.get(position);
                    Intent intent = new Intent(MisJuegosActivity.this, InformacionCircuitoActivity.class);
                    intent.putExtra("circuitoJson", new Gson().toJson(circuitoSeleccionado));
                    intent.putExtra("tiempo", juego.getTime());
                    startActivity(intent);
                });
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


    private static class UsuarioResponse {
        List<Usuario> results;
    }

    private static class JuegoResponse {
        List<Juego> results;
    }

    private List<String> obtenerImagenesPorEjercicio(String idEjercicio) {
        List<String> urls = new ArrayList<>();
        String API_URL = "https://parseapi.back4app.com/classes/Imagen?where={\"idEjercicio\":\"" + idEjercicio + "\"}";
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url(API_URL)
                .addHeader("X-Parse-Application-Id", APPLICATION_ID)
                .addHeader("X-Parse-REST-API-Key", REST_API_KEY)
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (response.isSuccessful() && response.body() != null) {
                String json = response.body().string();
                JsonObject obj = JsonParser.parseString(json).getAsJsonObject();
                JsonArray results = obj.getAsJsonArray("results");

                for (int i = 0; i < results.size(); i++) {
                    JsonObject imgObj = results.get(i).getAsJsonObject();
                    if (imgObj.has("url")) {
                        urls.add(imgObj.get("url").getAsString());
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return urls;
    }

    public Circuito obtenerCircuitoPorId(String objectId){
        // La URL de la API debería construirse correctamente para hacer la búsqueda por objectId
        String apiUrl = "https://parseapi.back4app.com/classes/Circuito?where={\"objectId\":\"" + objectId + "\"}";

        // Crear el cliente HTTP para la solicitud
        OkHttpClient client = new OkHttpClient();

        // Construir la solicitud GET
        Request request = new Request.Builder()
                .url(apiUrl)
                .addHeader("X-Parse-Application-Id", APPLICATION_ID)
                .addHeader("X-Parse-REST-API-Key", REST_API_KEY)
                .addHeader("Content-Type", "application/json")
                .get()
                .build();

        // Realizar la solicitud y manejar la respuesta
        try (Response response = client.newCall(request).execute()) {
            if (response.isSuccessful() && response.body() != null) {
                String json = response.body().string();  // Obtener la respuesta en formato JSON

                // Para depuración, imprimimos el JSON recibido
                Log.d("Circuito json:", json);

                // Convertir el JSON a un objeto Circuito usando Gson
                Gson gson = new Gson();
                MisJuegosActivity.CircuitoResponse circuitoResponse = gson.fromJson(json, MisJuegosActivity.CircuitoResponse.class);

                // Depurar la respuesta para ver si se han recibido datos
                Log.d("CircuitoResponse: ", circuitoResponse.toString());

                // Verificar si tenemos resultados
                if (circuitoResponse.results != null && !circuitoResponse.results.isEmpty()) {
                    Circuito circuito = circuitoResponse.results.get(0);  // Obtener el primer circuito
                    Log.d("Circuito: ", String.valueOf(circuito));  // Depuración para ver el objeto Circuito
                    return circuito;  // Devolver el circuito encontrado
                } else {
                    Log.e("Error:", "No se encontró el circuito con el ID especificado.");
                }
            } else {
                Log.e("Error:", "Respuesta no exitosa al buscar el circuito.");
            }
        } catch (IOException e) {
            e.printStackTrace();
            Log.e("Error:", "Error en la solicitud GET: " + e.getMessage());
        }

        return null;  // Devolver null si no se encontró el circuito
    }

    private static class CircuitoResponse{
        List<Circuito> results;
    }
}