package com.example.trabajoandroid.vista;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.trabajoandroid.R;

public class detalleCircuitoActivity extends AppCompatActivity {
    private TextView nombreTextView, dificultadTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_circuito);

        nombreTextView = findViewById(R.id.nombreCircuitoDetalle);
        dificultadTextView = findViewById(R.id.dificultadCircuitoDetalle);

        // Obtener los datos pasados a través de la Intent
        String nombre = getIntent().getStringExtra("circuito_nombre");
        String dificultad = getIntent().getStringExtra("circuito_dificultad");

        // Mostrar los detalles
        nombreTextView.setText(nombre);
        dificultadTextView.setText(dificultad);
    }
}