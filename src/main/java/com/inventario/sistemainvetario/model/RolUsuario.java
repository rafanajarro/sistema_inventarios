package com.inventario.sistemainvetario.model;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.Entity;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "ROL_USUARIOS")
public class RolUsuario {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_ROL")
    private Integer ID_ROL;

    @Column(name = "DESCRIPCION", length = 25, nullable = false)
    private String DESCRIPCION;

    @Column(name = "ESTADO", length = 1, nullable = false)
    private String ESTADO;

    @CreationTimestamp
    @Column(name = "FECHA_CREA", nullable = false)
    private LocalDateTime FECHA_CREA;

    @UpdateTimestamp
    @Column(name = "FECHA_MOD", nullable = true)
    private LocalDateTime FECHA_MOD;
}
