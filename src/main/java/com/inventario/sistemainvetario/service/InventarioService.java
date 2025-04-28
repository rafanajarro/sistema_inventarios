package com.inventario.sistemainvetario.service;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.inventario.sistemainvetario.model.Inventario;
import com.inventario.sistemainvetario.repository.InventarioRepository;
import com.inventario.sistemainvetario.service.InventarioService;

@Service
public class InventarioService {

    @Autowired
    private InventarioRepository inventarioRepository;

   
    public List<Inventario> obtenerTodos() {
        return inventarioRepository.findAll();
    }

   
    public Inventario obtenerPorId(Integer id) {
        return inventarioRepository.findById(id).orElse(null);
    }

   
    public void guardar(Inventario inventario) {
        inventarioRepository.save(inventario);
    }

   
    public void eliminar(Integer id) {
        inventarioRepository.deleteById(id);
    }
}