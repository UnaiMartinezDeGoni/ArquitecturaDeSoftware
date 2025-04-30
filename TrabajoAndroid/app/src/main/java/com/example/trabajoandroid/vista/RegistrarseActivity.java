package com.example.trabajoandroid.vista;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.trabajoandroid.R;
import com.example.trabajoandroid.dao.InventarioUsuario;
import com.example.trabajoandroid.modelo.Usuario;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class RegistrarseActivity extends AppCompatActivity {
    private EditText editTextUsername, editTextEmail, editTextPassword;
    private Button buttonRegister;
    private InventarioUsuario inventarioUsuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrarse);

        // Inicializar las vistas
        editTextUsername = findViewById(R.id.editTextUsername);
        editTextEmail = findViewById(R.id.editTextEmail);
        editTextPassword = findViewById(R.id.editTextPassword);
        buttonRegister = findViewById(R.id.buttonRegister);

        // Instancia del DAO para interactuar con Back4App
        inventarioUsuario = new InventarioUsuario();

        // Acción para el botón de registro
        buttonRegister.setOnClickListener(v -> register());

    }

    private void register() {
        String username = editTextUsername.getText().toString();
        String email = editTextEmail.getText().toString();
        String password = editTextPassword.getText().toString();

        // Verificar que los campos no estén vacíos
        if (username.isEmpty() || email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Por favor, complete todos los campos", Toast.LENGTH_SHORT).show();
            return;
        }

        // Verificar que el nombre de usuario no esté ya registrado
        new Thread(() -> {
            try {
                // Llamar al método para verificar si el nombre de usuario ya existe
                boolean usuarioExistente = verificarUsuarioExistente(username);

                if (usuarioExistente) {
                    runOnUiThread(() -> Toast.makeText(RegistrarseActivity.this, "El nombre de usuario ya está en uso", Toast.LENGTH_SHORT).show());
                } else {
                    // Crear un nuevo usuario
                    Usuario newUser = new Usuario(username, email, password, new ArrayList<>());

                    // Insertar el nuevo usuario en Back4App
                    String objectId = inventarioUsuario.insert(newUser);

                    // Si el usuario se creó correctamente, mostrar un mensaje
                    if (!objectId.isEmpty()) {
                        runOnUiThread(() -> Toast.makeText(RegistrarseActivity.this, "Registro exitoso", Toast.LENGTH_SHORT).show());
                        Intent intent = new Intent(RegistrarseActivity.this, HomeActivity.class);
                        startActivity(intent);  // Lanza la nueva actividad
                        finish();  // Finaliza la actividad de registro para que no se pueda regresar a ella
                    } else {
                        runOnUiThread(() -> Toast.makeText(RegistrarseActivity.this, "Error al registrar usuario", Toast.LENGTH_SHORT).show());
                    }
                }
            } catch (Exception e) {
                runOnUiThread(() -> Toast.makeText(RegistrarseActivity.this, "Error al conectar con el servidor", Toast.LENGTH_SHORT).show());
            }
        }).start();
    }

    private boolean verificarUsuarioExistente(String username) {
        // Comprobar si el nombre de usuario ya existe
        // En este caso, se hace una consulta a Back4App buscando el nombre de usuario
        String url = "https://parseapi.back4app.com/classes/Usuario?where={\"nombre\":\"" + username + "\"}";

        try {
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                    .url(url)
                    .addHeader("X-Parse-Application-Id", "EHUFM0VK5GnLAD601JX7T3sEfDSQ4tDkEPlZFqwH")
                    .addHeader("X-Parse-REST-API-Key", "eQNek326ON4ELaQdzCbRKt0dDQYSZKQlie504BWb")
                    .get()
                    .build();
            Response response = client.newCall(request).execute();

            if (response.isSuccessful()) {
                // Convertir el cuerpo de la respuesta a un string
                String responseBody = response.body().string();

                // Usamos Gson para analizar la respuesta JSON
                JsonObject jsonResponse = JsonParser.parseString(responseBody).getAsJsonObject();
                JsonArray results = jsonResponse.getAsJsonArray("results");

                // Si el array "results" está vacío, no hay usuario
                return results.size() > 0;  // Si hay elementos en "results", el usuario ya existe
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return false; // De forma predeterminada, asumimos que el usuario no existe
    }
}