package Bussiness;

import java.util.List;

import Bussiness.modelo.UsuarioJuego;
import DAO.dao.InventarioUsuarioJuego;


public class UsuarioJuegoService {
    private InventarioUsuarioJuego usuarioJuegoRepository;

    public UsuarioJuegoService(){
        this.usuarioJuegoRepository = new InventarioUsuarioJuego();
    }

    public String insert(UsuarioJuego cirItem){
        usuarioJuegoRepository.insert(cirItem);
        return cirItem.getId();
    }

    public boolean update(UsuarioJuego employee) {
        usuarioJuegoRepository.actualizaUsuario(employee);
        return true;
    }

    public List<UsuarioJuego> getAll() {
        return usuarioJuegoRepository.getAll();
    }
}
