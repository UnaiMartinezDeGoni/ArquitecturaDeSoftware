package Bussiness.modelo;

import java.util.List;

/**
 *
 * @author alumno
 */
public class Circuito {
    private String objectId;
    private String nombre;
    private String dificultad;

    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setDificultad(String dificultad) {
        this.dificultad = dificultad;
    }

    public void setCheckpoints(List<String> checkpoints) {
        this.checkpoints = checkpoints;
    }

    public void setListaEjercicios(List<String> listaEjercicios) {
        this.listaEjercicios = listaEjercicios;
    }

    private List<String> checkpoints;
    private List<String> listaEjercicios;

    public Circuito(String nombre, String dificultad, List<String> checkpoints, List<String> listaEjercicios) {
        this.nombre = nombre;
        this.dificultad = dificultad;
        this.checkpoints = checkpoints;
        this.listaEjercicios = listaEjercicios;

    }

    public String getId() {
        return objectId;
    }

    public String getNombre() {
        return nombre;
    }

    public String getDificultad() {
        return dificultad;
    }

    public List<String> getCheckpoints() {
        return checkpoints;
    }

    public List<String> getListaEjercicios() {
        return listaEjercicios;
    }

    @Override
    public String toString() {
        String res = "Circuito{" + "id=" + objectId + ", nombre=" + nombre + ", dificultad=" + dificultad + ", checkpoints=";

        for(String check : checkpoints){
            res += "[" + check + "]";
        }

        res += ", listaEjercicios=";

        for(String item : listaEjercicios){
            res += "[" + item.toString() + "]";
        }
        res += '}';

        return res;
    }

    public void setId(String id){
        this.objectId = id;
    }
}
