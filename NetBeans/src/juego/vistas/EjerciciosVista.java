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
public class EjerciciosVista extends JFrame{
    private JButton verEjercicioButton;
    private JComboBox<String> listaEjerciciosComboBox;

    public EjerciciosVista() {
        setTitle("Lista Ejercicios");
        setSize(400, 300);
        setLayout(new BorderLayout());

       // librosInventarioComboBox
        JPanel panelSuperior = new JPanel();
        panelSuperior.setLayout(new FlowLayout());
        listaEjerciciosComboBox = new JComboBox<>();
        panelSuperior.add(listaEjerciciosComboBox);
        
        add(panelSuperior, BorderLayout.NORTH);
        
        verEjercicioButton = new JButton("Mostrar Datos Ejercicio");
        add(verEjercicioButton, BorderLayout.SOUTH);
    }

    public JComboBox<String> getEjerciciosComboBox() {
        return listaEjerciciosComboBox;
    }
       
    public JButton getEjercicioButton() {
        return verEjercicioButton;
    }
 
    public void addEjercicio(String proveedor) {
       listaEjerciciosComboBox.addItem(proveedor);
    }
}
