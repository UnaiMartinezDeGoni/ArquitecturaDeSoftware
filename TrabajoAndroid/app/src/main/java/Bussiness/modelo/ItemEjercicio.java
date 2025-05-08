package Bussiness.modelo;

import com.google.gson.Gson;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class ItemEjercicio {
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

    public float getTime() {
        return time;
    }

    public int getCantidad() {
        return cantidad;
    }

    public String getObjectId() {
        return objectId;
    }

    @Override
    public String toString() {
        return "ItemEjercicio{" + "ejercicio=" + ejercicio + ", time=" + time + ", cantidad=" + cantidad + '}';
    }
}
