package com.example.trabajoandroid.vista;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.fragment.app.FragmentActivity;

import com.example.trabajoandroid.R;
import com.example.trabajoandroid.UbicacionHelper;
import com.example.trabajoandroid.CircuitoMapaHelper;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;

import java.util.List;

public class MapaActivity extends FragmentActivity implements OnMapReadyCallback {
    private GoogleMap mMap;
    private UbicacionHelper ubicacionHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mapa);

        ubicacionHelper = new UbicacionHelper(this);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        ubicacionHelper.solicitarPermisoYMostrarUbicacion(mMap);

        int idx = getIntent().getIntExtra("CIRCUITO_INDEX", -1);
        if (idx != -1) {
            CircuitoMapaHelper.cargarCircuitoEnMapa(idx, new CircuitoMapaHelper.Callback() {
                @Override
                public void onCircuitoLoaded(List<LatLng> puntos, List<String> titulos) {
                    // 1) Pintamos los marcadores
                    CircuitoMapaHelper.mostrarPuntosEnMapa(mMap, puntos, titulos);

                    // 2) Reubicamos la cámara en el primer punto
                    if (!puntos.isEmpty()) {
                        LatLng primerPunto = puntos.get(0);
                        // mover la cámara con un zoom de 15 (ajústalo a tu gusto)
                        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(primerPunto, 15));
                    }
                }
                @Override
                public void onError(String mensaje) {
                    Toast.makeText(MapaActivity.this, mensaje, Toast.LENGTH_LONG).show();
                }
            });
        }
    }



    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == UbicacionHelper.getRequestCode()) {
            ubicacionHelper.mostrarUbicacion(mMap);
        }
    }
}
