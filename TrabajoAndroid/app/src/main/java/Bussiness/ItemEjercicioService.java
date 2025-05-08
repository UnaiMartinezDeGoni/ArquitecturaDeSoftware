package Bussiness;

import java.util.List;

import DAO.dao.InventarioItemEjercicio;
import Bussiness.modelo.ItemEjercicio;

public class ItemEjercicioService {
    private InventarioItemEjercicio itemEjercicioRepository;

    public ItemEjercicioService(){
        this.itemEjercicioRepository = new InventarioItemEjercicio();
    }

    public String insert(ItemEjercicio cirItem){
        itemEjercicioRepository.insert(cirItem);
        return cirItem.getObjectId();
    }

    /*public boolean update(CircuitoItemEjercicio employee) {
        circuitoItemEjercicioRepository.update(employee);
        return true;
    }*/
}
