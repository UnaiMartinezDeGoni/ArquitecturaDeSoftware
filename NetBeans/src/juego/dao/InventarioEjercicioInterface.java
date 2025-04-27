/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package juego.dao;

import java.util.List;
import juego.modelos.Circuito;
import juego.modelos.Ejercicio;

/**
 *
 * @author alumno
 */
public interface InventarioEjercicioInterface {
    public List<Ejercicio> getAll() ;
      
    public String insert(Ejercicio ejercicio);

    public void actualizaEjercicio(Ejercicio ejercicio);
}
