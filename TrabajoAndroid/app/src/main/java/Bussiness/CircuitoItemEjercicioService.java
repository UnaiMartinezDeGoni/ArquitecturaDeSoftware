package Bussiness;

import java.util.List;

import Bussiness.modelo.CircuitoItemEjercicio;
import DAO.dao.InventarioCircuitoItemEjercicio;

public class CircuitoItemEjercicioService {
    private InventarioCircuitoItemEjercicio circuitoItemEjercicioRepository;

    public CircuitoItemEjercicioService(){
        this.circuitoItemEjercicioRepository = new InventarioCircuitoItemEjercicio();
    }

    public String insert(CircuitoItemEjercicio cirItem){
        circuitoItemEjercicioRepository.insert(cirItem);
        return cirItem.getId();
    }

    /*public boolean update(CircuitoItemEjercicio employee) {
        circuitoItemEjercicioRepository.update(employee);
        return true;
    }*/

    public List<CircuitoItemEjercicio> getAll() {
        return circuitoItemEjercicioRepository.getAll();
    }
}
