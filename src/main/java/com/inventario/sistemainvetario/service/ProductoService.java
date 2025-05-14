package com.inventario.sistemainvetario.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.inventario.sistemainvetario.model.Producto;
import com.inventario.sistemainvetario.model.ProductoActividad;
import com.inventario.sistemainvetario.repository.ProductoRepository;

@Service
public class ProductoService {
    @Autowired
    private ProductoRepository productoRepository;

        public List<Map<String, Object>> obtenerExcesoStock() {
        return productoRepository.findExcesoStock();
    }

    public String obtenerCantidadTotalProducto(Integer idProducto) {
        return productoRepository.findCantidadTotalProducto(idProducto);
    }

    public List<Map<String, Object>> obtenerMovimientosProducto(Integer idProducto) {
        return productoRepository.findMovimientosProducto(idProducto);
    }

    public List<ProductoActividad> obtenerUltimaActividadProducto(Integer idProducto) {
        return productoRepository.findActividadReciente(idProducto);
    }

    public List<Map<String, Object>> obtenerStockBajo() {
        return productoRepository.findStockBajo();
    }

    public List<Producto> obtenerTodos() {
        return productoRepository.findAll();
    }

    public Producto guardar(Producto producto) {
        return productoRepository.save(producto);
    }

    public Producto obtenerPorId(Integer id) {
        return productoRepository.findById(id).orElse(null);
    }

    public void eliminar(Integer id) {
        productoRepository.deleteById(id);
    }

}
