package com.inventario.sistemainvetario.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.inventario.sistemainvetario.model.Usuarios;

public interface UsuarioRepository extends JpaRepository<Usuarios, Integer>{
    
    Optional<Usuarios> findByUsername(String username);
}
