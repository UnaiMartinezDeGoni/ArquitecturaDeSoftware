package com.example.trabajoandroid.vista;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.trabajoandroid.R;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import Bussiness.EjercicioService;
import Bussiness.modelo.Ejercicio;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class InformacionEjercicioActivity extends AppCompatActivity {
    private ImageView imageView;
    private TextView textViewInfo;
    private static final String BASE_URL = "https://parseapi.back4app.com/";
    private static final String APP_ID = "EHUFM0VK5GnLAD601JX7T3sEfDSQ4tDkEPlZFqwH";
    private static final String API_KEY = "eQNek326ON4ELaQdzCbRKt0dDQYSZKQlie504BWb";
    private final OkHttpClient client = new OkHttpClient();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_informacion_ejercicio);

        imageView = findViewById(R.id.imageViewEjercicio);
        textViewInfo = findViewById(R.id.textViewInfoEjercicio);

        String idEjercicio = getIntent().getStringExtra("idEjercicio");
        String idItemEjercicio = getIntent().getStringExtra("idItemEjercicio");

        cargarDetalleEjercicio(idEjercicio, idItemEjercicio);
    }

    private void cargarDetalleEjercicio(String idEjercicio, String idItemEjercicio) {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Handler handler = new Handler(Looper.getMainLooper());

        executor.execute(() -> {
            // Realizamos la solicitud HTTP para obtener la imagen
            String API_URL = "https://parseapi.back4app.com/classes/Imagen?where={\"idEjercicio\":\"" + idItemEjercicio + "\"}";

            Request request = new Request.Builder()
                    .url(API_URL)
                    .addHeader("X-Parse-Application-Id", APP_ID)
                    .addHeader("X-Parse-REST-API-Key", API_KEY)
                    .build();

            try (Response response = client.newCall(request).execute()) {
                if (response.isSuccessful() && response.body() != null) {
                    String json = response.body().string();
                    JsonObject obj = JsonParser.parseString(json).getAsJsonObject();
                    JsonArray results = obj.getAsJsonArray("results");

                    String url = null;
                    if (results.size() > 0) {
                        url = results.get(0).getAsJsonObject().get("url").getAsString();
                    }

                    // Asegurarse de pasar la URL correctamente al hilo principal
                    String finalUrl = url;
                    handler.post(() -> {
                        // Consultamos la base de datos en un hilo separado
                        obtenerEjercicioYActualizarUI(idEjercicio, finalUrl);
                    });
                } else {
                    handler.post(() -> {
                        Toast.makeText(InformacionEjercicioActivity.this, "Error al obtener la imagen", Toast.LENGTH_SHORT).show();
                    });
                }
            } catch (IOException e) {
                e.printStackTrace();
                handler.post(() -> {
                    Toast.makeText(InformacionEjercicioActivity.this, "Error de red", Toast.LENGTH_SHORT).show();
                });
            }
        });
    }

    private void obtenerEjercicioYActualizarUI(String idEjercicio, String urlImagen) {
        // Cargar los detalles del ejercicio desde la base de datos en un hilo en segundo plano
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Handler handler = new Handler(Looper.getMainLooper());

        executor.execute(() -> {
            EjercicioService es = new EjercicioService();
            List<Ejercicio> ejs = es.getAll();
            for (Ejercicio ej : ejs) {
                if (ej.getId().equals(idEjercicio)) {
                    // Cuando encontramos el ejercicio, actualizamos la UI en el hilo principal
                    handler.post(() -> {
                        textViewInfo.setText("Titulo: " + ej.getNombre() + "\nDescripcion: " + ej.getDescripcion());
                    });
                    break;
                }
            }

            // Si la URL de la imagen es válida, la cargamos con Glide
            if (urlImagen != null) {
                handler.post(() -> Glide.with(InformacionEjercicioActivity.this).load(urlImagen).into(imageView));
            } else {
                handler.post(() -> {
                    Toast.makeText(InformacionEjercicioActivity.this, "No se encontró imagen", Toast.LENGTH_SHORT).show();
                });
            }
        });
    }
}
