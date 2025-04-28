package com.inventario.sistemainvetario.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.inventario.sistemainvetario.model.Inventario;
import com.inventario.sistemainvetario.service.InventarioService;
import com.inventario.sistemainvetario.service.ProductoService;

import java.time.LocalDateTime;
import java.util.List;

@Controller
@RequestMapping("/inventario")
public class InventarioController {

    @Autowired
    private InventarioService inventarioService;

    @Autowired
    private ProductoService productoService;

    @GetMapping
    public String listarInventario(Model model) {
        model.addAttribute("inventarios", inventarioService.obtenerTodos());
        return "inventario/listado_inventario";
    }

    @GetMapping("/nuevo")
    public String mostrarFormularioNuevo(Model model) {
        model.addAttribute("inventario", new Inventario());
        model.addAttribute("productos", productoService.obtenerTodos());
        return "inventario/crear_editar_inventario";
    }

    @PostMapping("/guardar")
    public String guardarInventario(@ModelAttribute Inventario inventario, RedirectAttributes redirectAttributes) {
        try {
            inventario.setFechaMovimiento(LocalDateTime.now());
            inventarioService.guardar(inventario);
            redirectAttributes.addFlashAttribute("mensaje", "Registro guardado exitosamente.");
            redirectAttributes.addFlashAttribute("tipoMensaje", "success");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("mensaje", "Error al guardar el registro.");
            redirectAttributes.addFlashAttribute("tipoMensaje", "error");
        }
        return "redirect:/inventario";
    }

    @GetMapping("/editar/{id}")
    public String mostrarFormularioEditar(@PathVariable Integer id, Model model) {
        Inventario inventario = inventarioService.obtenerPorId(id);
        if (inventario == null) {
            return "redirect:/inventario";
        }
        model.addAttribute("inventario", inventario);
        model.addAttribute("productos", productoService.obtenerTodos());
        return "inventario/crear_editar_inventario";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminarInventario(@PathVariable Integer id, RedirectAttributes redirectAttributes) {
        try {
            inventarioService.eliminar(id);
            redirectAttributes.addFlashAttribute("mensaje", "Registro eliminado exitosamente.");
            redirectAttributes.addFlashAttribute("tipoMensaje", "success");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("mensaje", "Error al eliminar el registro.");
            redirectAttributes.addFlashAttribute("tipoMensaje", "error");
        }
        return "redirect:/inventario";
    }
}