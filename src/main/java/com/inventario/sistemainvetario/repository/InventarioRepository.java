package com.inventario.sistemainvetario.repository;

import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.inventario.sistemainvetario.model.Inventario;

public interface InventarioRepository extends JpaRepository<Inventario, Integer> {

    @Query(value = "SELECT P.NOMBRE, SUM(I.CANTIDAD) AS TOTAL_SALIDA FROM INVENTARIO I INNER JOIN PRODUCTO P ON I.ID_PRODUCTO = P.ID_PRODUCTO WHERE I.ID_MOVIMIENTO = 2 GROUP BY P.NOMBRE ORDER BY TOTAL_SALIDA DESC", nativeQuery = true)
    List<Map<String, Object>> findProductosMasVendidos();
}