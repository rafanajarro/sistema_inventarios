package com.inventario.sistemainvetario.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.inventario.sistemainvetario.model.Empresa;
import com.inventario.sistemainvetario.repository.EmpresaRepository;

@Service
public class EmpresaService {

    @Autowired
    private EmpresaRepository empresaRepository;

    public List<Empresa> obtenerTodos() {
        return empresaRepository.findAll();
    }

    public Empresa obtenerPorId(Integer id) {
        return empresaRepository.findById(id).orElse(null);
    }

    public Empresa guardar(Empresa usuario) {
        return empresaRepository.save(usuario);
    }

    public void eliminar(Integer id) {
        empresaRepository.deleteById(id);
    }

    public long contarEmpresas() {
        return empresaRepository.count();
    }
}
