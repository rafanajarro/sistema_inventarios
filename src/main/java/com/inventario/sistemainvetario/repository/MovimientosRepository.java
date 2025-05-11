package com.inventario.sistemainvetario.repository;

import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.inventario.sistemainvetario.model.Movimiento;

public interface MovimientosRepository extends JpaRepository<Movimiento, Integer> {
    
    @Query(value = "SELECT M.TITULO AS TIPO_MOVIMIENTO, COUNT(*) AS TOTAL_MOVIMIENTOS FROM INVENTARIO I INNER JOIN MOVIMIENTOS M ON I.iD_MOVIMIENTO = M.ID_MOVIMIENTO GROUP BY M.TITULO", nativeQuery = true)
    List<Map<String, Object>> findTipoMovimiento();
}
