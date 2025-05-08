package com.example.trabajoandroid.vista;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.example.trabajoandroid.R;

import Bussiness.UsuarioService;
import DAO.dao.InventarioUsuario;
import Bussiness.modelo.Usuario;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class LogInActivity extends AppCompatActivity {
    private EditText usernameEditText, passwordEditText;
    private Button loginButton, registerButton;
    private InventarioUsuario inventarioUsuario;

    private static final String TAG = "LogInActivity"; // Para agregar logs

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        // Vincula las vistas con los elementos de la interfaz
        usernameEditText = findViewById(R.id.usernameEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        loginButton = findViewById(R.id.loginButton);
        registerButton = findViewById(R.id.registerButton);

        // Instancia del DAO para interactuar con Back4App
        inventarioUsuario = new InventarioUsuario();

        // Acción para el botón de login
        loginButton.setOnClickListener(v -> login());

        // Acción para el texto de registro
        registerButton.setOnClickListener(v -> {
            Intent intent = new Intent(LogInActivity.this, RegistrarseActivity.class);
            startActivity(intent);
        });
    }

    private void login() {
        String username = usernameEditText.getText().toString();
        String password = passwordEditText.getText().toString();

        if (username.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Por favor, complete todos los campos", Toast.LENGTH_SHORT).show();
            return;
        }

        new Thread(() -> {
            try {
                UsuarioService user = new UsuarioService();
                Usuario usuario = user.verificarLogin(username, password);

                if (usuario != null) {
                    runOnUiThread(() -> {
                        Toast.makeText(LogInActivity.this, "Inicio de sesión exitoso", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(LogInActivity.this, HomeActivity.class);
                        intent.putExtra("usuario_id", usuario.getId());
                        startActivity(intent);
                        finish();
                    });
                } else {
                    runOnUiThread(() -> Toast.makeText(LogInActivity.this, "Credenciales incorrectas", Toast.LENGTH_SHORT).show());
                }
            } catch (Exception e) {
                e.printStackTrace();
                runOnUiThread(() -> Toast.makeText(LogInActivity.this, "Error de conexión", Toast.LENGTH_SHORT).show());
            }
        }).start();
    }


}
