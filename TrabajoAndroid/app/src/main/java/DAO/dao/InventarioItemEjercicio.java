package DAO.dao;

import com.example.trabajoandroid.LocalTimeAdapter;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.time.LocalTime;

import Bussiness.modelo.ItemEjercicio;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class InventarioItemEjercicio {
    private final String API_URL = "https://parseapi.back4app.com/classes/ItemEjercicio";
    private final String APPLICATION_ID ="EHUFM0VK5GnLAD601JX7T3sEfDSQ4tDkEPlZFqwH";
    private final String REST_API_KEY ="eQNek326ON4ELaQdzCbRKt0dDQYSZKQlie504BWb";

    public String insert(ItemEjercicio juego) {
        String objectId="";
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(LocalTime.class, new LocalTimeAdapter())
                .create();
        String jsonUsuario = gson.toJson(juego);
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
                ItemEjercicio newUsuario = gson.fromJson(response.body().string(), ItemEjercicio.class);
                objectId = String.valueOf(newUsuario.getObjectId());
                System.out.println("insert" + objectId);
            }
        }
        catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return objectId;
    }
}
