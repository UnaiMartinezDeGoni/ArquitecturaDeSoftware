package Bussiness;

import java.util.List;

import Bussiness.modelo.CircuitoJuego;
import DAO.dao.InventarioCircuitoJuego;

public class CircuitoJuegoService {
    private InventarioCircuitoJuego circuitoJuegoRepository;

    public CircuitoJuegoService(){
        this.circuitoJuegoRepository = new InventarioCircuitoJuego();
    }

    public String insert(CircuitoJuego cirItem){
        circuitoJuegoRepository.insert(cirItem);
        return cirItem.getId();
    }

    /*public boolean update(CircuitoJuego employee) {
        circuitoJuegoRepository.update(employee);
        return true;
    }*/

    public List<CircuitoJuego> getAll() {
        return circuitoJuegoRepository.getAll();
    }
}
