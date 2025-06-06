package com.inventario.sistemainvetario.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.inventario.sistemainvetario.model.Categorias;
import com.inventario.sistemainvetario.repository.CategoriaRepository;

@Service
public class CategoriaService {

    @Autowired
    private CategoriaRepository categoriaRepository;

    public List<Map<String, Object>> obtenerStockCategoria() {
        return categoriaRepository.findStockCategoria();
    }

    public List<Categorias> obtenerTodos() {
        return categoriaRepository.findAll();
    }

    public Categorias obtenerPorId(Integer id) {
        return categoriaRepository.findById(id).orElse(null);
    }

    public Categorias guardar(Categorias usuario) {
        return categoriaRepository.save(usuario);
    }

    public void eliminar(Integer id) {
        categoriaRepository.deleteById(id);
    }

    public long contarEmpresas() {
        return categoriaRepository.count();
    }
}
