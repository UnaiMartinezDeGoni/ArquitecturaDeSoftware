package com.example.trabajoandroid.vista;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.trabajoandroid.R;
import com.example.trabajoandroid.modelo.Ejercicio;

import java.util.ArrayList;
import java.util.List;

public class mostrarEjercicios extends AppCompatActivity {
    private ListView listView;
    private List<Ejercicio> ejercicios;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mostrar_ejercicios);

        ejercicios = obtenerEjercicios(); // Aquí es donde obtenemos los circuitos

        // Configurar el ListView
        listView = findViewById(R.id.listViewEjercicios);

        // Crear un adaptador con los nombres de los circuitos
        ArrayAdapter<Ejercicio> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, ejercicios);
        listView.setAdapter(adapter);

        // Establecer el evento de clic en el ListView
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parentView, View view, int position, long id) {
                // Obtener el circuito seleccionado
                Ejercicio ejercicioSeleccionado = ejercicios.get(position);

                // Iniciar la actividad con la información del circuito
                Intent intent = new Intent(mostrarEjercicios.this, detalleEjercicioActivity.class);
                intent.putExtra("ejercicio_nombre", ejercicioSeleccionado.getNombre());
                intent.putExtra("ejercicio_descripcion", ejercicioSeleccionado.getDescripcion());
                startActivity(intent);
            }
        });
    }

    public List<Ejercicio> obtenerEjercicios() {
        Ejercicio ej1 = new Ejercicio("Nombre1", "Descripcion1");
        Ejercicio ej2 = new Ejercicio("Nombre2", "Descripcion2");
        Ejercicio ej3 = new Ejercicio("Nombre3", "Descripcion3");

        List<Ejercicio> ejercicios = new ArrayList<>();
        ejercicios.add(ej1);
        ejercicios.add(ej2);
        ejercicios.add(ej3);
        return ejercicios;
    }
}