/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package juego.modelos;

import com.google.gson.Gson;
import java.io.IOException;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 *
 * @author alumno
 */
public class ItemEjercicio { 
    private final String API_URL = "https://parseapi.back4app.com/classes/ItemEjercicio";
    private final String APPLICATION_ID ="EHUFM0VK5GnLAD601JX7T3sEfDSQ4tDkEPlZFqwH";
    private final String REST_API_KEY ="eQNek326ON4ELaQdzCbRKt0dDQYSZKQlie504BWb";
    
/*Los ejercicios de la clase Ejercicio indicando la cantidad o el tiempo que se 
haran para cada circuito teniendo en cuenta que esto puede cambiar dependiendo 
del circuito o la dificultad*/
    private String ejercicio;
    private float time;
    private int cantidad;
    private String objectId;
    private String circuito;

    public ItemEjercicio(String ejercicio, float time) {
        this.ejercicio = ejercicio;
        this.time = time;
    }

    public ItemEjercicio(String ejercicio, int cantidad) {
        this.ejercicio = ejercicio;
        this.cantidad = cantidad;
    }

    public String getEjercicio() {
        return ejercicio;
    }
    
    public void setCircuito(String cir) {
        this.circuito = cir;
    }
    
    public String getId() {
        return objectId;
    }

    public float getTime() {
        return time;
    }

    public int getCantidad() {
        return cantidad;
    }

    @Override
    public String toString() {
        return "ItemEjercicio{" + "ejercicio=" + ejercicio + ", time=" + time + ", cantidad=" + cantidad + '}';
    }
    
    public void insert() {
        Gson gson = new Gson();
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
                ItemEjercicio newJuego = gson.fromJson(response.body().string(), ItemEjercicio.class);
                this.objectId = newJuego.objectId;
                System.out.println("Juego insertado con ID: " + objectId);
            } else {
                System.out.println("Error al insertar el juego. Código de respuesta: " + response.code());
            }
        } catch (IOException e) {
            System.out.println("Error al insertar el juego: " + e.getMessage());
        }
    }
}
