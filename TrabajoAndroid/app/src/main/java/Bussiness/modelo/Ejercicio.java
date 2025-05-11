package Bussiness.modelo;

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

    public void setId(String id){
        this.objectId = id;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }
}
