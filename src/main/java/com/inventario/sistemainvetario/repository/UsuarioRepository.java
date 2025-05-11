package com.inventario.sistemainvetario.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.inventario.sistemainvetario.model.Usuario;
import com.inventario.sistemainvetario.model.UsuarioActividad;

public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {

    Optional<Usuario> findByUsername(String username);

    @Query(value = "SELECT p.NOMBRE, i.CANTIDAD, i.FECHA_MOVIMIENTO, m.TITULO AS    TIPO_MOVIMIENTO FROM    PRODUCTO p    INNER JOIN    INVENTARIO i    ON p.ID_PRODUCTO =    i.ID_PRODUCTO INNER    JOIN MOVIMIENTOS    m ON i.ID_MOVIMIENTO =    m.ID_MOVIMIENTO WHERE i.USUARIO_MOD = :usuarioMod ORDER    BY i.USUARIO_MOD DESC    ", nativeQuery = true)
    List<UsuarioActividad> findActividadReciente(@Param("usuarioMod") String usuarioMod);

}
