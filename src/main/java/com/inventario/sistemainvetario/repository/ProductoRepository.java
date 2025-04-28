

package com.inventario.sistemainvetario.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.inventario.sistemainvetario.model.Producto;

public interface ProductoRepository extends JpaRepository<Producto, Integer> {
}
