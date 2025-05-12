package Bussiness;

import java.io.IOException;
import java.util.List;

import Bussiness.modelo.Usuario;
import DAO.dao.InventarioUsuario;

public class UsuarioService {
    private InventarioUsuario usuarioRepository;

    public UsuarioService(){
        this.usuarioRepository = new InventarioUsuario();
    }

    public String insert(Usuario cirItem){
        usuarioRepository.insert(cirItem);
        return cirItem.getId();
    }

    public boolean update(Usuario employee) {
        usuarioRepository.actualizaUsuario(employee);
        return true;
    }

    public List<Usuario> getAll() {
        return usuarioRepository.getAll();
    }

    public Usuario verificarLogin(String username, String password) throws IOException {
        return usuarioRepository.verificarLogin(username, password);
    }

    public boolean verificarUsuarioExistente(String username){
        return usuarioRepository.verificarUsuarioExistente(username);
    }

    public Usuario getUsuario(int seleccionado){
        return getUsuario(seleccionado);
    }
}
