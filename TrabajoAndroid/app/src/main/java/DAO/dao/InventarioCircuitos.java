/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO.dao;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import Bussiness.modelo.Circuito;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 *
 * @author alumno
 */
public class InventarioCircuitos implements InventarioCircuitosInterfaz{
    private final String API_URL = "https://parseapi.back4app.com/classes/Circuito";
    private final String APPLICATION_ID ="EHUFM0VK5GnLAD601JX7T3sEfDSQ4tDkEPlZFqwH";
    private final String REST_API_KEY ="eQNek326ON4ELaQdzCbRKt0dDQYSZKQlie504BWb";

    @Override
    public ArrayList<Circuito> getAll(){
        ArrayList<Circuito> proveedores = new ArrayList<Circuito>();

        // Ejecutar en un hilo de fondo
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    OkHttpClient client = new OkHttpClient();
                    Request request = new Request.Builder()
                            .url(API_URL)
                            .addHeader("X-Parse-Application-Id", APPLICATION_ID)
                            .addHeader("X-Parse-REST-API-Key", REST_API_KEY)
                            .addHeader("Content-Type", "application/json")
                            .get()
                            .build();
                    Response response = client.newCall(request).execute();

                    if (response.code() == 200) {
                        String responseJson = response.body().string();
                        JsonObject jsonObject = new Gson().fromJson(responseJson, JsonObject.class);
                        JsonArray jsonArray = jsonObject.getAsJsonArray("results");
                        Gson gson = new Gson();
                        Type listType = new TypeToken<ArrayList<Circuito>>() {}.getType();
                        proveedores.addAll(gson.fromJson(jsonArray, listType));
                    }
                } catch (IOException e) {
                    System.out.println(e.getMessage());
                }

                // Actualiza la UI en el hilo principal si es necesario
                // Si necesitas actualizar la UI en el hilo principal, puedes usar un handler o algo similar:
                // runOnUiThread(new Runnable() {
                //     @Override
                //     public void run() {
                //         // Actualizar la UI con los datos aquí, si es necesario
                //     }
                // });
            }
        }).start();

        return proveedores;
    }

    @Override
    public String insert(Circuito circuito) {
        String objectId = "";
        Gson gson = new Gson();
        String jsonCircuito = gson.toJson(circuito);  // Convertimos el objeto Circuito a JSON

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
                Circuito newCircuito = gson.fromJson(response.body().string(), Circuito.class);
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


    @Override
    public void actualizaCircuito(Circuito circuito) {
        Gson gson = new Gson();
        String jsonCircuito = gson.toJson(circuito);

        try {
            // Crear la URL con el objectId del circuito que quieres actualizar
            String url = API_URL + "/" + circuito.getId();

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

    public Circuito getCircuito(int seleccionado) {
        ArrayList<Circuito> circuitos = getAll();
        return circuitos.get(seleccionado);
    }

    public ArrayList<Circuito> obtenerCircuitosPorDificultad(String filtro) throws IOException {
        ArrayList<Circuito> list = new ArrayList<>();
        String url = "https://parseapi.back4app.com/classes/Circuito";
        if (!"Todos".equals(filtro)) {
            url += "?where={\"dificultad\":\"" + filtro + "\"}";
        }

        OkHttpClient client = new OkHttpClient();
        Request req = new Request.Builder()
                .url(url)
                .addHeader("X-Parse-Application-Id", "EHUFM0VK5GnLAD601JX7T3sEfDSQ4tDkEPlZFqwH")
                .addHeader("X-Parse-REST-API-Key", "eQNek326ON4ELaQdzCbRKt0dDQYSZKQlie504BWb")
                .get().build();

        Response resp = client.newCall(req).execute();
        if (!resp.isSuccessful()) return list;

        JsonObject root = JsonParser.parseString(resp.body().string()).getAsJsonObject();
        JsonArray results = root.getAsJsonArray("results");
        for (int i = 0; i < results.size(); i++) {
            JsonObject obj = results.get(i).getAsJsonObject();
            String id = obj.get("objectId").getAsString();
            String nombre = obj.get("nombre").getAsString();
            String dif = obj.get("dificultad").getAsString();

            JsonArray cps = obj.getAsJsonArray("checkpoints");
            List<String> checkpoints = new ArrayList<>();
            for (int j = 0; j < cps.size(); j++) {
                checkpoints.add(cps.get(j).getAsString());
            }

            JsonArray lis = obj.getAsJsonArray("listaEjercicios");
            List<String> ejercicios = new ArrayList<>();
            for (int j = 0; j < lis.size(); j++) {
                ejercicios.add(lis.get(j).getAsString());
            }

            // Crear circuito y asignar el ID
            Circuito circuito = new Circuito(nombre, dif, checkpoints, ejercicios);
            circuito.setId(id);  // <-- asegurate de tener este setter en tu clase Circuito
            list.add(circuito);
        }
        return list;
    }
}
