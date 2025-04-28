package com.inventario.sistemainvetario.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.inventario.sistemainvetario.model.UnidadesMedida;
import com.inventario.sistemainvetario.service.UnidadesMedidaService;

@Controller
public class UnidadesMedidaController {

    @Autowired
    private UnidadesMedidaService unidadesMedidaService;

    // Mostrar
    @GetMapping("/unidades")
    public String listarTodos(Model model) {
        List<UnidadesMedida> unidades = unidadesMedidaService.obtenerTodos();
        model.addAttribute("unidades", unidades);
        return "unidades/listado_unidades";
    }

    // Form nuevo
    @GetMapping("/unidades/nuevo")
    public String mostrarFormularioNuevo(Model model) {
        model.addAttribute("unidades", new UnidadesMedida());
        return "unidades/crear_editar_unidades";
    }

    // Guardar
    @PostMapping("/unidades/crear")
    public String guardar(@ModelAttribute UnidadesMedida unidades, RedirectAttributes redirectAttributes) {
        try {
            unidadesMedidaService.guardar(unidades);
            redirectAttributes.addFlashAttribute("mensaje", "El valor se guardó correctamente.");
            redirectAttributes.addFlashAttribute("tipoMensaje", "success");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("mensaje", "Ocurrió un error al guardar el valor." + e.getMessage());
            redirectAttributes.addFlashAttribute("tipoMensaje", "error");
        }
        return "redirect:/unidades";
    }

    // Editar
    @PostMapping("/unidades/editar/{id}")
    public String editarRol(@PathVariable int id, RedirectAttributes redirectAttributes,
            @ModelAttribute UnidadesMedida unidades) {
        try {
            UnidadesMedida rolUsuario = unidadesMedidaService.obtenerPorId(id);
            if (rolUsuario == null) {
                return "redirect:/unidades";
            }
            rolUsuario.setTitulo(unidades.getTitulo());
            unidadesMedidaService.guardar(rolUsuario);

            redirectAttributes.addFlashAttribute("mensaje", "El valor se editó correctamente.");
            redirectAttributes.addFlashAttribute("tipoMensaje", "success");
            return "redirect:/unidades";

        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("mensaje", "Ocurrió un error al editar el valor.");
            redirectAttributes.addFlashAttribute("tipoMensaje", "error");
        }
        return "redirect:/unidades";
    }

    // Form editar
    @GetMapping("/unidades/editar/{id}")
    public String editarRolForm(@PathVariable int id, Model model) {
        UnidadesMedida unidadesMedida = unidadesMedidaService.obtenerPorId(id);
        if (unidadesMedida == null) {
            return "redirect:/unidades";
        }
        model.addAttribute("unidades", unidadesMedida);
        return "unidades/crear_editar_unidades";
    }

    // Eliminar
    @GetMapping("/unidades/eliminar/{id}")
    public String eliminarParametro(@PathVariable Integer id, RedirectAttributes redirectAttributes) {
        try {
            unidadesMedidaService.eliminar(id);
            redirectAttributes.addFlashAttribute("mensaje", "El valor se eliminó correctamente.");
            redirectAttributes.addFlashAttribute("tipoMensaje", "success");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("mensaje", "Ocurrió un error al eliminar el valor.");
            redirectAttributes.addFlashAttribute("tipoMensaje", "error");
        }
        return "redirect:/unidades";
    }
}
