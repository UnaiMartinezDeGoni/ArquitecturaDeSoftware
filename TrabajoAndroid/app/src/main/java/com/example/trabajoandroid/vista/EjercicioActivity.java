package com.example.trabajoandroid.vista;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import android.Manifest;
import android.content.ContentValues;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.camera.core.CameraInfo;
import androidx.camera.core.CameraSelector;
import androidx.camera.core.ImageCapture;
import androidx.camera.core.ImageCaptureException;
import androidx.camera.core.Preview;
import androidx.camera.lifecycle.ProcessCameraProvider;
import androidx.camera.view.PreviewView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.LifecycleOwner;

import com.example.trabajoandroid.R;
import com.example.trabajoandroid.modelo.ItemEjercicio;
import com.example.trabajoandroid.modelo.Usuario;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.File;
import java.io.IOException;

import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class EjercicioActivity extends AppCompatActivity {
    private TextView nombreTextView;
    private TextView descripcionTextView;
    private static final String BASE_URL = "https://parseapi.back4app.com/";
    private static final String APP_ID = "EHUFM0VK5GnLAD601JX7T3sEfDSQ4tDkEPlZFqwH";
    private static final String API_KEY = "eQNek326ON4ELaQdzCbRKt0dDQYSZKQlie504BWb";
    private final OkHttpClient client = new OkHttpClient();
    Button btnCamara;
    Button btnTerminar;
    ImageView visor;
    private PreviewView previewView;
    private ImageCapture imageCapture;
    private final int REQUEST_CAMERA_PERMISSION = 100;
    private Handler photoHandler;
    private Runnable photoRunnable;
    private boolean fotoTomada = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ejercicio);

        nombreTextView = findViewById(R.id.nombreTextView);
        descripcionTextView = findViewById(R.id.descripcionTextView);

        String idEj = getIntent().getStringExtra("ejercicio_id");
        Log.d(TAG, "ejercicio_id recibido: " + idEj);
        System.out.println(idEj);
        if (idEj != null) {
            obtenerEjercicioRelacionado(idEj);
        } else {
            Toast.makeText(this, "ID de ItemEjercicio no proporcionado", Toast.LENGTH_LONG).show();
        }

        btnCamara = findViewById(R.id.buttonCamera);
        btnCamara.setOnClickListener(view -> {
            if (!fotoTomada) {  // Solo tomar foto si no se ha tomado una antes
                tomarFoto();
            } else {
                Toast.makeText(this, "Ya se ha tomado una foto", Toast.LENGTH_SHORT).show();
            }
        });

        btnTerminar = findViewById(R.id.buttonTerminar);
        btnTerminar.setEnabled(false); // Inicialmente deshabilitado

        btnTerminar.setOnClickListener(view -> {
            // Esto lleva a la actividad anterior
            finish();  // Esto finaliza la actividad actual y vuelve a la anterior
        });

        // Configuración del visor y permisos de la cámara
        previewView = findViewById(R.id.previewView);
        visor = findViewById(R.id.iv_visor);

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.CAMERA},
                    REQUEST_CAMERA_PERMISSION);
        } else {
            iniciarCamara();
        }

        // Código para la toma de fotos cada 20 segundos
        photoHandler = new Handler();
        photoRunnable = new Runnable() {
            @Override
            public void run() {
                tomarFoto();
                photoHandler.postDelayed(this, 5000);  // Esto se puede ajustar a 20 segundos según lo que necesites
            }
        };

        photoHandler.postDelayed(photoRunnable, 20000);
    }

    private void obtenerEjercicioRelacionado(String itemEjercicioId) {
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
                // Ejecuta la solicitud en el hilo secundario
                Response response = client.newCall(request).execute();

                if (response.isSuccessful()) {
                    String body = response.body().string();
                    Log.d(TAG, "Respuesta de EjercicioItemEjercicio: " + body);

                    JsonObject json = JsonParser.parseString(body).getAsJsonObject();
                    JsonArray results = json.getAsJsonArray("results");

                    if (results.size() > 0) {
                        JsonObject relacion = results.get(0).getAsJsonObject();
                        if (relacion.has("objectIdEjercicio") && !relacion.get("objectIdEjercicio").isJsonNull()) {
                            // Obtiene el valor como un String
                            String ejercicioId = relacion.get("objectIdEjercicio").getAsString();
                            Log.d(TAG, "Ejercicio ID relacionado: " + ejercicioId);
                            obtenerDatosEjercicio(ejercicioId);
                        } else {
                            runOnUiThread(() -> Toast.makeText(this, "El campo 'objectIdEjercicio' no está presente o es nulo.", Toast.LENGTH_LONG).show());
                            Log.e(TAG, "Campo 'objectIdEjercicio' faltante o nulo en la relación JSON.");
                        }


                        // Llamamos al método que obtiene los datos del ejercicio

                    } else {
                        // Si no se encuentra la relación, mostramos un Toast en el hilo principal
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

    private void obtenerDatosEjercicio(String ejercicioId) {
        // Paso 2: consultar el objeto Ejercicio
        String url = BASE_URL + "classes/Ejercicio/" + ejercicioId;

        Request request = new Request.Builder()
                .url(url)
                .addHeader("X-Parse-Application-Id", APP_ID)
                .addHeader("X-Parse-REST-API-Key", API_KEY)
                .get()
                .build();

        new Thread(() -> {
            try {
                // Ejecuta la solicitud en el hilo secundario
                Response response = client.newCall(request).execute();

                if (response.isSuccessful()) {
                    String body = response.body().string();
                    Log.d(TAG, "Respuesta de Ejercicio: " + body);

                    // Procesa la respuesta en un objeto Json
                    JsonObject ejercicio = JsonParser.parseString(body).getAsJsonObject();

                    if (ejercicio.has("nombre") && ejercicio.has("descripcion")) {
                        String nombre = ejercicio.get("nombre").getAsString();
                        String descripcion = ejercicio.get("descripcion").getAsString();

                        // Actualiza la UI en el hilo principal
                        runOnUiThread(() -> {
                            nombreTextView.setText(nombre);
                            descripcionTextView.setText(descripcion);
                        });
                    } else {
                        Log.e(TAG, "El objeto Ejercicio no contiene los campos esperados.");
                    }
                } else {
                    Log.e(TAG, "Error al obtener Ejercicio: " + response.message());
                }
            } catch (IOException e) {
                Log.e(TAG, "Excepción al obtener Ejercicio: " + e.getMessage());
            }
        }).start();
    }

    private void iniciarCamara() {
        ListenableFuture<ProcessCameraProvider> cameraProviderFuture = ProcessCameraProvider.getInstance(this);
        cameraProviderFuture.addListener(() -> {
            try {
                ProcessCameraProvider cameraProvider = cameraProviderFuture.get();

                Preview preview = new Preview.Builder()
                        .build();
                CameraSelector cameraSelector = new CameraSelector.Builder()
                        .requireLensFacing(CameraSelector.LENS_FACING_FRONT)
                        .build();
                CameraInfo camerainfo = cameraProvider.bindToLifecycle(
                        (LifecycleOwner) this,
                        cameraSelector,
                        preview
                ).getCameraInfo();
                preview.setSurfaceProvider(previewView.createSurfaceProvider(camerainfo));



                imageCapture = new ImageCapture.Builder().build();

                cameraProvider.bindToLifecycle(
                        (LifecycleOwner) this,
                        cameraSelector,
                        preview,
                        imageCapture
                );
            } catch (Exception e) {
                Log.e("CameraX", "Error al iniciar la cámara", e);
            }
        }, ContextCompat.getMainExecutor(this));
    }

    private void tomarFoto() {
        if (imageCapture == null || fotoTomada) {
            return;  // Si ya se ha tomado una foto, no se vuelve a ejecutar
        }

        fotoTomada = true;

        btnTerminar.setEnabled(true);

        File fotoFile = new File(getExternalFilesDir(null), "foto_" + System.currentTimeMillis() + ".jpg");
        ContentValues values = new ContentValues();
        values.put(MediaStore.Images.Media.TITLE, "Foto tomada con CameraX");
        values.put(MediaStore.Images.Media.DESCRIPTION, "Foto tomada desde la app");

        imageCapture.takePicture(
                new ImageCapture.OutputFileOptions.Builder(fotoFile).build(),
                ContextCompat.getMainExecutor(this),
                new ImageCapture.OnImageSavedCallback() {
                    @Override
                    public void onImageSaved(@NonNull ImageCapture.OutputFileResults outputFileResults) {
                        Log.d("MainActivity", "Imagen guardada en " + outputFileResults.getSavedUri());
                        Bitmap bitmap = BitmapFactory.decodeFile(fotoFile.getAbsolutePath());
                        visor.setImageBitmap(bitmap);
                    }

                    @Override
                    public void onError(@NonNull ImageCaptureException exception) {
                        Log.e("MainActivity", "Error al capturar la foto", exception);
                    }
                });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CAMERA_PERMISSION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                iniciarCamara();
            } else {
                Toast.makeText(this, "Permiso de cámara denegado", Toast.LENGTH_SHORT).show();
            }
        }
    }
    @Override
    protected void onStart() {
        super.onStart();
        photoHandler.postDelayed(photoRunnable, 20000);  // Toma una foto después de 20 segundos
    }

    @Override
    protected void onStop() {
        super.onStop();
        photoHandler.removeCallbacks(photoRunnable);  // Detener el ciclo cuando la actividad se detiene
    }
}