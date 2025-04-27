/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package juego.modelos;

/**
 *
 * @author alumno
 */
public class Ejercicio {
    private String nombre;
    private String descripcion;
    private String objectId;
    
    public Ejercicio(String nombre, String descripcion){
        this.descripcion = descripcion;
        this.nombre = nombre;
        
    }

    public String getNombre() {
        return nombre;
    }

    public String getId() {
        return objectId;
    }
    
    public String getDescripcion(){
        return descripcion;
    }

    @Override
    public String toString() {
        return "Ejercicio{" + "nombre=" + nombre + ", descripcion=" + descripcion + ", id=" + objectId + '}';
    }
    
}
