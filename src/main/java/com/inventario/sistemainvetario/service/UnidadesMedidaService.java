package com.inventario.sistemainvetario.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.inventario.sistemainvetario.model.UnidadesMedida;
import com.inventario.sistemainvetario.repository.UnidadesMedidaRepository;

@Service
public class UnidadesMedidaService {
    @Autowired
    private UnidadesMedidaRepository unidadesMedidaRepository;

    public List<UnidadesMedida> obtenerTodos() {
        return unidadesMedidaRepository.findAll();
    }

    public UnidadesMedida obtenerPorId(Integer id) {
        return unidadesMedidaRepository.findById(id).orElse(null);
    }

    public UnidadesMedida guardar(UnidadesMedida rolUsuario) {
        return unidadesMedidaRepository.save(rolUsuario);
    }

    public void eliminar(Integer id) {
        unidadesMedidaRepository.deleteById(id);
    }
}
