package com.inventario.sistemainvetario.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.inventario.sistemainvetario.model.RolUsuario;
import com.inventario.sistemainvetario.repository.RolUsuarioRepository;

@Service
public class RolUsuarioService {

    @Autowired
    private RolUsuarioRepository rolUsuarioRepository;

    public List<RolUsuario> obtenerTodos() {
        return rolUsuarioRepository.findAll();
    }

    public RolUsuario obtenerPorId(Integer id) {
        return rolUsuarioRepository.findById(id).orElse(null);
    }

    public RolUsuario guardar(RolUsuario rolUsuario) {
        return rolUsuarioRepository.save(rolUsuario);
    }

    public void eliminar(Integer id) {
        rolUsuarioRepository.deleteById(id);
    }

    public List<RolUsuario> obtenerRolesActivos() {
        return rolUsuarioRepository.encontrarRolesActivos("A");
    }
}
