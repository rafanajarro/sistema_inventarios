package com.inventario.sistemainvetario.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.inventario.sistemainvetario.model.Movimiento;
import com.inventario.sistemainvetario.repository.MovimientosRepository;

@Service
public class MovimientoService {
    @Autowired
    private MovimientosRepository movimientosRepository;

    public List<Map<String, Object>> obtenerTipoMovimiento() {
        return movimientosRepository.findTipoMovimiento();
    }

    public List<Movimiento> obtenerTodos() {
        return movimientosRepository.findAll();
    }

    public Movimiento obtenerPorId(Integer id) {
        return movimientosRepository.findById(id).orElse(null);
    }

    public void guardar(Movimiento inventario) {
        movimientosRepository.save(inventario);
    }

    public void eliminar(Integer id) {
        movimientosRepository.deleteById(id);
    }
}
