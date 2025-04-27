/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package juego.dao;

import java.util.List;
import juego.modelos.*;

/**
 *
 * @author alumno
 */
public interface InventarioUsuarioInterface {
    public List<Usuario> getAll() ;
      
    public String insert(Usuario user);

    public void actualizaUsuario(Usuario user);
}
