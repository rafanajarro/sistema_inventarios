
package com.inventario.sistemainvetario.repository;

import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.inventario.sistemainvetario.model.Producto;

public interface ProductoRepository extends JpaRepository<Producto, Integer> {

    @Query(value = "SELECT P.NOMBRE, P.STOCK_MINIMO, SUM(I.CANTIDAD) AS STOCK_ACTUAL FROM  PRODUCTO P INNER JOIN INVENTARIO I ON P.ID_PRODUCTO = I.ID_PRODUCTO GROUP BY P.NOMBRE, P.STOCK_MINIMO HAVING SUM(I.CANTIDAD) < P.STOCK_MINIMO", nativeQuery = true)
    List<Map<String, Object>> findStockBajo();
}
