package com.inventario.sistemainvetario.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.inventario.sistemainvetario.model.MedidasLongitud;
import com.inventario.sistemainvetario.model.MedidasPeso;

public interface MedidasPesoRepository extends JpaRepository<MedidasPeso, Integer> {

}
