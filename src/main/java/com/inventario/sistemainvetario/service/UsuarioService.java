package com.inventario.sistemainvetario.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.inventario.sistemainvetario.model.Usuarios;
import com.inventario.sistemainvetario.repository.UsuarioRepository;

@Service
public class UsuarioService {
    @Autowired
    private UsuarioRepository usuarioRepository;

    public List<Usuarios> obtenerTodos() {
        return usuarioRepository.findAll();
    }

    public Usuarios obtenerPorId(Integer id) {
        return usuarioRepository.findById(id).orElse(null);
    }

    public Usuarios guardar(Usuarios usuario) {
        return usuarioRepository.save(usuario);
    }

    public void eliminar(Integer id) {
        usuarioRepository.deleteById(id);
    }

    /*
     * public Usuarios obtenerPorUsername(String username) {
     * return usuarioRepository.encontrarPorUsername(username);
     * }
     */
}
