package com.inventario.sistemainvetario.repository;

import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.inventario.sistemainvetario.model.Categorias;

public interface CategoriaRepository extends JpaRepository<Categorias, Integer> {

    @Query(value = "SELECT C.NOMBRE AS CATEGORIA, SUM(I.CANTIDAD) AS STOCK_TOTAL FROM INVENTARIO I INNER JOIN PRODUCTO P ON I.ID_PRODUCTO = P.ID_PRODUCTO INNER JOIN CATEGORIA C ON P.ID_CATEGORIA = C.ID_CATEGORIA WHERE I.ID_MOVIMIENTO IN (1, 3, 6) GROUP BY C.NOMBRE", nativeQuery = true)
    List<Map<String, Object>> findStockCategoria();
}