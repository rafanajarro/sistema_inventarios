
package com.inventario.sistemainvetario.repository;

import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.inventario.sistemainvetario.model.Producto;
import com.inventario.sistemainvetario.model.ProductoActividad;

public interface ProductoRepository extends JpaRepository<Producto, Integer> {

    @Query(value = "SELECT P.NOMBRE, P.STOCK_MINIMO, SUM(I.CANTIDAD) AS STOCK_ACTUAL FROM PRODUCTO P INNER JOIN INVENTARIO I ON P.ID_PRODUCTO = I.ID_PRODUCTO GROUP BY P.NOMBRE, P.STOCK_MINIMO HAVING SUM(I.CANTIDAD) < P.STOCK_MINIMO", nativeQuery = true)
    List<Map<String, Object>> findStockBajo();

    @Query(value = "SELECT TOP 5 " +
            "i.FECHA_MOVIMIENTO AS fechaMovimiento, " +
            "i.CANTIDAD AS cantidad, " +
            "m.TITULO AS tipoMovimiento, " +
            "i.DESCRIPCION AS descripcion " +
            "FROM INVENTARIO i " +
            "INNER JOIN MOVIMIENTOS m ON i.ID_MOVIMIENTO = m.ID_MOVIMIENTO " +
            "WHERE i.ID_PRODUCTO = :idProducto " +
            "ORDER BY i.FECHA_MOVIMIENTO DESC", nativeQuery = true)
    List<ProductoActividad> findActividadReciente(@Param("idProducto") Integer idProducto);

    @Query(value = "SELECT SUM(CASE WHEN i.ID_MOVIMIENTO IN (1, 3, 5) THEN i.CANTIDAD    WHEN    i.ID_MOVIMIENTO IN (2, 4, 6) THEN -i.CANTIDAD    ELSE 0 END) AS stock_actual FROM INVENTARIO i WHERE i.ID_PRODUCTO = :idProducto", nativeQuery = true)
    String findCantidadTotalProducto(@Param("idProducto") Integer idProducto);

    @Query(value = "SELECT YEAR(i.FECHA_MOVIMIENTO) AS anio, " +
            "MONTH(i.FECHA_MOVIMIENTO) AS mes, " +
            "SUM(CASE WHEN m.TITULO IN ('Entrada de inventario', 'Ajuste positivo', 'Devolución de proveedor') THEN i.CANTIDAD ELSE 0 END) AS entradas, "
            +
            "SUM(CASE WHEN m.TITULO IN ('Salida de inventario', 'Ajuste negativo', 'Devolución de cliente') THEN i.CANTIDAD ELSE 0 END) AS salidas "
            +
            "FROM INVENTARIO i " +
            "INNER JOIN MOVIMIENTOS m ON i.ID_MOVIMIENTO = m.ID_MOVIMIENTO " +
            "WHERE i.ID_PRODUCTO = :idProducto " +
            "GROUP BY YEAR(i.FECHA_MOVIMIENTO), MONTH(i.FECHA_MOVIMIENTO) " +
            "ORDER BY anio DESC, mes DESC", nativeQuery = true)
    List<Map<String, Object>> findMovimientosProducto(@Param("idProducto") Integer idProducto);

}
