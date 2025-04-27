/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package juego.vistas;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

/**
 *
 * @author alumno
 */
public class EjercicioVista extends JFrame{
    private String nombre;
    private String descripcion;
    private JTextArea ejercicioArea;

    public EjercicioVista() {
        setTitle("Ejercicio");
        setSize(300, 300);
        setLayout(new BorderLayout());
        // Panel superior para selección de libros
        JPanel panelSuperior = new JPanel();
        panelSuperior.setLayout(new FlowLayout());
 
        ejercicioArea = new JTextArea();
        ejercicioArea.setEditable(false);
        add(new JScrollPane(ejercicioArea), BorderLayout.CENTER);
    
        add(panelSuperior, BorderLayout.NORTH);
      }
    
    public JTextArea getEjercicioArea() {
        return ejercicioArea;
    }
}
