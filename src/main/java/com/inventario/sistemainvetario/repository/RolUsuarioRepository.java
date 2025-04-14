package com.inventario.sistemainvetario.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.inventario.sistemainvetario.model.RolUsuario;

public interface RolUsuarioRepository extends JpaRepository<RolUsuario, Integer> {

    @Query(value = "SELECT * FROM ROL_USUARIOS WHERE ESTADO = :estado", nativeQuery = true)
    List<RolUsuario> encontrarRolesActivos(@Param("estado") String estado);
}
