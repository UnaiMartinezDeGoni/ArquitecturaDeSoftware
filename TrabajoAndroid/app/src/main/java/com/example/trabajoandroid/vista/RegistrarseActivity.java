package com.example.trabajoandroid.vista;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.trabajoandroid.R;

import Bussiness.UsuarioService;
import DAO.dao.InventarioUsuario;
import Bussiness.modelo.Usuario;
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
                UsuarioService user = new UsuarioService();
                boolean usuarioExistente = user.verificarUsuarioExistente(username);

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
                        intent.putExtra("usuario_id", newUser.getId());
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


}