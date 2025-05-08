package com.example.trabajoandroid.dao;

import com.example.trabajoandroid.modelo.Imagen;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class InventarioImagen {
    private String API_URL="https://parseapi.back4app.com/classes/Imagen";;
    private final String APPLICATION_ID = "EHUFM0VK5GnLAD601JX7T3sEfDSQ4tDkEPlZFqwH";
    private final String REST_API_KEY = "eQNek326ON4ELaQdzCbRKt0dDQYSZKQlie504BWb";
    private List <Imagen> imagenes;

    public InventarioImagen() {
        this.imagenes = new ArrayList<>();
    }

    public void subirImagenes(List<File> imagenesFile, List<String> nombreImagenes){
        if(imagenesFile.size() != nombreImagenes.size()){
            System.out.println("No hay el mismo número de imagenes y de nombres");
        }
        for (int i = 0; i < imagenesFile.size(); i++){
            Imagen fileResource = upload(imagenesFile.get(i), nombreImagenes.get(i));
            imagenes.add(fileResource);
        }
    }

    public Imagen upload(File imageFile, String nameFileUpload){
        Imagen fileResource = null;
        Gson gson = new Gson();
        try {
            String url = API_URL + nameFileUpload;
            Request request = new Request.Builder()
                    .url("https://parseapi.back4app.com/files/" + nameFileUpload)
                    .addHeader("X-Parse-Application-Id", APPLICATION_ID)
                    .addHeader("X-Parse-REST-API-Key", REST_API_KEY)
                    .post(RequestBody.create(MediaType.parse("image/jpeg"), imageFile))
                    .build();
            OkHttpClient client = new OkHttpClient();
            Response response = client.newCall(request).execute();
            if(response.isSuccessful()) {
                fileResource = gson.fromJson(response.body().string(), Imagen.class);
            } else {
                System.out.println("Error al subir la imagen. Código de respuesta: " + response.code());
            }
        }
        catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return fileResource;
    }

    public List<Imagen> getImagenes() {
        return imagenes;
    }

    public ArrayList<Imagen> getAll(){
        ArrayList<Imagen> ejercicios = new ArrayList<Imagen>();
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
                Type listType = new TypeToken<ArrayList<Imagen>>() {
                }.getType();
                ejercicios = gson.fromJson(jsonArray, listType);
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return ejercicios;
    }

    public String insert(Imagen ejercicio) {
        String objectId = "";
        Gson gson = new Gson();
        String jsonEjercicio = gson.toJson(ejercicio);
        try {
            Request request = new Request.Builder()
                    .url(API_URL)
                    .addHeader("X-Parse-Application-Id", APPLICATION_ID)
                    .addHeader("X-Parse-REST-API-Key", REST_API_KEY)
                    .post(RequestBody.create(MediaType.get("application/json"), jsonEjercicio))
                    .build();
            OkHttpClient client = new OkHttpClient();
            Response response = client.newCall(request).execute();
            if (response.isSuccessful()) {
                // Obtener la respuesta como un objeto JSON
                JsonObject jsonResponse = new Gson().fromJson(response.body().string(), JsonObject.class);

                // Obtener el objectId directamente de la respuesta
                objectId = jsonResponse.get("objectId").getAsString();

                System.out.println("Insertado Ejercicio con ID: " + objectId);
            } else {
                String errorBody = response.body() != null ? response.body().string() : "Sin cuerpo de error";
                System.out.println("Error al insertar ejercicio: " + errorBody);
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return objectId;
    }
}
