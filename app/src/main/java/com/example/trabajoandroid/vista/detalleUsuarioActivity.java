package com.example.trabajoandroid.vista;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.trabajoandroid.R;

public class detalleUsuarioActivity extends AppCompatActivity {
    private TextView nombreTextView, correoTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_usuario);

        nombreTextView = findViewById(R.id.nombreUsuarioDetalle);
        correoTextView = findViewById(R.id.correoUsuarioDetalle);

        // Obtener los datos pasados a través de la Intent
        String nombre = getIntent().getStringExtra("usuario_nombre");
        String correo = getIntent().getStringExtra("usuario_correo");

        // Mostrar los detalles
        nombreTextView.setText(nombre);
        correoTextView.setText(correo);
    }
}