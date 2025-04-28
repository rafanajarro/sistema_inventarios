package com.inventario.sistemainvetario.repository;


import org.springframework.data.jpa.repository.JpaRepository;


import com.inventario.sistemainvetario.model.Categorias;
public interface CategoriaRepository extends JpaRepository<Categorias, Integer>{

    
} 