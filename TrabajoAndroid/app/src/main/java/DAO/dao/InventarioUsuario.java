/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO.dao;

import static androidx.camera.core.CameraXThreads.TAG;

import android.util.Log;

import com.example.trabajoandroid.LocalTimeAdapter;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import java.io.IOException;
import java.lang.reflect.Type;
import java.time.LocalTime;
import java.util.ArrayList;

import Bussiness.modelo.Usuario;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 *
 * @author alumno
 */
public class InventarioUsuario implements InventarioUsuarioInterfaz{
    private final String API_URL = "https://parseapi.back4app.com/classes/Usuario";
    private final String APPLICATION_ID ="EHUFM0VK5GnLAD601JX7T3sEfDSQ4tDkEPlZFqwH";
    private final String REST_API_KEY ="eQNek326ON4ELaQdzCbRKt0dDQYSZKQlie504BWb";

    @Override
    public ArrayList<Usuario> getAll(){
        ArrayList<Usuario> usuarios = new ArrayList<Usuario>();
        try{
            //sincrono
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                    .url(API_URL)
                    .addHeader("X-Parse-Application-Id", APPLICATION_ID)
                    .addHeader("X-Parse-REST-API-Key", REST_API_KEY)
                    .addHeader("Content-Type", "application/json")
                    .get()
                    .build();
            Response response = client.newCall(request).execute();
            if(response.code() == 200) {
                String responseJson = response.body().string();
                System.out.println(responseJson);
                JsonObject jsonObject = new Gson().fromJson(responseJson, JsonObject.class);
                JsonArray jsonArray = jsonObject.getAsJsonArray("results");
                Gson gson = new GsonBuilder()
                        .registerTypeAdapter(LocalTime.class, new LocalTimeAdapter())
                        .create();
                Type listType = new TypeToken<ArrayList<Usuario>>() {
                }.getType();
                usuarios = gson.fromJson(jsonArray, listType);
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return usuarios;
    }

    @Override
    public String insert(Usuario usuario){
        String objectId="";
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(LocalTime.class, new LocalTimeAdapter())
                .create();
        String jsonUsuario = gson.toJson(usuario);
        try {
            Request request = new Request.Builder()
                    .url(API_URL)
                    .addHeader("X-Parse-Application-Id", APPLICATION_ID)
                    .addHeader("X-Parse-REST-API-Key", REST_API_KEY)
                    .post(RequestBody.create(MediaType.get("application/json"),jsonUsuario))
                    .build();
            OkHttpClient client = new OkHttpClient();
            Response response = client.newCall(request).execute();
            if(response.isSuccessful()) {
                Usuario newUsuario = gson.fromJson(response.body().string(), Usuario.class);
                objectId = String.valueOf(newUsuario.getId());
                System.out.println("insert" + objectId);
            }
        }
        catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return objectId;
    }

    @Override
    public void actualizaUsuario(Usuario user) {
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(LocalTime.class, new LocalTimeAdapter())
                .create();
        String jsonUsuario = gson.toJson(user);

        try {
            // Crear la URL con el objectId del circuito que quieres actualizar
            String url = API_URL + "/" + user.getId();

            // Crear la solicitud PUT
            Request request = new Request.Builder()
                    .url(url)
                    .addHeader("X-Parse-Application-Id", APPLICATION_ID)
                    .addHeader("X-Parse-REST-API-Key", REST_API_KEY)
                    .addHeader("Content-Type", "application/json")
                    .put(RequestBody.create(MediaType.get("application/json"), jsonUsuario)) // Usamos PUT para actualizar
                    .build();

            OkHttpClient client = new OkHttpClient();
            Response response = client.newCall(request).execute();

            if (response.isSuccessful()) {
                // Si la actualización fue exitosa, puedes obtener la respuesta (si es necesario)
                System.out.println("Circuito actualizado exitosamente.");
            } else {
                // Si la actualización falla, puedes manejarlo aquí
                System.out.println("Error al actualizar el circuito: " + response.message());
            }
        } catch (IOException e) {
            // Manejo de excepciones
            System.out.println(e.getMessage());
        }
    }

    public Usuario getUsuario(int seleccionado) {
        ArrayList<Usuario> usuarios = getAll();
        return usuarios.get(seleccionado);
    }

    public Usuario verificarLogin(String username, String password) throws IOException {
        String url = "https://parseapi.back4app.com/classes/Usuario?where={\"nombre\":\"" + username + "\"}";

        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url)
                .addHeader("X-Parse-Application-Id", "EHUFM0VK5GnLAD601JX7T3sEfDSQ4tDkEPlZFqwH")
                .addHeader("X-Parse-REST-API-Key", "eQNek326ON4ELaQdzCbRKt0dDQYSZKQlie504BWb")
                .get()
                .build();

        Log.d("Verificando log in", "Haciendo solicitud a la URL: " + url); // Log para depurar URL

        Response response = client.newCall(request).execute();

        if (response.isSuccessful()) {
            String responseBody = response.body().string();
            Log.d("Verificando log in", "Respuesta del servidor: " + responseBody); // Log para depurar la respuesta

            JsonObject json = JsonParser.parseString(responseBody).getAsJsonObject();
            JsonArray results = json.getAsJsonArray("results");

            if (results.size() > 0) {
                JsonObject userJson = results.get(0).getAsJsonObject();
                Usuario usuario = new Gson().fromJson(userJson, Usuario.class);

                // Comparar la contraseña correctamente
                if (usuario.getPasswd().equals(password)) {
                    return usuario;
                }
            }
        } else {
            Log.d("Verificando log in", "Error en la respuesta: " + response.message()); // Log de error
        }

        return null;
    }

    public boolean verificarUsuarioExistente(String username) {
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
