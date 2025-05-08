package Bussiness.modelo;

public class Imagen {
    public String url;
    private String idEjercicio;

    public Imagen(String url, String id){
        this.idEjercicio = id;
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getIdEjercicio() {
        return idEjercicio;
    }

    public void setIdEjercicio(String id){
        this.idEjercicio = id;
    }
}

