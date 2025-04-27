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
public class UsuarioVista extends JFrame{
    private String nombre;
    private String correo;
    private JTextArea usuarioArea;

    public UsuarioVista() {
        setTitle("Usuario");
        setSize(300, 300);
        setLayout(new BorderLayout());
        // Panel superior para selección de libros
        JPanel panelSuperior = new JPanel();
        panelSuperior.setLayout(new FlowLayout());
 
        usuarioArea = new JTextArea();
        usuarioArea.setEditable(false);
        add(new JScrollPane(usuarioArea), BorderLayout.CENTER);
    
        add(panelSuperior, BorderLayout.NORTH);
      }
    
    public JTextArea getUsuarioArea() {
        return usuarioArea;
    }
}
