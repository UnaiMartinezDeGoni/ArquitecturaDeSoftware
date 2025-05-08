/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package DAO.dao;

import java.util.List;

import Bussiness.modelo.Circuito;

/**
 *
 * @author alumno
 */
public interface InventarioCircuitosInterfaz {
    public List<Circuito> getAll() ;

    public String insert(Circuito circuito);

    public void actualizaCircuito(Circuito circuito);
}
