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
public class CircuitosVista extends JFrame{
    private JButton verCircuitoButton;
    private JComboBox<String> listaCircuitosComboBox;

    public CircuitosVista() {
        setTitle("Lista Circuitos");
        setSize(400, 300);
        setLayout(new BorderLayout());

       // librosInventarioComboBox
        JPanel panelSuperior = new JPanel();
        panelSuperior.setLayout(new FlowLayout());
        listaCircuitosComboBox = new JComboBox<>();
        panelSuperior.add(listaCircuitosComboBox);
        
        add(panelSuperior, BorderLayout.NORTH);
        
        verCircuitoButton = new JButton("Mostrar Datos Circuito");
        add(verCircuitoButton, BorderLayout.SOUTH);
    }

    public JComboBox<String> getCircuitosComboBox() {
        return listaCircuitosComboBox;
    }
       
    public JButton getCircuitoButton() {
        return verCircuitoButton;
    }
 
    public void addCircuito(String proveedor) {
       listaCircuitosComboBox.addItem(proveedor);
    }
}
