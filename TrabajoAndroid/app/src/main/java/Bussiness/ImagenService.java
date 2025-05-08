package Bussiness;

import java.util.List;

import Bussiness.modelo.Imagen;
import DAO.dao.InventarioImagen;


public class ImagenService {
    private InventarioImagen imagenRepository;

    public ImagenService(){
        this.imagenRepository = new InventarioImagen();
    }

    public String insert(Imagen cirItem){
        imagenRepository.insert(cirItem);
        return cirItem.getIdEjercicio();
    }

    /*public boolean update(Circuito employee) {
        circuitoRepository.update(employee);
        return true;
    }*/

    public List<Imagen> getAll() {
        return imagenRepository.getAll();
    }
}
