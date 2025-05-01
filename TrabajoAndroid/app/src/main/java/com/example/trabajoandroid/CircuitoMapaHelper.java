package com.example.trabajoandroid;

import android.os.AsyncTask;
import android.widget.Toast;

import com.example.trabajoandroid.dao.InventarioCircuitos;
import com.example.trabajoandroid.modelo.Circuito;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;


public class CircuitoMapaHelper {

    public interface Callback {
        void onCircuitoLoaded(List<LatLng> puntos, List<String> titulos);
        void onError(String mensaje);
    }

    /**
     * Carga en background el circuito con el ID dado (índice en tu listado)
     * y devuelve listas de LatLng y títulos de cada punto vía callback.
     */
    public static void cargarCircuitoEnMapa(int circuitoIndex, Callback cb) {
        new AsyncTask<Void, Void, Circuito>() {
            private Exception error;

            @Override
            protected Circuito doInBackground(Void... voids) {
                try {
                    InventarioCircuitos inv = new InventarioCircuitos();
                    // obtienes la lista completa y tomas el índice
                    List<Circuito> todos = inv.getAll();
                    return (circuitoIndex >= 0 && circuitoIndex < todos.size())
                            ? todos.get(circuitoIndex)
                            : null;
                } catch (Exception e) {
                    error = e;
                    return null;
                }
            }

            @Override
            protected void onPostExecute(Circuito circuito) {
                if (error != null) {
                    cb.onError("Error al cargar circuito: " + error.getMessage());
                    return;
                }
                if (circuito == null) {
                    cb.onError("Circuito no encontrado");
                    return;
                }
                List<LatLng> puntos = new ArrayList<>();
                List<String> titulos = new ArrayList<>();

                // Cada checkpoint está como "lat,lng"
                for (String cp : circuito.getCheckpoints()) {
                    try {
                        String[] parts = cp.split(",");
                        double lat = Double.parseDouble(parts[0].trim());
                        double lng = Double.parseDouble(parts[1].trim());
                        puntos.add(new LatLng(lat, lng));
                        titulos.add(circuito.getNombre()); // o usa cp como título
                    } catch (Exception ex) {
                        // skip malformed
                    }
                }
                cb.onCircuitoLoaded(puntos, titulos);
            }
        }.execute();
    }

    /**
     * Agrega al mapa marcadores en cada LatLng con su título correspondiente.
     */
    public static void mostrarPuntosEnMapa(GoogleMap map, List<LatLng> puntos, List<String> titulos) {
        for (int i = 0; i < puntos.size(); i++) {
            map.addMarker(new MarkerOptions()
                    .position(puntos.get(i))
                    .title(titulos.get(i))
            );
        }
    }
}
