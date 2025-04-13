package com.inventario.sistemainvetario.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.inventario.sistemainvetario.model.Empresa;

public interface EmpresaRepository extends JpaRepository<Empresa, Integer>{

}
