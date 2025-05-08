package Bussiness;

import java.util.List;

import Bussiness.modelo.Juego;
import DAO.dao.InventarioJuego;

public class JuegoService {
    private InventarioJuego juegoRepository;

    public JuegoService(){
        this.juegoRepository = new InventarioJuego();
    }

    public String insert(Juego cirItem){
        juegoRepository.insert(cirItem);
        return cirItem.getId();
    }

    public boolean update(Juego employee) {
        juegoRepository.actualizaJuego(employee);
        return true;
    }
}
