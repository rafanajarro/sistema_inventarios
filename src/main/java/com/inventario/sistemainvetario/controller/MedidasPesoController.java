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

import com.inventario.sistemainvetario.model.MedidasPeso;
import com.inventario.sistemainvetario.service.MedidasPesoService;

@Controller
public class MedidasPesoController {

    @Autowired
    private MedidasPesoService medidasPesoService;

    // Mostrar
    @GetMapping("/peso")
    public String listarTodos(Model model) {
        List<MedidasPeso> pesos = medidasPesoService.obtenerTodos();
        model.addAttribute("pesos", pesos);
        return "unidades/listado_pesos";
    }

    // Form nuevo
    @GetMapping("/peso/nuevo")
    public String mostrarFormularioNuevo(Model model) {
        model.addAttribute("pesos", new MedidasPeso());
        return "unidades/crear_editar_pesos";
    }

    // Guardar
    @PostMapping("/peso/crear")
    public String guardar(@ModelAttribute MedidasPeso peso, RedirectAttributes redirectAttributes) {
        try {
            System.out.println("peso: " + peso);
            medidasPesoService.guardar(peso);
            redirectAttributes.addFlashAttribute("mensaje", "El valor se guardó correctamente.");
            redirectAttributes.addFlashAttribute("tipoMensaje", "success");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("mensaje", "Ocurrió un error al guardar el valor." + e.getMessage());
            redirectAttributes.addFlashAttribute("tipoMensaje", "error");
        }
        return "redirect:/peso";
    }

    // Editar
    @PostMapping("/peso/editar/{id}")
    public String editarRol(@PathVariable int id, RedirectAttributes redirectAttributes,
            @ModelAttribute MedidasPeso peso) {
        try {
            MedidasPeso rolUsuario = medidasPesoService.obtenerPorId(id);
            if (rolUsuario == null) {
                return "redirect:/peso";
            }
            rolUsuario.setTitulo(peso.getTitulo());
            medidasPesoService.guardar(rolUsuario);

            redirectAttributes.addFlashAttribute("mensaje", "El valor se editó correctamente.");
            redirectAttributes.addFlashAttribute("tipoMensaje", "success");
            return "redirect:/peso";

        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("mensaje", "Ocurrió un error al editar el valor.");
            redirectAttributes.addFlashAttribute("tipoMensaje", "error");
        }
        return "redirect:/peso";
    }

    // Form editar
    @GetMapping("/peso/editar/{id}")
    public String editarRolForm(@PathVariable int id, Model model) {
        MedidasPeso medLong = medidasPesoService.obtenerPorId(id);
        if (medLong == null) {
            return "redirect:/peso";
        }
        model.addAttribute("pesos", medLong);
        return "unidades/crear_editar_pesos";
    }

    // Eliminar
    @GetMapping("/peso/eliminar/{id}")
    public String eliminarParametro(@PathVariable Integer id, RedirectAttributes redirectAttributes) {
        try {
            medidasPesoService.eliminar(id);
            redirectAttributes.addFlashAttribute("mensaje", "El valor se eliminó correctamente.");
            redirectAttributes.addFlashAttribute("tipoMensaje", "success");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("mensaje", "Ocurrió un error al eliminar el valor.");
            redirectAttributes.addFlashAttribute("tipoMensaje", "error");
        }
        return "redirect:/peso";
    }
}
