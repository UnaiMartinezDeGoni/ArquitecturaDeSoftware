package com.example.trabajoandroid.dao;

import com.example.trabajoandroid.modelo.Circuito;
import com.example.trabajoandroid.modelo.CircuitoItemEjercicio;
import com.example.trabajoandroid.modelo.EjercicioItemEjercicio;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class InventarioEjercicioItemEjercicio {
    private final String API_URL = "https://parseapi.back4app.com/classes/EjercicioItemEjercicio";
    private final String APPLICATION_ID ="EHUFM0VK5GnLAD601JX7T3sEfDSQ4tDkEPlZFqwH";
    private final String REST_API_KEY ="eQNek326ON4ELaQdzCbRKt0dDQYSZKQlie504BWb";

    public ArrayList<EjercicioItemEjercicio> getAll(){
        ArrayList<EjercicioItemEjercicio> proveedores = new ArrayList<EjercicioItemEjercicio>();
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
                Gson gson = new Gson();
                Type listType = new TypeToken<ArrayList<Circuito>>() {
                }.getType();
                proveedores = gson.fromJson(jsonArray, listType);
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return proveedores;
    }

    public String insert(EjercicioItemEjercicio ejercicioItemEjercicio) {
        String objectId = "";
        Gson gson = new Gson();
        String jsonCircuito = gson.toJson(ejercicioItemEjercicio);  // Convertimos el objeto Circuito a JSON

        try {
            // Realizamos la solicitud POST para insertar el Circuito
            Request request = new Request.Builder()
                    .url(API_URL)
                    .addHeader("X-Parse-Application-Id", APPLICATION_ID)
                    .addHeader("X-Parse-REST-API-Key", REST_API_KEY)
                    .addHeader("Content-Type", "application/json")
                    .post(RequestBody.create(MediaType.get("application/json"), jsonCircuito))
                    .build();

            OkHttpClient client = new OkHttpClient();
            Response response = client.newCall(request).execute();

            if (response.isSuccessful()) {
                // Parseamos la respuesta de la API para obtener el objectId del nuevo circuito
                EjercicioItemEjercicio newCircuito = gson.fromJson(response.body().string(), EjercicioItemEjercicio.class);
                objectId = String.valueOf(newCircuito.getId());
                System.out.println("Circuito insertado con objectId: " + objectId);
            } else {
                System.out.println("Error al insertar el circuito: " + response.message());
            }
        } catch (IOException e) {
            System.out.println("Error en la inserción: " + e.getMessage());
        }
        return objectId;
    }

    public void actualizaEjercicioItemEjercicio(EjercicioItemEjercicio ejercicioItemEjercicio) {
        Gson gson = new Gson();
        String jsonCircuito = gson.toJson(ejercicioItemEjercicio);

        try {
            // Crear la URL con el objectId del circuito que quieres actualizar
            String url = API_URL + "/" + ejercicioItemEjercicio.getId();

            // Crear la solicitud PUT
            Request request = new Request.Builder()
                    .url(url)
                    .addHeader("X-Parse-Application-Id", APPLICATION_ID)
                    .addHeader("X-Parse-REST-API-Key", REST_API_KEY)
                    .addHeader("Content-Type", "application/json")
                    .put(RequestBody.create(MediaType.get("application/json"), jsonCircuito)) // Usamos PUT para actualizar
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
}
