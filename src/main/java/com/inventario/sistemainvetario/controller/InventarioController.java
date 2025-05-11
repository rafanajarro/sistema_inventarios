package com.inventario.sistemainvetario.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.security.core.Authentication;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.inventario.sistemainvetario.model.Inventario;
import com.inventario.sistemainvetario.model.Movimiento;
import com.inventario.sistemainvetario.model.Producto;
import com.inventario.sistemainvetario.service.InventarioService;
import com.inventario.sistemainvetario.service.MovimientoService;
import com.inventario.sistemainvetario.service.ProductoService;

import java.time.LocalDateTime;

@Controller
@RequestMapping("/inventario")
public class InventarioController {

    @Autowired
    private InventarioService inventarioService;

    @Autowired
    private ProductoService productoService;

    @Autowired
    private MovimientoService movimientoService;

    @GetMapping("/productosMasVendidos")
    public ResponseEntity<?> obtenerProductosMasVendidos() {
        List<Map<String, Object>> resumen = inventarioService.obtenerProductosMasVendidos();
        return ResponseEntity.ok(resumen);
    }

    @GetMapping
    public String listarInventario(Model model) {
        model.addAttribute("inventarios", inventarioService.obtenerTodos());
        return "inventario/listado_inventario";
    }

    @GetMapping("/nuevo")
    public String mostrarFormularioNuevo(Model model) {
        List<Producto> productos = productoService.obtenerTodos();
        List<Movimiento> movimientos = movimientoService.obtenerTodos();
        model.addAttribute("inventario", new Inventario());
        model.addAttribute("productos", productos);
        model.addAttribute("movimientos", movimientos);

        return "inventario/crear_editar_inventario";
    }

    @PostMapping("/guardar")
    public String guardarInventario(@ModelAttribute Inventario inventario, RedirectAttributes redirectAttributes) {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String username = authentication != null ? authentication.getName() : "Anónimo";

            inventario.setFechaMovimiento(LocalDateTime.now());
            inventario.setUsuarioCrea(username);
            inventario.setUsuarioMod(username);
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
        model.addAttribute("movimientos", movimientoService.obtenerTodos());
        return "inventario/crear_editar_inventario";
    }

    @PostMapping("/editar/{id}")
    public String editarInventario(@PathVariable Integer id, @ModelAttribute Inventario inventario,
            RedirectAttributes redirectAttributes) {
        try {
            Inventario inventarioExistente = inventarioService.obtenerPorId(id);
            if (inventarioExistente == null) {
                return "redirect:/inventario";
            }
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String username = authentication != null ? authentication.getName() : "Anónimo";

            inventarioExistente.setCantidad(inventario.getCantidad());
            inventarioExistente.setIdProducto(inventario.getIdProducto());
            inventarioExistente.setIdMovimiento(inventario.getIdMovimiento());
            inventarioExistente.setDescripcion(inventario.getDescripcion());
            inventarioExistente.setUsuarioMod(username);
            inventarioService.guardar(inventarioExistente);
            redirectAttributes.addFlashAttribute("mensaje", "El inventario se actualizó correctamente.");
            redirectAttributes.addFlashAttribute("tipoMensaje", "success");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("mensaje",
                    "Ocurrió un error al editar el inventario." + e.getMessage());
            redirectAttributes.addFlashAttribute("tipoMensaje", "error");
        }
        return "redirect:/inventario";
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