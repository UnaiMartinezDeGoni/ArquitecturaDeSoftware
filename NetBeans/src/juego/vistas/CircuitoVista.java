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
public class CircuitoVista extends JFrame{
    private String nombre;
    private String dificultad;
    private JTextArea circuitoArea;

    public CircuitoVista() {
        setTitle("Circuito");
        setSize(300, 300);
        setLayout(new BorderLayout());
        // Panel superior para selección de libros
        JPanel panelSuperior = new JPanel();
        panelSuperior.setLayout(new FlowLayout());
 
        circuitoArea = new JTextArea();
        circuitoArea.setEditable(false);
        add(new JScrollPane(circuitoArea), BorderLayout.CENTER);
    
        add(panelSuperior, BorderLayout.NORTH);
      }
    
    public JTextArea getCircuitoArea() {
        return circuitoArea;
    }
}
