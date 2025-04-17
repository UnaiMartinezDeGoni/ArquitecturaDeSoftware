package com.example.trabajoandroid.vista;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.trabajoandroid.R;
import com.example.trabajoandroid.modelo.Circuito;
import com.example.trabajoandroid.modelo.Ejercicio;
import com.example.trabajoandroid.modelo.ItemEjercicio;

import java.util.ArrayList;
import java.util.List;

public class mostrarCircuitos extends AppCompatActivity {
    private ListView listView;
    private List<Circuito> circuitos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mostrar_circuitos);

        circuitos = obtenerCircuitos(); // Aquí es donde obtenemos los circuitos

        // Configurar el ListView
        listView = findViewById(R.id.listViewCircuitos);

        // Crear un adaptador con los nombres de los circuitos
        ArrayAdapter<Circuito> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, circuitos);
        listView.setAdapter(adapter);

        // Establecer el evento de clic en el ListView
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parentView, View view, int position, long id) {
                // Obtener el circuito seleccionado
                Circuito circuitoSeleccionado = circuitos.get(position);

                // Iniciar la actividad con la información del circuito
                Intent intent = new Intent(mostrarCircuitos.this, detalleCircuitoActivity.class);
                intent.putExtra("circuito_nombre", circuitoSeleccionado.getNombre());
                intent.putExtra("circuito_dificultad", circuitoSeleccionado.getDificultad());
                startActivity(intent);
            }
        });
    }

    public List<Circuito> obtenerCircuitos(){
        Ejercicio ej1 = new Ejercicio("Nombre1", "Descripcion1");
        Ejercicio ej2 = new Ejercicio("Nombre2", "Descripcion2");
        Ejercicio ej3 = new Ejercicio("Nombre3", "Descripcion3");

        ItemEjercicio iEj1 = new ItemEjercicio(ej1.getId(), 10);
        ItemEjercicio iEj2 = new ItemEjercicio(ej2.getId(), 15);
        ItemEjercicio iEj3 = new ItemEjercicio(ej3.getId(), 12);

        List<String> ejs = new ArrayList<>();
        ejs.add(iEj1.getObjectId());
        ejs.add(iEj2.getObjectId());
        ejs.add(iEj3.getObjectId());

        List<String> checkpoints = new ArrayList<>();
        checkpoints.add("Arbol");
        checkpoints.add("Banco");
        checkpoints.add("Farola");

        Circuito cir1 = new Circuito( "Parque1", "Facil", checkpoints, ejs);
        Circuito cir2 = new Circuito("Parque1", "Medio", checkpoints, ejs);
        Circuito cir3 = new Circuito("Parque1", "Dificil", checkpoints, ejs);

        List<Circuito> circuitos = new ArrayList<>();
        circuitos.add(cir1);
        circuitos.add(cir2);
        circuitos.add(cir3);
        return circuitos;
    }
}