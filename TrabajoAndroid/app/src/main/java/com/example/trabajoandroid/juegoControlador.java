/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.trabajoandroid;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import Bussiness.CircuitoItemEjercicioService;
import Bussiness.CircuitoJuegoService;
import Bussiness.CircuitosService;
import Bussiness.EjercicioItemEjercicioService;
import Bussiness.EjercicioService;
import Bussiness.ItemEjercicioService;
import Bussiness.JuegoService;
import Bussiness.UsuarioJuegoService;
import Bussiness.UsuarioService;
import Bussiness.modelo.Circuito;
import Bussiness.modelo.EjercicioItemEjercicio;
import Bussiness.modelo.CircuitoItemEjercicio;
import Bussiness.modelo.CircuitoJuego;
import Bussiness.modelo.Ejercicio;
import Bussiness.modelo.ItemEjercicio;
import Bussiness.modelo.Juego;
import Bussiness.modelo.Usuario;
import Bussiness.modelo.UsuarioJuego;

/**
 *
 * @author alumno
 */
public class juegoControlador {
    private CircuitosService circuitos;
    private EjercicioService ejercicios;
    private UsuarioService usuarios;
    private UsuarioJuegoService usuariosJuegos;
    private CircuitoItemEjercicioService circuitoEjs;
    private EjercicioItemEjercicioService ejerciciosItem;
    private CircuitoJuegoService circuitoJuegos;
    private ItemEjercicioService itemEjs;

    /*
    private CircuitoVista circuitoVista;
    private EjercicioVista ejercicioVista;
    private JuegoVista juegoVista;
    private UsuarioVista usuarioVista;*/

    public juegoControlador() {
        // Crear el modelo
        circuitos = new CircuitosService();
        ejercicios = new EjercicioService();
        usuarios = new UsuarioService();
        usuariosJuegos = new UsuarioJuegoService();
        ejerciciosItem = new EjercicioItemEjercicioService();
        circuitoEjs = new CircuitoItemEjercicioService();
        circuitoJuegos = new CircuitoJuegoService();
        itemEjs = new ItemEjercicioService();
    }

    public void run() {/*
        Ejercicio ej1 = new Ejercicio("Flexiones tradicionales", "debes colocar las manos en el suelo, al ancho de los hombros, y el cuerpo debe estar recto desde la cabeza hasta los talones. Flexionas los codos para bajar el torso hacia el suelo, y luego empujas con las manos para regresar a la posición inicial.");
        Ejercicio ej2 = new Ejercicio("Mountain Climbers", "Empiezas en posición de plancha, con las manos en el suelo y el cuerpo recto. Luego, alternas llevando una rodilla hacia el pecho y la otra pierna extendida, imitando un movimiento de escalador o escalando una montaña, manteniendo un ritmo rápido.");
        Ejercicio ej3 = new Ejercicio("Sentadillas clásicas", "Colocas los pies a la altura de los hombros y bajas el cuerpo como si fueras a sentarte en una silla invisible. La espalda debe mantenerse recta y las rodillas no deben sobrepasar los dedos de los pies. Luego, subes de nuevo a la posición inicial.");
        Ejercicio ej4 = new Ejercicio("Zancadas", "Das un paso grande hacia adelante con una pierna, bajando las caderas hasta que ambas rodillas formen un ángulo de 90 grados. La pierna trasera debe casi tocar el suelo, pero sin llegar a apoyarse. Luego, empujas con la pierna adelantada para regresar a la posición inicial y repites con la otra pierna.");
        Ejercicio ej5 = new Ejercicio("Burpees", "Comienzas de pie, te agachas y pones las manos en el suelo, luego saltas con los pies hacia atrás para estar en posición de plancha. Realizas una flexión, luego saltas con los pies hacia las manos y terminas con un salto hacia arriba.");

        ejercicios.insert(ej1);
        ejercicios.insert(ej2);
        ejercicios.insert(ej3);
        ejercicios.insert(ej4);
        ejercicios.insert(ej5);

        //Primer Circuito Facil
        ItemEjercicio iEj1 = new ItemEjercicio(ej1.getId(), 10);
        ItemEjercicio iEj2 = new ItemEjercicio(ej2.getId(), 15);
        ItemEjercicio iEj3 = new ItemEjercicio(ej3.getId(), 10);

        String idItem = itemEjs.insert(iEj1);
        String idItem2 = itemEjs.insert(iEj2);
        String idItem3 = itemEjs.insert(iEj3);

        EjercicioItemEjercicio unionEjI1 = new EjercicioItemEjercicio(idItem, ej1.getId());
        EjercicioItemEjercicio unionEjI2 = new EjercicioItemEjercicio(idItem2, ej2.getId());
        EjercicioItemEjercicio unionEjI3 = new EjercicioItemEjercicio(idItem3, ej3.getId());

        ejerciciosItem.insert(unionEjI1);
        ejerciciosItem.insert(unionEjI2);
        ejerciciosItem.insert(unionEjI3);

        List<String> ejs = new ArrayList<>();
        ejs.add(idItem);
        ejs.add(idItem2);
        ejs.add(idItem3);

        List<String> checkpoints = new ArrayList<>();
        checkpoints.add("42.800330, -1.636106");
        checkpoints.add("42.799224, -1.635006");
        checkpoints.add("42.797878, -1.633638");
        checkpoints.add("42.798149, -1.634808");
        checkpoints.add("42.799031, -1.635666");

        Circuito cir1 = new Circuito("Circuito Detras Biblioteca Facil 1", "Facil", checkpoints, ejs);

        circuitos.insert(cir1);

        CircuitoItemEjercicio cirI11 = new CircuitoItemEjercicio(cir1.getId(), idItem);
        CircuitoItemEjercicio cirI12 = new CircuitoItemEjercicio(cir1.getId(), idItem2);
        CircuitoItemEjercicio cirI13 = new CircuitoItemEjercicio(cir1.getId(), idItem3);

        circuitoEjs.insert(cirI11);
        circuitoEjs.insert(cirI12);
        circuitoEjs.insert(cirI13);

        //Segundo Circuito Facil
        ItemEjercicio iEj4 = new ItemEjercicio(ej4.getId(), 10);
        ItemEjercicio iEj5 = new ItemEjercicio(ej2.getId(), 15);
        ItemEjercicio iEj6 = new ItemEjercicio(ej5.getId(), 10);

        String idItem4 = itemEjs.insert(iEj4);
        String idItem5 = itemEjs.insert(iEj5);
        String idItem6 = itemEjs.insert(iEj6);

        EjercicioItemEjercicio unionEjI4 = new EjercicioItemEjercicio(idItem4, ej4.getId());
        EjercicioItemEjercicio unionEjI5 = new EjercicioItemEjercicio(idItem5, ej2.getId());
        EjercicioItemEjercicio unionEjI6 = new EjercicioItemEjercicio(idItem6, ej5.getId());

        ejerciciosItem.insert(unionEjI4);
        ejerciciosItem.insert(unionEjI5);
        ejerciciosItem.insert(unionEjI6);

        List<String> ejs1 = new ArrayList<>();
        ejs1.add(idItem4);
        ejs1.add(idItem5);
        ejs1.add(idItem6);

        List<String> checkpoints1 = new ArrayList<>();
        checkpoints1.add("42.800330, -1.636106");
        checkpoints1.add("42.799224, -1.635006");
        checkpoints1.add("42.797878, -1.633638");
        checkpoints1.add("42.798149, -1.634808");
        checkpoints1.add("42.799031, -1.635666");

        Circuito cir2 = new Circuito("Circuito Detras Biblioteca Facil 2", "Facil", checkpoints1, ejs1);

        circuitos.insert(cir1);

        CircuitoItemEjercicio cirI21 = new CircuitoItemEjercicio(cir2.getId(), idItem);
        CircuitoItemEjercicio cirI22 = new CircuitoItemEjercicio(cir2.getId(), idItem2);
        CircuitoItemEjercicio cirI23 = new CircuitoItemEjercicio(cir2.getId(), idItem3);

        circuitoEjs.insert(cirI21);
        circuitoEjs.insert(cirI22);
        circuitoEjs.insert(cirI23);

        //Primer Circuito Medio
        ItemEjercicio iEj7 = new ItemEjercicio(ej1.getId(), 15);
        ItemEjercicio iEj8 = new ItemEjercicio(ej2.getId(), 25);
        ItemEjercicio iEj9 = new ItemEjercicio(ej3.getId(), 20);
        ItemEjercicio iEj10 = new ItemEjercicio(ej4.getId(), 20);

        String idItem7 = itemEjs.insert(iEj7);
        String idItem8 = itemEjs.insert(iEj8);
        String idItem9 = itemEjs.insert(iEj9);
        String idItem10 = itemEjs.insert(iEj10);

        EjercicioItemEjercicio unionEjI7 = new EjercicioItemEjercicio(idItem7, ej1.getId());
        EjercicioItemEjercicio unionEjI8 = new EjercicioItemEjercicio(idItem8, ej2.getId());
        EjercicioItemEjercicio unionEjI9 = new EjercicioItemEjercicio(idItem9, ej3.getId());
        EjercicioItemEjercicio unionEjI10 = new EjercicioItemEjercicio(idItem10, ej4.getId());

        ejerciciosItem.insert(unionEjI7);
        ejerciciosItem.insert(unionEjI8);
        ejerciciosItem.insert(unionEjI9);
        ejerciciosItem.insert(unionEjI10);

        List<String> ejs3 = new ArrayList<>();
        ejs3.add(idItem7);
        ejs3.add(idItem8);
        ejs3.add(idItem9);
        ejs3.add(idItem10);

        List<String> checkpoints2 = new ArrayList<>();
        checkpoints2.add("42.796213, -1.627633");
        checkpoints2.add("42.796838, -1.627131");
        checkpoints2.add("42.796664, -1.626476");
        checkpoints2.add("42.796659, -1.625943");
        checkpoints2.add("42.796202, -1.625821");
        checkpoints2.add("42.795640, -1.626269");

        Circuito cir3 = new Circuito("Circuito Parque UPNA Medio 1", "Medio", checkpoints2, ejs3);

        circuitos.insert(cir3);

        CircuitoItemEjercicio cirI31 = new CircuitoItemEjercicio(cir2.getId(), idItem7);
        CircuitoItemEjercicio cirI32 = new CircuitoItemEjercicio(cir2.getId(), idItem8);
        CircuitoItemEjercicio cirI33 = new CircuitoItemEjercicio(cir2.getId(), idItem9);
        CircuitoItemEjercicio cirI34 = new CircuitoItemEjercicio(cir2.getId(), idItem10);

        circuitoEjs.insert(cirI31);
        circuitoEjs.insert(cirI32);
        circuitoEjs.insert(cirI33);
        circuitoEjs.insert(cirI34);

        //Segundo Circuito Medio
        ItemEjercicio iEj11 = new ItemEjercicio(ej1.getId(), 15);
        ItemEjercicio iEj12 = new ItemEjercicio(ej2.getId(), 25);
        ItemEjercicio iEj13 = new ItemEjercicio(ej3.getId(), 20);
        ItemEjercicio iEj14 = new ItemEjercicio(ej5.getId(), 20);

        String idItem11 = itemEjs.insert(iEj11);
        String idItem12 = itemEjs.insert(iEj12);
        String idItem13 = itemEjs.insert(iEj13);
        String idItem14 = itemEjs.insert(iEj14);

        EjercicioItemEjercicio unionEjI11 = new EjercicioItemEjercicio(idItem11, ej1.getId());
        EjercicioItemEjercicio unionEjI12 = new EjercicioItemEjercicio(idItem12, ej2.getId());
        EjercicioItemEjercicio unionEjI13 = new EjercicioItemEjercicio(idItem13, ej3.getId());
        EjercicioItemEjercicio unionEjI14 = new EjercicioItemEjercicio(idItem14, ej5.getId());

        ejerciciosItem.insert(unionEjI11);
        ejerciciosItem.insert(unionEjI12);
        ejerciciosItem.insert(unionEjI13);
        ejerciciosItem.insert(unionEjI14);

        List<String> ejs4 = new ArrayList<>();
        ejs4.add(idItem11);
        ejs4.add(idItem12);
        ejs4.add(idItem13);
        ejs4.add(idItem14);

        List<String> checkpoints3 = new ArrayList<>();
        checkpoints3.add("42.796213, -1.627633");
        checkpoints3.add("42.796838, -1.627131");
        checkpoints3.add("42.796664, -1.626476");
        checkpoints3.add("42.796659, -1.625943");
        checkpoints3.add("42.796202, -1.625821");
        checkpoints3.add("42.795640, -1.626269");

        Circuito cir4 = new Circuito("Circuito Parque UPNA Medio 2", "Medio", checkpoints3, ejs4);

        circuitos.insert(cir4);

        CircuitoItemEjercicio cirI41 = new CircuitoItemEjercicio(cir4.getId(), idItem11);
        CircuitoItemEjercicio cirI42 = new CircuitoItemEjercicio(cir4.getId(), idItem12);
        CircuitoItemEjercicio cirI43 = new CircuitoItemEjercicio(cir4.getId(), idItem13);
        CircuitoItemEjercicio cirI44 = new CircuitoItemEjercicio(cir4.getId(), idItem14);

        circuitoEjs.insert(cirI41);
        circuitoEjs.insert(cirI42);
        circuitoEjs.insert(cirI43);
        circuitoEjs.insert(cirI44);

        //Primer Circuito Dificil
        ItemEjercicio iEj15 = new ItemEjercicio(ej1.getId(), 25);
        ItemEjercicio iEj16 = new ItemEjercicio(ej2.getId(), 35);
        ItemEjercicio iEj17 = new ItemEjercicio(ej3.getId(), 30);
        ItemEjercicio iEj18 = new ItemEjercicio(ej4.getId(), 30);
        ItemEjercicio iEj19 = new ItemEjercicio(ej5.getId(), 30);

        String idItem15 = itemEjs.insert(iEj15);
        String idItem16 = itemEjs.insert(iEj16);
        String idItem17 = itemEjs.insert(iEj17);
        String idItem18 = itemEjs.insert(iEj18);
        String idItem19 = itemEjs.insert(iEj19);

        EjercicioItemEjercicio unionEjI15 = new EjercicioItemEjercicio(idItem15, ej1.getId());
        EjercicioItemEjercicio unionEjI16 = new EjercicioItemEjercicio(idItem16, ej2.getId());
        EjercicioItemEjercicio unionEjI17 = new EjercicioItemEjercicio(idItem17, ej3.getId());
        EjercicioItemEjercicio unionEjI18 = new EjercicioItemEjercicio(idItem18, ej4.getId());
        EjercicioItemEjercicio unionEjI19 = new EjercicioItemEjercicio(idItem19, ej5.getId());

        ejerciciosItem.insert(unionEjI15);
        ejerciciosItem.insert(unionEjI16);
        ejerciciosItem.insert(unionEjI17);
        ejerciciosItem.insert(unionEjI18);
        ejerciciosItem.insert(unionEjI19);

        List<String> ejs5 = new ArrayList<>();
        ejs5.add(idItem15);
        ejs5.add(idItem16);
        ejs5.add(idItem17);
        ejs5.add(idItem18);
        ejs5.add(idItem19);

        List<String> checkpoints4 = new ArrayList<>();
        checkpoints4.add("42.796213, -1.627633");
        checkpoints4.add("42.796838, -1.627131");
        checkpoints4.add("42.796664, -1.626476");
        checkpoints4.add("42.796659, -1.625943");
        checkpoints4.add("42.796202, -1.625821");
        checkpoints4.add("42.795640, -1.626269");
        checkpoints4.add("42.795979, -1.627049");

        Circuito cir5 = new Circuito("Circuito Parque UPNA Dificil 1", "Dificil", checkpoints4, ejs5);

        circuitos.insert(cir5);

        CircuitoItemEjercicio cirI51 = new CircuitoItemEjercicio(cir5.getId(), idItem15);
        CircuitoItemEjercicio cirI52 = new CircuitoItemEjercicio(cir5.getId(), idItem16);
        CircuitoItemEjercicio cirI53 = new CircuitoItemEjercicio(cir5.getId(), idItem17);
        CircuitoItemEjercicio cirI54 = new CircuitoItemEjercicio(cir5.getId(), idItem18);
        CircuitoItemEjercicio cirI55 = new CircuitoItemEjercicio(cir5.getId(), idItem19);

        circuitoEjs.insert(cirI51);
        circuitoEjs.insert(cirI52);
        circuitoEjs.insert(cirI53);
        circuitoEjs.insert(cirI54);
        circuitoEjs.insert(cirI55);

        //Segundo Circuito Dificil
        ItemEjercicio iEj20 = new ItemEjercicio(ej3.getId(), 25);
        ItemEjercicio iEj21 = new ItemEjercicio(ej4.getId(), 35);
        ItemEjercicio iEj22 = new ItemEjercicio(ej1.getId(), 30);
        ItemEjercicio iEj23 = new ItemEjercicio(ej5.getId(), 30);
        ItemEjercicio iEj24 = new ItemEjercicio(ej2.getId(), 30);

        String idItem20 = itemEjs.insert(iEj20);
        String idItem21 = itemEjs.insert(iEj21);
        String idItem22 = itemEjs.insert(iEj22);
        String idItem23 = itemEjs.insert(iEj23);
        String idItem24 = itemEjs.insert(iEj24);

        EjercicioItemEjercicio unionEjI20 = new EjercicioItemEjercicio(idItem20, ej3.getId());
        EjercicioItemEjercicio unionEjI21 = new EjercicioItemEjercicio(idItem21, ej4.getId());
        EjercicioItemEjercicio unionEjI22 = new EjercicioItemEjercicio(idItem22, ej1.getId());
        EjercicioItemEjercicio unionEjI23 = new EjercicioItemEjercicio(idItem23, ej5.getId());
        EjercicioItemEjercicio unionEjI24 = new EjercicioItemEjercicio(idItem24, ej2.getId());

        ejerciciosItem.insert(unionEjI20);
        ejerciciosItem.insert(unionEjI21);
        ejerciciosItem.insert(unionEjI22);
        ejerciciosItem.insert(unionEjI23);
        ejerciciosItem.insert(unionEjI24);

        List<String> ejs6 = new ArrayList<>();
        ejs6.add(idItem20);
        ejs6.add(idItem21);
        ejs6.add(idItem22);
        ejs6.add(idItem23);
        ejs6.add(idItem24);

        List<String> checkpoints5 = new ArrayList<>();
        checkpoints5.add("42.796213, -1.627633");
        checkpoints5.add("42.796838, -1.627131");
        checkpoints5.add("42.796664, -1.626476");
        checkpoints5.add("42.796659, -1.625943");
        checkpoints5.add("42.796202, -1.625821");
        checkpoints5.add("42.795640, -1.626269");
        checkpoints5.add("42.795979, -1.627049");

        Circuito cir6 = new Circuito("Circuito Parque UPNA Dificil 2", "Dificil", checkpoints5, ejs6);

        circuitos.insert(cir6);

        CircuitoItemEjercicio cirI61 = new CircuitoItemEjercicio(cir6.getId(), idItem20);
        CircuitoItemEjercicio cirI62 = new CircuitoItemEjercicio(cir6.getId(), idItem21);
        CircuitoItemEjercicio cirI63 = new CircuitoItemEjercicio(cir6.getId(), idItem22);
        CircuitoItemEjercicio cirI64 = new CircuitoItemEjercicio(cir6.getId(), idItem23);
        CircuitoItemEjercicio cirI65 = new CircuitoItemEjercicio(cir6.getId(), idItem24);

        circuitoEjs.insert(cirI61);
        circuitoEjs.insert(cirI62);
        circuitoEjs.insert(cirI63);
        circuitoEjs.insert(cirI64);
        circuitoEjs.insert(cirI65);*/

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
            usuarios.update(user);
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
            usuarios.update(user);
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
            usuarios.update(usuarios.getUsuario(indice));
        }else{
            System.out.println("No existe ese juego");
        }
    }
}
