package Bussiness.modelo;

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
    private String objectId;
    private String user;
    private String circuito;
    private String dificultad;
    private transient LocalTime horaInicio;
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



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Juego juego = (Juego) o;
        return Float.compare(time, juego.time) == 0 && Objects.equals(objectId, juego.objectId) && Objects.equals(user, juego.user) && Objects.equals(circuito, juego.circuito) && Objects.equals(dificultad, juego.dificultad) && Objects.equals(horaInicio, juego.horaInicio);
    }

    @Override
    public int hashCode() {
        return Objects.hash(objectId, user, circuito, dificultad, horaInicio, time);
    }

    public void setTime(float tiempo){
        this.time = tiempo;
    }

    public String getDificultad() {
        return dificultad;
    }
}
