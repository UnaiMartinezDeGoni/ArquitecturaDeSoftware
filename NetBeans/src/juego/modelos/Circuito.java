/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package juego.modelos;

import java.util.ArrayList;
import java.util.List;
import juego.modelos.ItemEjercicio;

/**
 *
 * @author alumno
 */
public class Circuito {
    private String objectId;
    private String nombre;
    private String dificultad;
    private List<String> checkpoints;
    private List<String> listaEjercicios;

    public Circuito(String nombre, String dificultad, List<String> checkpoints, List<String> ejercicios) {
        this.nombre = nombre;
        this.dificultad = dificultad;
        this.checkpoints = checkpoints;
        this.listaEjercicios = ejercicios;
        
    }
    
    public void agregarEjercicio(String ejercicio) {
        this.listaEjercicios.add(ejercicio);  // Agregar ejercicio a la lista
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
}
