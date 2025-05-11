package com.example.trabajoandroid;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import Bussiness.modelo.Circuito;
import Bussiness.modelo.Ejercicio;
import Bussiness.modelo.Usuario;
import DAO.dao.InventarioCircuitos;
import DAO.dao.InventarioEjercicio;
import DAO.dao.InventarioUsuario;

public class Test{
    // =====================
    // PRUEBAS PARA EJERCICIO
    // =====================

    @org.junit.Test
    public void testCrearEjercicio() {
        Ejercicio ejercicio = new Ejercicio("Flexiones", "Entrena pecho y tríceps");
        InventarioEjercicio inventario = new InventarioEjercicio();
        inventario.insert(ejercicio);

        assertEquals("El nombre del ejercicio debe ser 'Flexiones'", "Flexiones", inventario.getAll().get(0).getNombre());
    }

    @org.junit.Test
    public void testActualizarEjercicio() {
        Ejercicio ejercicio = new Ejercicio("Flexiones", "Entrena pecho y tríceps");
        InventarioEjercicio inventario = new InventarioEjercicio();
        inventario.insert(ejercicio);

        inventario.getAll().get(0).setNombre("Flexiones Modificadas");
        inventario.getAll().get(0).setDescripcion("Actualizado: mayor intensidad");

        inventario.actualizaEjercicio(ejercicio);

        assertEquals("El nombre debe actualizarse", "Flexiones Modificadas", inventario.getAll().get(0).getNombre());
        assertEquals("La descripción debe actualizarse", "Actualizado: mayor intensidad", inventario.getAll().get(0).getDescripcion());
    }

    // =====================
    // PRUEBAS PARA CIRCUITO
    // =====================

    @org.junit.Test
    public void testCrearCircuito() {
        List<String> checkpoints = new ArrayList<>(Arrays.asList("Inicio", "Mitad", "Fin"));
        // Se asume que el circuito se crea inicialmente con una lista de ItemEjercicio vacía
        Circuito circuito = new Circuito("Circuito Básico", "Fácil", checkpoints, new ArrayList<>());
        InventarioCircuitos inventario = new InventarioCircuitos();
        inventario.insert(circuito);

        assertEquals("El nombre del circuito debe ser 'Circuito Básico'","Circuito Básico", inventario.getAll().get(0).getNombre());
    }

    @org.junit.Test
    public void testActualizarCircuito() {
        List<String> checkpoints = new ArrayList<>(Arrays.asList("Inicio", "Mitad", "Fin"));
        Circuito circuito = new Circuito("Circuito Original", "Fácil", checkpoints, new ArrayList<>());
        InventarioCircuitos inventario = new InventarioCircuitos();
        inventario.insert(circuito);

        inventario.getAll().get(0).setNombre("Circuito Actualizado");
        inventario.getAll().get(0).setDificultad("Medio");

        inventario.actualizaCircuito(circuito);

        assertEquals("El nombre debe actualizarse","Circuito Actualizado", inventario.getAll().get(0).getNombre());
        assertEquals("La dificultad debe actualizarse", "Medio", inventario.getAll().get(0).getDificultad());
    }

    // =====================
    // PRUEBAS PARA USUARIO
    // =====================

    @org.junit.Test
    public void testCrearUsuario() {
        // Se asume que el usuario se crea con una lista de juegos vacía
        Usuario usuario = new Usuario("Juan Perez", "juan@example.com", "12345", new ArrayList<>());
        InventarioUsuario inventario = new InventarioUsuario();
        inventario.insert(usuario);

        assertEquals("El nombre del usuario debe ser 'Juan Perez'","Juan Perez", inventario.getAll().get(0).getNombre());
    }

    @org.junit.Test
    public void testActualizarUsuario() {
        Usuario usuario = new Usuario("Juan Perez", "juan@example.com", "12345", new ArrayList<>());
        InventarioUsuario inventario = new InventarioUsuario();
        inventario.insert(usuario);

        inventario.getAll().get(0).setNombre("Juan P.");
        inventario.getAll().get(0).setCorreo("juanp@example.com");
        inventario.actualizaUsuario(usuario);

        assertEquals("El nombre debe actualizarse","Juan P.", inventario.getAll().get(0).getNombre());
        assertEquals("El correo debe actualizarse","juanp@example.com", inventario.getAll().get(0).getCorreo());
    }
}
