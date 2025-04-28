package com.inventario.sistemainvetario.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.inventario.sistemainvetario.model.Inventario;

public interface InventarioRepository extends JpaRepository<Inventario, Integer> {
}