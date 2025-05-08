package Bussiness;

import java.util.List;

import Bussiness.modelo.EjercicioItemEjercicio;
import DAO.dao.InventarioEjercicioItemEjercicio;


public class EjercicioItemEjercicioService {
    private InventarioEjercicioItemEjercicio ejercicioItemEjercicio;

    public EjercicioItemEjercicioService(){
        this.ejercicioItemEjercicio = new InventarioEjercicioItemEjercicio();
    }

    public String insert(EjercicioItemEjercicio cirItem){
        ejercicioItemEjercicio.insert(cirItem);
        return cirItem.getObjectId();
    }

    /*public boolean update(Circuito employee) {
        circuitoRepository.update(employee);
        return true;
    }*/

    public List<EjercicioItemEjercicio> getAll() {
        return ejercicioItemEjercicio.getAll();
    }
}
