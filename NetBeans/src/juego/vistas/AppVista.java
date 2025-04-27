/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package juego.vistas;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

/**
 *
 * @author alumno
 */
public class AppVista extends JFrame{
    private JButton circuitoButton;
    private JButton ejercicioButton;
    private JButton usuarioButton;
    private JPanel botonesPanel;
    
    public AppVista(){
        setTitle("Tienda de Libros");
        setSize(600, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        
        // Crear los botones
        circuitoButton = new JButton("Mostrar Circuitos");
        ejercicioButton = new JButton("Mostrar Ejercicios");
        usuarioButton = new JButton("Mostrar Usuarios");

        // Crear un panel para los botones
        botonesPanel = new JPanel();
        botonesPanel.setLayout(new FlowLayout()); // Usar FlowLayout o cualquier otro layout

        // Añadir los botones al panel
        botonesPanel.add(circuitoButton);
        botonesPanel.add(ejercicioButton);
        botonesPanel.add(usuarioButton);

        // Añadir el panel con los botones al centro del JFrame
        add(botonesPanel, BorderLayout.CENTER);
    }
    
    public JButton getCircuitoButton() {
        return circuitoButton;
    }
 
    public JButton getEjercicioButton() {
        return ejercicioButton;
    }
    
    public JButton getUsuarioButton() {
        return usuarioButton;
    }
}
