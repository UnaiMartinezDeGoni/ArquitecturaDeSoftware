package Bussiness.modelo;

import com.google.gson.annotations.SerializedName;

public class EjercicioItemEjercicio {
    private String objectId;
    private String objectIdItemEjercicio;
    private String objectIdEjercicio;

    public EjercicioItemEjercicio(String objectIdItemEjercicio, String objectIdEjercicio) {
        this.objectIdItemEjercicio = objectIdItemEjercicio;
        this.objectIdEjercicio = objectIdEjercicio;
    }

    public String getObjectIdEjercicio() {
        return objectIdEjercicio;
    }

    public String getObjectIdItemEjercicio() {
        return objectIdItemEjercicio;
    }

    public String getObjectId() {
        return objectId;
    }
}
