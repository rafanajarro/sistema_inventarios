package com.inventario.sistemainvetario.model;

import java.time.LocalDateTime;

public interface ProductoActividad {
    LocalDateTime getFechaMovimiento();

    Integer getCantidad();

    String getTipoMovimiento();

    String getDescripcion();
}
