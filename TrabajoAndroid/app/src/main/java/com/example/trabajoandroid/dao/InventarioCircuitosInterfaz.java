/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.example.trabajoandroid.dao;

import java.util.List;
import com.example.trabajoandroid.modelo.*;

/**
 *
 * @author alumno
 */
public interface InventarioCircuitosInterfaz {
    public List<Circuito> getAll() ;

    public String insert(Circuito circuito);

    public void actualizaCircuito(Circuito circuito);
}
