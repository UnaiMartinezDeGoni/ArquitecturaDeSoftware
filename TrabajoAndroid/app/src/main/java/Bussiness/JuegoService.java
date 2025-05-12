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
        String id = juegoRepository.insert(cirItem);
        return id;
    }

    public boolean update(Juego employee) {
        juegoRepository.actualizaJuego(employee);
        return true;
    }
}
