package com.inventario.sistemainvetario.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.inventario.sistemainvetario.model.Usuario;
import com.inventario.sistemainvetario.model.UsuarioActividad;
import com.inventario.sistemainvetario.repository.UsuarioRepository;

@Service
public class UsuarioService {
    @Autowired
    private UsuarioRepository usuarioRepository;

    public List<UsuarioActividad> obtenerUltimaActividadUsuarioActividads(String usuarioMod) {
        return usuarioRepository.findActividadReciente(usuarioMod);
    }

    public List<Usuario> obtenerTodos() {
        return usuarioRepository.findAll();
    }

    public Usuario obtenerPorId(Integer id) {
        return usuarioRepository.findById(id).orElse(null);
    }

    public Usuario guardar(Usuario usuario) {
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
