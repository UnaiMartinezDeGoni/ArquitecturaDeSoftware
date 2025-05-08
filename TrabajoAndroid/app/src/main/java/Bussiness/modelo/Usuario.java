package Bussiness.modelo;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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

    public String getPasswd() {
        return passwd;
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
            user += "[" + juego + "]";
        }
        user += '}';
        return user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Usuario usuario = (Usuario) o;
        return Objects.equals(nombre, usuario.nombre) && Objects.equals(objectId, usuario.objectId) && Objects.equals(correo, usuario.correo) && Objects.equals(passwd, usuario.passwd) && Objects.equals(juegosRealizados, usuario.juegosRealizados);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nombre, objectId, correo, passwd, juegosRealizados);
    }
}

