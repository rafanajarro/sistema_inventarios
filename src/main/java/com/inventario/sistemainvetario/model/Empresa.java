package com.inventario.sistemainvetario.model;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "EMPRESA", schema = "dbo")
public class Empresa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_EMPRESA")
    private Integer idEmpresa;

    @Column(name = "NOMBRE", nullable = false)
    private String nombreEmpresa;

    @Column(name = "PASSWORD", nullable = false)
    private String password;

    @Column(name = "CORREO", nullable = false)
    private String correoEmpresa;

    @Column(name = "RUBRO", nullable = false)
    private String rubroEmpresa;

    @Column(name = "LOGO")
    private String logo;

    @Column(name = "REPRESENTANTE", nullable = false)
    private String representante;

    @Column(name = "DIRECCION", nullable = false)
    private String direccion;

    @Column(name = "NIT", nullable = false, length = 17)
    private String nit;

    @Column(name = "TELEFONO", nullable = false, length = 9)
    private String telefono;

    @Column(name = "FECHA_CREA")
    @CreationTimestamp
    private LocalDateTime fechaCrea;

    @Column(name = "FECHA_MOD")
    @UpdateTimestamp
    private LocalDateTime fechaMod;
}
