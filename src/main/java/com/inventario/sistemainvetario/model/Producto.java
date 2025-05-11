package com.inventario.sistemainvetario.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "PRODUCTO")
public class Producto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_PRODUCTO")
    private Integer idProducto;

    @Column(name = "NOMBRE", nullable = false, length = 100)
    private String nombre;

    @Column(name = "DESCRIPCION", length = 255)
    private String descripcion;

    @ManyToOne
    @JoinColumn(name = "ID_CATEGORIA", referencedColumnName = "ID_CATEGORIA", nullable = false)
    private Categorias idCategoria;

    @Column(name = "PRECIO_COMPRA", nullable = false)
    private BigDecimal precioCompra;

    @Column(name = "PRECIO_VENTA", nullable = false)
    private BigDecimal precioVenta;

    @Column(name = "STOCK_MINIMO", nullable = false)
    private Integer stockMinimo;

    @Column(name = "IMAGEN", length = 150)
    private String imagen;

    @Column(name = "ESTADO", length = 1, nullable = false)
    private String estado;

    @CreationTimestamp
    @Column(name = "FECHA_CREA", nullable = false)
    private LocalDateTime fechaCrea;

    @UpdateTimestamp
    @Column(name = "FECHA_MOD")
    private LocalDateTime fechaMod;

    @Column(name = "UNIDAD_MEDIDA", nullable = false)
    private String unidadMedida;

    @Column(name = "USUARIO_CREA", nullable = true)
    private String usuarioCrea;

    @Column(name = "USUARIO_MOD", nullable = true)
    private String usuarioMod;
}
