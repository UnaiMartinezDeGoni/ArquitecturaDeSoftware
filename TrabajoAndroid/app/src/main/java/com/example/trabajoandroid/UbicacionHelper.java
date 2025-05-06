package com.example.trabajoandroid;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.location.Location;
import com.google.android.gms.location.LocationRequest;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class UbicacionHelper {
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1001;
    private final Activity activity;
    private final FusedLocationProviderClient fusedLocationClient;
    private LocationCallback locationCallback;
    private LocationRequest locationRequest;
    private GoogleMap mMap;

    public UbicacionHelper(Activity activity) {
        this.activity = activity;
        this.fusedLocationClient = LocationServices.getFusedLocationProviderClient(activity);
    }

    public void solicitarPermisoYMostrarUbicacion(GoogleMap map) {
        if (ContextCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(activity,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    LOCATION_PERMISSION_REQUEST_CODE);
        } else {
            mostrarUbicacion(map);
        }
    }

    public void mostrarUbicacion(GoogleMap map) {
        if (ContextCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {

            mMap = map;
            map.setMyLocationEnabled(true);

            // Configurar request para actualizaciones en tiempo real
            locationRequest = LocationRequest.create();
            locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
            locationRequest.setInterval(5000); // cada 5 segundos
            locationRequest.setFastestInterval(2000); // mínimo 2 segundos

            // Callback que se ejecuta con cada nueva ubicación
            locationCallback = new LocationCallback() {
                @Override
                public void onLocationResult(LocationResult locationResult) {
                    for (Location location : locationResult.getLocations()) {
                        if (location != null) {
                            LatLng actual = new LatLng(location.getLatitude(), location.getLongitude());

                            // Enviar a MapaActivity para verificar si llegó al primer checkpoint
                            if (activity instanceof com.example.trabajoandroid.vista.MapaActivity) {
                                ((com.example.trabajoandroid.vista.MapaActivity) activity)
                                        .verificarInicioCronometro(actual);
                            }

                            // (opcional) mover cámara o mostrar marker
                            // mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(actual, 15));
                        }
                    }
                }
            };

            // Iniciar actualizaciones
            fusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, null);
        }
    }

    public void detenerActualizaciones() {
        if (locationCallback != null) {
            fusedLocationClient.removeLocationUpdates(locationCallback);
        }
    }

    public static int getRequestCode() {
        return LOCATION_PERMISSION_REQUEST_CODE;
    }
}
