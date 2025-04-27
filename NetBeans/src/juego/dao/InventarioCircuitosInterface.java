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
public interface InventarioCircuitosInterface {
    public List<Circuito> getAll() ;
      
    public String insert(Circuito circuito);

    public void actualizaCircuito(Circuito circuito);
}
