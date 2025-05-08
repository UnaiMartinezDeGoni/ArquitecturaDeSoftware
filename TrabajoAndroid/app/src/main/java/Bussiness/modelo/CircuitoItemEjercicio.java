package Bussiness.modelo;

public class CircuitoItemEjercicio {
    private String objectId;
    private String objectIdCircuito;
    private String objectIdItemEjercicio;

    public CircuitoItemEjercicio(String objectIdCircuito, String objectIdItemEjercicio) {
        this.objectIdCircuito = objectIdCircuito;
        this.objectIdItemEjercicio = objectIdItemEjercicio;
    }

    public String getObjectIdCircuito() {
        return objectIdCircuito;
    }

    public String getObjectIdItemEjercicio() {
        return objectIdItemEjercicio;
    }

    public String getId() {
        return objectId;
    }
}
