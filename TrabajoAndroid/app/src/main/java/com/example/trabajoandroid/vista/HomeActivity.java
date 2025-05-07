package com.example.trabajoandroid.vista;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.trabajoandroid.R;

public class HomeActivity extends AppCompatActivity {
    private String usuarioNombre;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        usuarioNombre = getIntent().getStringExtra("usuario_id");

        Button btnFacil = findViewById(R.id.btnFacil);
        Button btnMedio = findViewById(R.id.btnMedio);
        Button btnDificil = findViewById(R.id.btnDificil);
        Button btnTodos = findViewById(R.id.btnTodos);
        Button btnMisJuegos = findViewById(R.id.btnMisJuegos);

        btnFacil.setOnClickListener(v -> navigateToCircuitos("Facil"));
        btnMedio.setOnClickListener(v -> navigateToCircuitos("Medio"));
        btnDificil.setOnClickListener(v -> navigateToCircuitos("Dificil"));
        btnTodos.setOnClickListener(v -> navigateToCircuitos("Todos"));
        btnMisJuegos.setOnClickListener(v -> {
            Intent intent = new Intent(HomeActivity.this, MisJuegosActivity.class);
            intent.putExtra("usuario", usuarioNombre);
            startActivity(intent);
        });

    }

    private void navigateToCircuitos(String dificultad) {
        Intent intent = new Intent(HomeActivity.this, CircuitosActivity.class);
        intent.putExtra("dificultad", dificultad);
        intent.putExtra("usuario_id", usuarioNombre);
        startActivity(intent);
    }
}