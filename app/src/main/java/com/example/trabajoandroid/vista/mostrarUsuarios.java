package com.example.trabajoandroid.vista;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.trabajoandroid.R;
import com.example.trabajoandroid.modelo.Usuario;
import com.example.trabajoandroid.modelo.Juego;

import java.util.ArrayList;
import java.util.List;

public class mostrarUsuarios extends AppCompatActivity {
    private ListView listView;
    private List<Usuario> usuarios;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mostrar_usuarios);

        usuarios = obtenerUsuarios(); // Aquí es donde obtenemos los circuitos

        // Configurar el ListView
        listView = findViewById(R.id.listViewUsuarios);

        // Crear un adaptador con los nombres de los circuitos
        ArrayAdapter<Usuario> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, usuarios);
        listView.setAdapter(adapter);

        // Establecer el evento de clic en el ListView
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parentView, View view, int position, long id) {
                // Obtener el circuito seleccionado
                Usuario usuarioSeleccionado = usuarios.get(position);

                // Iniciar la actividad con la información del circuito
                Intent intent = new Intent(mostrarUsuarios.this, detalleUsuarioActivity.class);
                intent.putExtra("usuario_nombre", usuarioSeleccionado.getNombre());
                intent.putExtra("usuario_correo", usuarioSeleccionado.getCorreo());
                startActivity(intent);
            }
        });
    }

    public List<Usuario> obtenerUsuarios() {
        List<String> juegos = new ArrayList<>();
        Usuario user = new Usuario("Leyre", "leyre@correo.com", "ghsrtfg", juegos);
        Usuario user1 = new Usuario("Ainhoa", "ainhoa@correo.com", "fbdfgtht", juegos);

        List<Usuario> usuarios = new ArrayList<>();
        usuarios.add(user);
        usuarios.add(user1);
        return usuarios;
    }
}