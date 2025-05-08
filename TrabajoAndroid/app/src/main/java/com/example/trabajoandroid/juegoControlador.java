/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.trabajoandroid;

import static java.lang.Thread.sleep;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import Bussiness.JuegoService;
import Bussiness.modelo.Circuito;
import Bussiness.modelo.CircuitoJuego;
import Bussiness.modelo.Juego;
import Bussiness.modelo.Usuario;
import Bussiness.modelo.UsuarioJuego;
import DAO.dao.InventarioCircuitoItemEjercicio;
import DAO.dao.InventarioCircuitoJuego;
import DAO.dao.InventarioCircuitos;
import DAO.dao.InventarioEjercicio;
import DAO.dao.InventarioEjercicioItemEjercicio;
import DAO.dao.InventarioUsuario;
import DAO.dao.InventarioUsuarioJuego;

/**
 *
 * @author alumno
 */
public class juegoControlador {
    private InventarioCircuitos circuitos;
    private InventarioEjercicio ejercicios;
    private InventarioUsuario usuarios;
    private InventarioUsuarioJuego usuariosJuegos;
    private InventarioCircuitoItemEjercicio circuitoEjs;
    private InventarioEjercicioItemEjercicio ejerciciosItem;
    private InventarioCircuitoJuego circuitoJuegos;

    /*
    private CircuitoVista circuitoVista;
    private EjercicioVista ejercicioVista;
    private JuegoVista juegoVista;
    private UsuarioVista usuarioVista;*/

    public juegoControlador() {
        // Crear el modelo
        circuitos = new InventarioCircuitos();
        ejercicios = new InventarioEjercicio();
        usuarios = new InventarioUsuario();
        usuariosJuegos = new InventarioUsuarioJuego();
        ejerciciosItem = new InventarioEjercicioItemEjercicio();
        circuitoEjs = new InventarioCircuitoItemEjercicio();
        circuitoJuegos = new InventarioCircuitoJuego();
    }

    public void run() {/*
        Ejercicio ej1 = new Ejercicio("Nombre1", "Descripcion1");
        Ejercicio ej2 = new Ejercicio("Nombre2", "Descripcion2");
        Ejercicio ej3 = new Ejercicio("Nombre3", "Descripcion3");

        ejercicios.insert(ej1);
        ejercicios.insert(ej2);
        ejercicios.insert(ej3);

        ItemEjercicio iEj1 = new ItemEjercicio(ej1.getId(), 10);
        ItemEjercicio iEj2 = new ItemEjercicio(ej2.getId(), 15);
        ItemEjercicio iEj3 = new ItemEjercicio(ej3.getId(), 12);

        iEj1.insert();
        iEj2.insert();
        iEj3.insert();

        EjercicioItemEjercicio unionEjI1 = new EjercicioItemEjercicio(iEj1.getObjectId(), ej1.getId());
        EjercicioItemEjercicio unionEjI2 = new EjercicioItemEjercicio(iEj2.getObjectId(), ej2.getId());
        EjercicioItemEjercicio unionEjI3 = new EjercicioItemEjercicio(iEj3.getObjectId(), ej3.getId());

        ejerciciosItem.insert(unionEjI1);
        ejerciciosItem.insert(unionEjI2);
        ejerciciosItem.insert(unionEjI3);

        List<String> ejs = new ArrayList<>();
        ejs.add(iEj1.getObjectId());
        ejs.add(iEj2.getObjectId());
        ejs.add(iEj3.getObjectId());

        List<String> checkpoints = new ArrayList<>();
        checkpoints.add("42.796115, -1.627748");
        checkpoints.add("42.796022, -1.629337");
        checkpoints.add("42.796885, -1.628121");

        Circuito cir1 = new Circuito("Parque1", "Facil", checkpoints, ejs);
        Circuito cir2 = new Circuito("Parque1", "Medio", checkpoints, ejs);
        Circuito cir3 = new Circuito("Parque1", "Dificil", checkpoints, ejs);

        circuitos.insert(cir1);
        circuitos.insert(cir2);
        circuitos.insert(cir3);

        CircuitoItemEjercicio cirI11 = new CircuitoItemEjercicio(cir1.getId(), iEj1.getObjectId());
        CircuitoItemEjercicio cirI12 = new CircuitoItemEjercicio(cir1.getId(), iEj2.getObjectId());
        CircuitoItemEjercicio cirI13 = new CircuitoItemEjercicio(cir1.getId(), iEj3.getObjectId());
        CircuitoItemEjercicio cirI21 = new CircuitoItemEjercicio(cir2.getId(), iEj1.getObjectId());
        CircuitoItemEjercicio cirI22 = new CircuitoItemEjercicio(cir2.getId(), iEj2.getObjectId());
        CircuitoItemEjercicio cirI23 = new CircuitoItemEjercicio(cir2.getId(), iEj3.getObjectId());
        CircuitoItemEjercicio cirI31 = new CircuitoItemEjercicio(cir3.getId(), iEj1.getObjectId());
        CircuitoItemEjercicio cirI32 = new CircuitoItemEjercicio(cir3.getId(), iEj2.getObjectId());
        CircuitoItemEjercicio cirI33 = new CircuitoItemEjercicio(cir3.getId(), iEj3.getObjectId());

        circuitoEjs.insert(cirI11);
        circuitoEjs.insert(cirI12);
        circuitoEjs.insert(cirI13);
        circuitoEjs.insert(cirI21);
        circuitoEjs.insert(cirI22);
        circuitoEjs.insert(cirI23);
        circuitoEjs.insert(cirI31);
        circuitoEjs.insert(cirI32);
        circuitoEjs.insert(cirI33);

        List<String> juegos = new ArrayList<>();

        Usuario user = new Usuario("Leyre", "leyre@correo.com", "ghsrtfg", juegos);
        Usuario user1 = new Usuario("Ainhoa", "ainhoa@correo.com", "fbdfgtht", juegos);

        usuarios.insert(user);
        usuarios.insert(user1);

        Juego juego = crearJuego(usuarios.getUsuario(0));

        juegoFinalizado(usuarios.getUsuario(0), juego);

        Juego juego1 = crearJuego(usuarios.getUsuario(1), "Medio");

        juegoFinalizado(usuarios.getUsuario(1), juego1);*/

        //Crear las vistas
    }
    //Añadir metodos
    private Juego crearJuego(Usuario user){
        Random random = new Random();
        int indiceAleatorio = random.nextInt(circuitos.getAll().size());
        Circuito circuito = circuitos.getCircuito(indiceAleatorio); //Escojemos un circuito aleatorio, tambien se podria por dificultad

        Juego juego = new Juego(user.getId(), circuito.getId(), circuito.getDificultad());
        JuegoService ju = new JuegoService();
        ju.insert(juego);
        CircuitoJuego cirJ = new CircuitoJuego(circuitos.getCircuito(indiceAleatorio).getId(), juego.getId());
        circuitoJuegos.insert(cirJ);

        if (usuarios.getAll().contains(user)) {
            user.addJuego(juego.getId());
            usuarios.actualizaUsuario(user);
            UsuarioJuego userJ = new UsuarioJuego(user.getId(), juego.getId());
            usuariosJuegos.insert(userJ);
        } else {
            System.out.println("El usuario no se encuentra en la lista.");
        }
        return juego;
    }

    private Juego crearJuego(Usuario user, String dificultad){
        List<Circuito> circuitosDificultad = new ArrayList<>();
        for(Circuito c : circuitos.getAll()){
            if(c.getDificultad().equals(dificultad)){
                circuitosDificultad.add(c);
            }
        }
        if(circuitosDificultad.isEmpty()){
            System.out.println("No existen circuitos con esa dificultad");
        }else{
            Random random = new Random();
            int indiceAleatorio = random.nextInt(circuitosDificultad.size());
            Circuito circuito = circuitos.getCircuito(indiceAleatorio);

            Juego juego = new Juego(user.getId(), circuito.getId(), circuito.getDificultad());
            JuegoService ju = new JuegoService();
            ju.insert(juego);
            CircuitoJuego cirJ = new CircuitoJuego(circuitos.getCircuito(indiceAleatorio).getId(), juego.getId());
            circuitoJuegos.insert(cirJ);

            user.addJuego(juego.getId());
            usuarios.actualizaUsuario(user);
            UsuarioJuego userJ = new UsuarioJuego(user.getId(), juego.getId());
            usuariosJuegos.insert(userJ);
            return juego;
        }
        return null;
    }

    private void juegoFinalizado(Usuario user, Juego juego){
        int indice = usuarios.getAll().indexOf(user);
        if(usuarios.getUsuario(indice).getJuegosRealizados().contains(juego.getId())){
            int i = usuarios.getUsuario(indice).getJuegosRealizados().indexOf(juego);
            juego.actualizarTiempo();
            //juego.actualizaJuego();
            usuarios.actualizaUsuario(usuarios.getUsuario(indice));
        }else{
            System.out.println("No existe ese juego");
        }
    }
}
