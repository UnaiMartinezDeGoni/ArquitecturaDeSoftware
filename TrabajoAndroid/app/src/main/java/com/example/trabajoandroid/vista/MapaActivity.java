package com.example.trabajoandroid.vista;

import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.FragmentActivity;

import com.example.trabajoandroid.R;
import com.example.trabajoandroid.UbicacionHelper;
import com.example.trabajoandroid.dao.InventarioUsuario;
import com.example.trabajoandroid.modelo.Juego;
import com.example.trabajoandroid.modelo.Usuario;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

public class MapaActivity extends FragmentActivity implements OnMapReadyCallback {
    private GoogleMap mMap;
    private UbicacionHelper ubicHelper;
    private LatLng primerCheckpoint;
    private LatLng siguienteCheckpoint;
    private boolean cronometroIniciado = false;
    private long tiempoInicio;
    private TextView mensajeInicio;
    private TextView mensajeDistancia;
    private int indiceCheckpoint = 1; // Comienza desde el segundo checkpoint
    private ArrayList<LatLng> checkpoints = new ArrayList<>(); // Lista global de checkpoints
    private Juego juego;
    private String usuarioNombre;
    private String circuitoId;
    private String circuitoDificultad;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mapa);

        usuarioNombre = getIntent().getStringExtra("usuario_id");
        circuitoId = getIntent().getStringExtra("circuito_id");
        circuitoDificultad = getIntent().getStringExtra("circuito_dificultad");

        mensajeInicio = findViewById(R.id.mensajeInicio);
        mensajeDistancia = findViewById(R.id.distanceText);

        ubicHelper = new UbicacionHelper(this);
        SupportMapFragment mapF = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        if (mapF != null) mapF.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        ubicHelper.solicitarPermisoYMostrarUbicacion(mMap);

        // Leer checkpoints y nombre
        ArrayList<String> cps = getIntent().getStringArrayListExtra("CHECKPOINTS");
        String nombre = getIntent().getStringExtra("CIRCUITO_NOMBRE");
        if (cps == null || cps.isEmpty()) {
            Toast.makeText(this, "No hay puntos para mostrar", Toast.LENGTH_LONG).show();
            return;
        }

        LatLngBounds.Builder b = new LatLngBounds.Builder();

        // Parsear y llenar la lista de checkpoints
        for (String cp : cps) {
            String[] p = cp.split(",");
            try {
                double lat = Double.parseDouble(p[0].trim());
                double lng = Double.parseDouble(p[1].trim());
                LatLng coord = new LatLng(lat, lng);
                checkpoints.add(coord); // Añadir a la lista de checkpoints
                mMap.addMarker(new MarkerOptions().position(coord).title(nombre));
                b.include(coord);
            } catch (Exception ignored) {}
        }

        // Centrar cámara en el primer punto
        primerCheckpoint = checkpoints.get(0);
        siguienteCheckpoint = checkpoints.get(indiceCheckpoint); // El siguiente es el segundo
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(primerCheckpoint, 15));

        // (opcional) si quieres ajustar bounds para todos:
        // mMap.animateCamera(CameraUpdateFactory.newLatLngBounds(b.build(), 100));
    }

    @Override
    public void onRequestPermissionsResult(int req, String[] perms, int[] grants) {
        super.onRequestPermissionsResult(req, perms, grants);
        if (req == UbicacionHelper.getRequestCode()) {
            ubicHelper.mostrarUbicacion(mMap);
        }
    }

    public void verificarInicioCronometro(LatLng actual) {
        // Verificar si el cronómetro debe iniciarse en el primer checkpoint
        if (!cronometroIniciado && estaCerca(actual, primerCheckpoint)) {
            cronometroIniciado = true;
            System.out.println(circuitoId);
            //Aqui se inicia la partida necesitamos saber el usuario del inicio y que circuito se ha seleccionado
            juego = new Juego(usuarioNombre, circuitoId, circuitoDificultad);
            new Thread(() -> {
                juego.insert(); // <-- método que hace la solicitud HTTP

                runOnUiThread(() -> {
                    // Opcional: actualizar la UI si necesitas mostrar algo
                });
            }).start();

            tiempoInicio = System.currentTimeMillis();
            mensajeInicio.setVisibility(View.VISIBLE); // Mostrar mensaje

            // Ocultarlo automáticamente después de 4 segundos
            mensajeInicio.postDelayed(() -> mensajeInicio.setVisibility(View.GONE), 4000);
            System.out.println("Cronómetro iniciado");
        }

        // Calcular y mostrar la distancia al siguiente checkpoint en el nuevo TextView
        if (siguienteCheckpoint != null) {
            float[] distancia = new float[1];
            Location.distanceBetween(actual.latitude, actual.longitude, siguienteCheckpoint.latitude, siguienteCheckpoint.longitude, distancia);
            String mensaje = String.format("Distancia: %.2f m", distancia[0]);

            // Actualizar el nuevo TextView
            mensajeDistancia.setText(mensaje);
            mensajeDistancia.setVisibility(View.VISIBLE); // Asegurarse de que se muestra
        }

        // Verifica si hemos llegado al siguiente checkpoint
        if (estaCerca(actual, siguienteCheckpoint)) {
            // Hemos llegado al siguiente checkpoint, así que abre la siguiente actividad
            abrirSiguienteActividad();
        }
    }

    private boolean estaCerca(LatLng actual, LatLng objetivo) {
        float[] resultado = new float[1];
        Location.distanceBetween(
                actual.latitude, actual.longitude,
                objetivo.latitude, objetivo.longitude,
                resultado
        );
        return resultado[0] < 30; // menos de 30 metros
    }

    private void abrirSiguienteActividad() {
        // Si hay más checkpoints, actualiza el siguiente
        if (indiceCheckpoint < checkpoints.size() - 1) {
            indiceCheckpoint++; // Incrementa el índice del checkpoint
            siguienteCheckpoint = checkpoints.get(indiceCheckpoint); // Actualiza el siguiente checkpoint

            // Muestra un mensaje diciendo que has llegado al checkpoint
            Toast.makeText(MapaActivity.this, "¡Has llegado al siguiente checkpoint!", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(MapaActivity.this, EjercicioActivity.class);
            startActivity(intent);
        }

        // Si no hay más checkpoints, finalizar o realizar alguna acción adicional
        if (indiceCheckpoint >= checkpoints.size()) {
            Toast.makeText(MapaActivity.this, "¡Has completado el circuito!", Toast.LENGTH_SHORT).show();
            // Detener el cronómetro
            detenerCronometro();

            // Detener la ubicación y liberar recursos
            ubicHelper.detenerActualizaciones(); // Método que puedes agregar en tu clase UbicacionHelper para detener el seguimiento de la ubicación.

            // Abrir la siguiente actividad (EjercicioActivity)
            Intent intent = new Intent(MapaActivity.this, FinalActivity.class);
            startActivity(intent);
            finish();
        }
    }

    private void detenerCronometro() {
        // Detener el cronómetro (si es necesario)
        long tiempoFinal = System.currentTimeMillis() - tiempoInicio;
        // Aquí puedes mostrar el tiempo final o hacer alguna acción adicional
        System.out.println("Cronómetro detenido, tiempo: " + tiempoFinal);
        //Aqui terminamos la partida, basicamente actualizamos el tiempo y lo actualizamos en el back4app
        juego.setTime(tiempoFinal);
        new Thread(() -> {
            juego.actualizaJuego(); // <-- método que hace la solicitud HTTP

            runOnUiThread(() -> {
                // Opcional: actualizar la UI si necesitas mostrar algo
            });
        }).start();
    }
}
