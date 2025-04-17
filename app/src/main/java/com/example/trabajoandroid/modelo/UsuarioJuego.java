package com.example.trabajoandroid.modelo;

public class UsuarioJuego {
    private String objectId;
    private String objectIdUsuario;
    private String objectIdJuego;

    public UsuarioJuego(String objectIdCircuito, String objectIdItemEjercicio) {
        this.objectIdUsuario = objectIdCircuito;
        this.objectIdJuego = objectIdItemEjercicio;
    }

    public String getObjectIdUsuario() {
        return objectIdUsuario;
    }

    public String getObjectIdJuego() {
        return objectIdJuego;
    }

    public String getId() {
        return objectId;
    }
}
