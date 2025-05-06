package com.example.trabajoandroid.modelo;

import com.example.trabajoandroid.LocalTimeAdapter;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.time.Duration;
import java.time.LocalTime;
import java.util.Objects;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class Juego {//habria que pasar el juego al dao o añadir un inventario de juegos al dao
    private final String API_URL = "https://parseapi.back4app.com/classes/Juego";
    private final String APPLICATION_ID ="EHUFM0VK5GnLAD601JX7T3sEfDSQ4tDkEPlZFqwH";
    private final String REST_API_KEY ="eQNek326ON4ELaQdzCbRKt0dDQYSZKQlie504BWb";

    private String objectId;
    private String user;
    private String circuito;
    private String dificultad;
    private LocalTime horaInicio;
    private float time; //El tiempo se añade al final del juego, pero el juego se crea cuando un usario selecciona un circuito

    public Juego(String user, String circuito, String dificultad) {
        this.user = user;
        this.circuito = circuito;
        this.dificultad = dificultad;
        this.time = 0;
        this.horaInicio = LocalTime.now();
        System.out.println(horaInicio);
    }

    public String getId() {
        return objectId;
    }

    public String getCircuito() {
        return circuito;
    }

    public float getTime() {
        return time;
    }

    public void actualizarTiempo(){
        System.out.println(LocalTime.now());
        long segundos = Duration.between(horaInicio, LocalTime.now()).getSeconds();
        this.time = segundos / 60f;//Calculamos el tiempo que el usuario ha tardado en terminar el juego
        System.out.println(time);
    }

    @Override
    public String toString() {
        return "Juego{" + "id=" + objectId + ", circuito=" + circuito + ", " + dificultad + ", time=" + time + '}';
    }

    public void insert() {
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(LocalTime.class, new LocalTimeAdapter())
                .create();
        String jsonJuego = gson.toJson(this);

        try {
            Request request = new Request.Builder()
                    .url(API_URL) // URL de la clase Juego en Back4App
                    .addHeader("X-Parse-Application-Id", APPLICATION_ID)
                    .addHeader("X-Parse-REST-API-Key", REST_API_KEY)
                    .post(RequestBody.create(MediaType.get("application/json"), jsonJuego))
                    .build();

            OkHttpClient client = new OkHttpClient();
            Response response = client.newCall(request).execute();

            if(response.isSuccessful()) {
                Juego newJuego = gson.fromJson(response.body().string(), Juego.class);
                this.objectId = newJuego.objectId;
                System.out.println("Juego insertado con ID: " + objectId);
            } else {
                System.out.println("Error al insertar el juego. Código de respuesta: " + response.code());
            }
        } catch (IOException e) {
            System.out.println("Error al insertar el juego: " + e.getMessage());
        }
    }

    public void actualizaJuego() {
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(LocalTime.class, new LocalTimeAdapter())
                .create();
        String jsonUsuario = gson.toJson(this);

        try {
            // Crear la URL con el objectId del circuito que quieres actualizar
            String url = API_URL + "/" + this.getId();

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Juego juego = (Juego) o;
        return Float.compare(time, juego.time) == 0 && Objects.equals(API_URL, juego.API_URL) && Objects.equals(APPLICATION_ID, juego.APPLICATION_ID) && Objects.equals(REST_API_KEY, juego.REST_API_KEY) && Objects.equals(objectId, juego.objectId) && Objects.equals(user, juego.user) && Objects.equals(circuito, juego.circuito) && Objects.equals(dificultad, juego.dificultad) && Objects.equals(horaInicio, juego.horaInicio);
    }

    @Override
    public int hashCode() {
        return Objects.hash(API_URL, APPLICATION_ID, REST_API_KEY, objectId, user, circuito, dificultad, horaInicio, time);
    }

    public void setTime(float tiempo){
        this.time = tiempo;
    }
}
