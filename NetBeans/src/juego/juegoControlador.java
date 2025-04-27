/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package juego;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import static java.lang.Thread.sleep;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import javax.swing.JOptionPane;
import juego.dao.*;
import juego.modelos.*;
import juego.vistas.*;

/**
 *
 * @author alumno
 */
public class juegoControlador {
    private InventarioCircuitos circuitos;
    private InventarioEjercicio ejercicios;
    private InventarioUsuario usuarios;
    private InventarioUsuarioJuego usuariosJuegos;
    private InventarioEjercicioItemEjercicio ejerciciosItem;
    private InventarioCircuitoItemEjercicio circuitoEjs;
    private InventarioCircuitoJuego circuitoJuegos;
    
    private CircuitosVista circuitoVista;
    private CircuitoVista circuito;
    private EjerciciosVista ejercicioVista;
    private EjercicioVista ejercicio;
    private JuegoVista juegoVista;
    private UsuariosVista usuarioVista;
    private UsuarioVista usuario;
    private AppVista app;

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

    public void run() {
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

        EjercicioItemEjercicio unionEjI1 = new EjercicioItemEjercicio(iEj1.getId(), ej1.getId());
        EjercicioItemEjercicio unionEjI2 = new EjercicioItemEjercicio(iEj2.getId(), ej2.getId());
        EjercicioItemEjercicio unionEjI3 = new EjercicioItemEjercicio(iEj3.getId(), ej3.getId());

        ejerciciosItem.insert(unionEjI1);
        ejerciciosItem.insert(unionEjI2);
        ejerciciosItem.insert(unionEjI3);

        List<String> ejs = new ArrayList<>();
        ejs.add(iEj1.getId());
        ejs.add(iEj2.getId());
        ejs.add(iEj3.getId());

        List<String> checkpoints = new ArrayList<>();
        checkpoints.add("Arbol");
        checkpoints.add("Banco");
        checkpoints.add("Farola");

        Circuito cir1 = new Circuito("Parque1", "Facil", checkpoints, ejs);
        Circuito cir2 = new Circuito("Parque1", "Medio", checkpoints, ejs);
        Circuito cir3 = new Circuito("Parque1", "Dificil", checkpoints, ejs);

        circuitos.insert(cir1);
        circuitos.insert(cir2);
        circuitos.insert(cir3);

        CircuitoItemEjercicio cirI11 = new CircuitoItemEjercicio(cir1.getId(), iEj1.getId());
        CircuitoItemEjercicio cirI12 = new CircuitoItemEjercicio(cir1.getId(), iEj2.getId());
        CircuitoItemEjercicio cirI13 = new CircuitoItemEjercicio(cir1.getId(), iEj3.getId());
        CircuitoItemEjercicio cirI21 = new CircuitoItemEjercicio(cir2.getId(), iEj1.getId());
        CircuitoItemEjercicio cirI22 = new CircuitoItemEjercicio(cir2.getId(), iEj2.getId());
        CircuitoItemEjercicio cirI23 = new CircuitoItemEjercicio(cir2.getId(), iEj3.getId());
        CircuitoItemEjercicio cirI31 = new CircuitoItemEjercicio(cir3.getId(), iEj1.getId());
        CircuitoItemEjercicio cirI32 = new CircuitoItemEjercicio(cir3.getId(), iEj2.getId());
        CircuitoItemEjercicio cirI33 = new CircuitoItemEjercicio(cir3.getId(), iEj3.getId());

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

        //Crear las vistas
        app = new AppVista();
        
        app.getCircuitoButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mostrarCircuitos();
            }
        });
        
        app.getEjercicioButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mostrarEjercicios();
            }
        });
        
        app.getUsuarioButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mostrarUsuarios();
            }
        });
        
        app.setVisible(true);
    }
    //Añadir metodos
    private Juego crearJuego(Usuario user){
        Random random = new Random();
        int indiceAleatorio = random.nextInt(circuitos.getAll().size());
        Circuito circuito = circuitos.getCircuito(indiceAleatorio); //Escojemos un circuito aleatorio, tambien se podria por dificultad

        Juego juego = new Juego(user.getId(), circuito.getId(), circuito.getDificultad());
        juego.insert();
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
            juego.insert();
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
            juego.actualizaJuego();
            usuarios.actualizaUsuario(usuarios.getUsuario(indice));
        }else{
            System.out.println("No existe ese juego");
        }
    }
    
    //Vistas
    private void mostrarCircuitos() {
        circuitoVista = new CircuitosVista();

        for(Circuito circuito : circuitos.getAll() ) {
            circuitoVista.getCircuitosComboBox().addItem(circuito.getNombre());
        }
        // Agregar listener al botón de agregar al carrito
        circuitoVista.getCircuitoButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mostrarCircuito();
            }
        });

        circuitoVista.setVisible(true);
    }
    
    private void mostrarCircuito() {
        int selectedIndex = circuitoVista.getCircuitosComboBox().getSelectedIndex();
        if (selectedIndex >= 0) {
            Circuito circuitoSeleccionado = circuitos.getCircuito(selectedIndex);
            try {
                circuito = new CircuitoVista();
                circuito.getCircuitoArea().setText(circuitoSeleccionado.toString());

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(app, "Elija un circuito válido.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
        circuito.setVisible(true);
    }
    
    private void mostrarEjercicios() {
        ejercicioVista = new EjerciciosVista();

        for (Ejercicio ejercicio : ejercicios.getAll() ) {
            ejercicioVista.getEjerciciosComboBox().addItem(ejercicio.getNombre());
        }
        // Agregar listener al botón de agregar al carrito
        ejercicioVista.getEjercicioButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mostrarEjercicio();
            }
        });

        ejercicioVista.setVisible(true);
    }
    
    private void mostrarEjercicio() {
        int selectedIndex = ejercicioVista.getEjerciciosComboBox().getSelectedIndex();
        if (selectedIndex >= 0) {
            Ejercicio ejercicioSeleccionado = ejercicios.getEjercicio(selectedIndex);
            try {
                ejercicio = new EjercicioVista();
                ejercicio.getEjercicioArea().setText(ejercicioSeleccionado.toString());

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(app, "Elija un ejercicio válido.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
        ejercicio.setVisible(true);
    }
    
    private void mostrarUsuarios() {
        usuarioVista = new UsuariosVista();

        for (Usuario usuario : usuarios.getAll() ) {
            usuarioVista.getUsuariosComboBox().addItem(usuario.getNombre());
        }
        // Agregar listener al botón de agregar al carrito
        usuarioVista.getUsuarioButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mostrarUsuario();
            }
        });

        usuarioVista.setVisible(true);
    }
    
    private void mostrarUsuario() {
        int selectedIndex = usuarioVista.getUsuariosComboBox().getSelectedIndex();
        if (selectedIndex >= 0) {
            Usuario usuarioSeleccionado = usuarios.getUsuario(selectedIndex);
            try {
                usuario = new UsuarioVista();
                usuario.getUsuarioArea().setText(usuarioSeleccionado.toString());

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(app, "Elija un usuario válido.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
        usuario.setVisible(true);
    }
}