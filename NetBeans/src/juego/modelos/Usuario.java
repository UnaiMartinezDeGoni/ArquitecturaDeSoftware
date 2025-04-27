/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package juego.modelos;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author alumno
 */
public class Usuario {
    private String nombre;
    private String objectId;
    private String correo;
    private String passwd;
    private List<String> juegosRealizados;

    public Usuario(String nombre, String correo, String passwd, List<String> juegosRealizados) {
        this.nombre = nombre;
        this.juegosRealizados = new ArrayList<>();
        this.correo = correo;
        this.passwd = passwd;
    }

    public String getNombre() {
        return nombre;
    }

    public String getId() {
        return objectId;
    }

    public List<String> getJuegosRealizados() {
        return juegosRealizados;
    }
    
    public String getJuegos(int indice) {
        return juegosRealizados.get(indice);
    }

    public String getCorreo() {
        return correo;
    }
    
    public void addJuego(String juego){
        juegosRealizados.add(juego);
    }

    @Override
    public String toString() {
        String user = "Usuario{" + "nombre=" + nombre + ", id=" + objectId + ", correo=" + correo + ", juegosRealizados=";
        for(String juego : juegosRealizados){
             user += "[" + juego.toString() + "]";
        }
        user += '}';
        return user;
    }
}
