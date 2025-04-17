package com.example.trabajoandroid.vista;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.trabajoandroid.R;

public class detalleEjercicioActivity extends AppCompatActivity {
    private TextView nombreTextView, descripcionTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_ejercicio);

        nombreTextView = findViewById(R.id.nombreEjercicioDetalle);
        descripcionTextView = findViewById(R.id.descripcionEjercicioDetalle);

        // Obtener los datos pasados a través de la Intent
        String nombre = getIntent().getStringExtra("ejercicio_nombre");
        String descripcion = getIntent().getStringExtra("ejercicio_descripcion");

        // Mostrar los detalles
        nombreTextView.setText(nombre);
        descripcionTextView.setText(descripcion);
    }
}