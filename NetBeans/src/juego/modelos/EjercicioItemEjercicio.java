package juego.modelos;

public class EjercicioItemEjercicio {
    private String objectId;
    private String objectIdEjercicio;
    private String objectIdItemEjercicio;

    public EjercicioItemEjercicio(String objectIdCircuito, String objectIdItemEjercicio) {
        this.objectIdEjercicio = objectIdCircuito;
        this.objectIdItemEjercicio = objectIdItemEjercicio;
    }

    public String getObjectIdEjercicio() {
        return objectIdEjercicio;
    }

    public String getObjectIdItemEjercicio() {
        return objectIdItemEjercicio;
    }

    public String getId() {
        return objectId;
    }
}
