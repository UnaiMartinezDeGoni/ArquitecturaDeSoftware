package com.example.trabajoandroid.vista;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.trabajoandroid.R;

import Bussiness.CircuitosService;
import Bussiness.modelo.Circuito;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class CircuitosActivity extends AppCompatActivity {
    private ListView listView;
    private CircuitoAdapter circuitoAdapter;
    private ArrayList<Circuito> circuitosList;
    private String usuarioNombre;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_circuitos);

        usuarioNombre = getIntent().getStringExtra("usuario_id");

        listView = findViewById(R.id.listViewCircuitos);
        String dificultad = getIntent().getStringExtra("dificultad");
        if (dificultad == null) {
            Toast.makeText(this, "No se ha recibido la dificultad", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        new ObtenerCircuitosTask().execute(dificultad);
    }

    private class ObtenerCircuitosTask extends AsyncTask<String, Void, ArrayList<Circuito>> {
        @Override
        protected ArrayList<Circuito> doInBackground(String... params) {
            CircuitosService cir = new CircuitosService();
            return cir.obtenerCircuitosPorDificultad(params[0]);
        }

        @Override
        protected void onPostExecute(ArrayList<Circuito> result) {
            if (result == null) {
                Toast.makeText(CircuitosActivity.this,
                        "Error al cargar los circuitos.", Toast.LENGTH_SHORT).show();
                return;
            }
            circuitosList = result;
            circuitoAdapter = new CircuitoAdapter(CircuitosActivity.this, circuitosList);
            listView.setAdapter(circuitoAdapter);

            // Al hacer click, pasa checkpoints y nombre al mapa
            listView.setOnItemClickListener((parent, view, position, id) -> {
                Circuito sel = circuitosList.get(position);
                Intent intent = new Intent(CircuitosActivity.this, MapaActivity.class);
                intent.putStringArrayListExtra("CHECKPOINTS",
                        new ArrayList<>(sel.getCheckpoints()));
                intent.putExtra("CIRCUITO_NOMBRE", sel.getNombre());
                intent.putExtra("usuario_id", usuarioNombre);
                System.out.println(sel.getId());
                intent.putExtra("circuito_id", sel.getId());
                intent.putExtra("circuito_dificultad", sel.getDificultad());
                intent.putStringArrayListExtra("circuito_ejs", new ArrayList<>(sel.getListaEjercicios()));
                startActivity(intent);
            });
        }
    }
}
