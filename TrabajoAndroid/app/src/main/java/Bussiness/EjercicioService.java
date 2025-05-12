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
        String id = ejerciciosRepository.insert(cirItem);
        return id;
    }

    /*public boolean update(Circuito employee) {
        circuitoRepository.update(employee);
        return true;
    }*/

    public List<Ejercicio> getAll() {
        return ejerciciosRepository.getAll();
    }
}
