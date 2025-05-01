package com.example.trabajoandroid;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.location.Location;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class UbicacionHelper {

    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1001;
    private final Activity activity;
    private final FusedLocationProviderClient fusedLocationClient;

    public UbicacionHelper(Activity activity) {
        this.activity = activity;
        this.fusedLocationClient = LocationServices.getFusedLocationProviderClient(activity);
    }

    /** Pide permiso y, si lo tienes, dibuja tu ubicación en el mapa */
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

    /** Dibuja tu última ubicación conocida en el mapa */
    public void mostrarUbicacion(GoogleMap map) {
        if (ContextCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            map.setMyLocationEnabled(true);
            fusedLocationClient.getLastLocation()
                    .addOnSuccessListener(activity, (Location location) -> {
                        if (location != null) {
                            LatLng pos = new LatLng(location.getLatitude(), location.getLongitude());
                            map.moveCamera(CameraUpdateFactory.newLatLngZoom(pos, 15));
                            map.addMarker(new MarkerOptions()
                                    .position(pos)
                                    .title("Tu ubicación"));
                        } else {
                            Toast.makeText(activity, "Ubicación no disponible", Toast.LENGTH_SHORT).show();
                        }
                    });
        }
    }

    public static int getRequestCode() {
        return LOCATION_PERMISSION_REQUEST_CODE;
    }

    public FusedLocationProviderClient getFusedLocationClient() {
        return fusedLocationClient;
    }
}
