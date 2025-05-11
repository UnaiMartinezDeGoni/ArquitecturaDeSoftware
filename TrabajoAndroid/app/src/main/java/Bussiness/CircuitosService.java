package Bussiness;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import Bussiness.modelo.Circuito;
import DAO.dao.InventarioCircuitos;


public class CircuitosService {
    private InventarioCircuitos circuitoRepository;

    public CircuitosService(){
        this.circuitoRepository = new InventarioCircuitos();
    }

    public String insert(Circuito cirItem){
        circuitoRepository.insert(cirItem);
        return cirItem.getId();
    }

    /*public boolean update(Circuito employee) {
        circuitoRepository.update(employee);
        return true;
    }*/

    public List<Circuito> getAll() {
        return circuitoRepository.getAll();
    }

    public ArrayList<Circuito> obtenerCircuitosPorDificultad(String filtro){
        try {
            return circuitoRepository.obtenerCircuitosPorDificultad(filtro);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public Circuito obtenerCircuitoPorId(String objectId){
        return obtenerCircuitoPorId(objectId);
    }
}
