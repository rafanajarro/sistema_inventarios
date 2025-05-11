package com.inventario.sistemainvetario.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

import org.hibernate.annotations.UpdateTimestamp;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "INVENTARIO")
public class Inventario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_INVENTARIO")
    private Integer idInventario;

    @ManyToOne
    @JoinColumn(name = "ID_PRODUCTO", referencedColumnName = "ID_PRODUCTO", nullable = false)
    private Producto idProducto;

    @Column(name = "CANTIDAD", nullable = false)
    private Integer cantidad;

    @ManyToOne
    @JoinColumn(name = "ID_MOVIMIENTO", referencedColumnName = "ID_MOVIMIENTO", nullable = false)
    private Movimiento idMovimiento;

    @Column(name = "DESCRIPCION", length = 255)
    private String descripcion;

    @UpdateTimestamp
    @Column(name = "FECHA_MOVIMIENTO", nullable = false)
    private LocalDateTime fechaMovimiento;

    @Column(name = "USUARIO_CREA", nullable = true)
    private String usuarioCrea;

    @Column(name = "USUARIO_MOD", nullable = true)
    private String usuarioMod;
}