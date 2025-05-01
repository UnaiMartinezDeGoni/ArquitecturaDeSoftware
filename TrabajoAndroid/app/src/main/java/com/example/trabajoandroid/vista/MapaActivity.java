package com.example.trabajoandroid.vista;

import android.os.Bundle;
import android.widget.Toast;

import androidx.fragment.app.FragmentActivity;

import com.example.trabajoandroid.R;
import com.example.trabajoandroid.UbicacionHelper;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mapa);

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
        // Parsear y pintar cada punto
        for (String cp : cps) {
            String[] p = cp.split(",");
            try {
                double lat = Double.parseDouble(p[0].trim());
                double lng = Double.parseDouble(p[1].trim());
                LatLng coord = new LatLng(lat, lng);
                mMap.addMarker(new MarkerOptions()
                        .position(coord)
                        .title(nombre));
                b.include(coord);
            } catch (Exception ignored) {}
        }

        // Centrar cámara en el primer punto
        String[] first = cps.get(0).split(",");
        LatLng inicio = new LatLng(
                Double.parseDouble(first[0].trim()),
                Double.parseDouble(first[1].trim())
        );
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(inicio, 15));

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
}
