/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package juego.vistas;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 *
 * @author alumno
 */
public class UsuariosVista extends JFrame{
    private JButton verUsuarioButton;
    private JComboBox<String> listaUsuariosComboBox;

    public UsuariosVista() {
        setTitle("Lista Usuarios");
        setSize(400, 300);
        setLayout(new BorderLayout());

       // librosInventarioComboBox
        JPanel panelSuperior = new JPanel();
        panelSuperior.setLayout(new FlowLayout());
        listaUsuariosComboBox = new JComboBox<>();
        panelSuperior.add(listaUsuariosComboBox);
        
        add(panelSuperior, BorderLayout.NORTH);
        
        verUsuarioButton = new JButton("Mostrar Datos Usuario");
        add(verUsuarioButton, BorderLayout.SOUTH);
    }

    public JComboBox<String> getUsuariosComboBox() {
        return listaUsuariosComboBox;
    }
       
    public JButton getUsuarioButton() {
        return verUsuarioButton;
    }
 
    public void addUsuario(String proveedor) {
       listaUsuariosComboBox.addItem(proveedor);
    }
}
