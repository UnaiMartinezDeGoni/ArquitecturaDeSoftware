package com.example.trabajoandroid.vista;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.trabajoandroid.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button circuitoButton = findViewById(R.id.circuitoButton);
        Button ejercicioButton = findViewById(R.id.ejercicioButton);
        Button usuarioButton = findViewById(R.id.usuarioButton);

        // Configurar los listeners para los botones
        circuitoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Iniciar la actividad para mostrar circuitos
                Intent intent = new Intent(MainActivity.this, mostrarCircuitos.class);
                startActivity(intent);
            }
        });

        ejercicioButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Iniciar la actividad para mostrar circuitos
                Intent intent = new Intent(MainActivity.this, mostrarEjercicios.class);
                startActivity(intent);
            }
        });

        usuarioButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Iniciar la actividad para mostrar circuitos
                Intent intent = new Intent(MainActivity.this, mostrarUsuarios.class);
                startActivity(intent);
            }
        });
    }
}