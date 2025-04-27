package juego.modelos;

public class CircuitoJuego {
    private String objectId;
    private String objectIdCircuito;
    private String objectIdJuego;

    public CircuitoJuego(String objectIdCircuito, String objectIdItemEjercicio) {
        this.objectIdCircuito = objectIdCircuito;
        this.objectIdJuego = objectIdItemEjercicio;
    }

    public String getObjectIdCircuito() {
        return objectIdCircuito;
    }

    public String getObjectIdJuego() {
        return objectIdJuego;
    }

    public String getId() {
        return objectId;
    }
}
