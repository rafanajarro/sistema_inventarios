package com.inventario.sistemainvetario.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.inventario.sistemainvetario.model.Movimiento;
import com.inventario.sistemainvetario.service.MovimientoService;

@Controller
public class MovimientoController {

    @Autowired
    private MovimientoService movimientoService;

    @GetMapping("/tipoMovimientos")
    public ResponseEntity<?> obtenerTipoMovimientos() {
        List<Map<String, Object>> resumen = movimientoService.obtenerTipoMovimiento();
        return ResponseEntity.ok(resumen);
    }

    // Mostrar
    @GetMapping("/movimiento")
    public String listarMovimientos(Model model) {
        List<Movimiento> movimientos = movimientoService.obtenerTodos();
        model.addAttribute("movimientos", movimientos);
        return "movimientos/listado_movimientos";
    }

    // Formulario nueva
    @GetMapping("/movimientos/nueva")
    public String mostrarFormularioMovimiento(Model model) {
        model.addAttribute("movimientos", new Movimiento());
        return "movimientos/crear_editar_movimientos";
    }

    // Guardar
    @PostMapping("/movimientos/crear")
    public String guardarMovimiento(@ModelAttribute Movimiento movimiento, RedirectAttributes redirectAttributes) {
        try {
            movimientoService.guardar(movimiento);
            redirectAttributes.addFlashAttribute("mensaje", "El movimiento se guardó correctamente.");
            redirectAttributes.addFlashAttribute("tipoMensaje", "success");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("mensaje", "Ocurrió un error al guardar el movimiento.");
            redirectAttributes.addFlashAttribute("tipoMensaje", "error");
        }
        return "redirect:/movimiento";
    }

    // Formulario editar
    @GetMapping("/movimientos/editar/{id}")
    public String editarMovimientoForm(@PathVariable Integer id, Model model) {
        Movimiento movimiento = movimientoService.obtenerPorId(id);
        if (movimiento == null) {
            return "redirect:/movimiento";
        }
        model.addAttribute("movimientos", movimiento);
        return "movimientos/crear_editar_movimientos";
    }

    // Editar
    @PostMapping("/movimientos/editar/{id}")
    public String editarMovimiento(@PathVariable Integer id, @ModelAttribute Movimiento movimiento,
            RedirectAttributes redirectAttributes) {
        try {
            Movimiento movimientoExistente = movimientoService.obtenerPorId(id);
            if (movimientoExistente == null) {
                return "redirect:/movimiento";
            }

            movimientoExistente.setTitulo(movimiento.getTitulo());

            movimientoService.guardar(movimientoExistente);
            redirectAttributes.addFlashAttribute("mensaje", "El movimiento se actualizó correctamente.");
            redirectAttributes.addFlashAttribute("tipoMensaje", "success");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("mensaje", "Ocurrió un error al editar el movimiento.");
            redirectAttributes.addFlashAttribute("tipoMensaje", "error");
        }
        return "redirect:/movimiento";
    }

    // Eliminar
    @GetMapping("/movimientos/eliminar/{id}")
    public String eliminarMovimientos(@PathVariable Integer id, RedirectAttributes redirectAttributes) {
        try {
            movimientoService.eliminar(id);
            redirectAttributes.addFlashAttribute("mensaje", "El movimiento se eliminó correctamente.");
            redirectAttributes.addFlashAttribute("tipoMensaje", "success");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("mensaje", "Ocurrió un error al eliminar el movimiento.");
            redirectAttributes.addFlashAttribute("tipoMensaje", "error");
        }
        return "redirect:/categoria";
    }
}
