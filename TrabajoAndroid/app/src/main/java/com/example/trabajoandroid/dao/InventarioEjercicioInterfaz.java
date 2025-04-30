/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.example.trabajoandroid.dao;

import java.util.List;
import com.example.trabajoandroid.modelo.Ejercicio;

/**
 *
 * @author alumno
 */
public interface InventarioEjercicioInterfaz {
    public List<Ejercicio> getAll() ;

    public String insert(Ejercicio ejercicio);

    public void actualizaEjercicio(Ejercicio ejercicio);
}
