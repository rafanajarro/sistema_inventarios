package com.inventario.sistemainvetario.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.inventario.sistemainvetario.model.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Integer>{
    
    Optional<Usuario> findByUsername(String username);
}
