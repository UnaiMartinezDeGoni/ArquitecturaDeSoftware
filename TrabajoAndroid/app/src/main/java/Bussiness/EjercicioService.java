package Bussiness;

import java.util.List;

import Bussiness.modelo.Ejercicio;
import DAO.dao.InventarioEjercicio;


public class EjercicioService {
    private InventarioEjercicio ejerciciosRepository;

    public EjercicioService(){
        this.ejerciciosRepository = new InventarioEjercicio();
    }

    public String insert(Ejercicio cirItem){
        ejerciciosRepository.insert(cirItem);
        return cirItem.getId();
    }

    /*public boolean update(Circuito employee) {
        circuitoRepository.update(employee);
        return true;
    }*/

    public List<Ejercicio> getAll() {
        return ejerciciosRepository.getAll();
    }
}
