package com.inventario.sistemainvetario.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.inventario.sistemainvetario.model.MedidasLongitud;
import com.inventario.sistemainvetario.service.MedidasLongitudService;

@Controller
public class MedidasLongitudController {

    @Autowired
    private MedidasLongitudService medidasLongitudService;

    //
    @GetMapping("/obtenerLongitudesSelect")
    public ResponseEntity<?> obtenerLongitudes() {
        List<MedidasLongitud> medidasLongituds = medidasLongitudService.obtenerTodos();
        return ResponseEntity.ok(medidasLongituds);
    }

    // Mostrar
    @GetMapping("/longitud")
    public String listarTodos(Model model) {
        List<MedidasLongitud> longitudes = medidasLongitudService.obtenerTodos();
        model.addAttribute("longitudes", longitudes);
        return "unidades/listado_longitudes";
    }

    // Form nuevo
    @GetMapping("/longitud/nuevo")
    public String mostrarFormularioNuevo(Model model) {
        model.addAttribute("longitudes", new MedidasLongitud());
        return "unidades/crear_editar_longitudes";
    }

    // Guardar
    @PostMapping("/longitud/crear")
    public String guardar(@ModelAttribute MedidasLongitud longitud, RedirectAttributes redirectAttributes) {
        try {
            medidasLongitudService.guardar(longitud);
            redirectAttributes.addFlashAttribute("mensaje", "El valor se guardó correctamente.");
            redirectAttributes.addFlashAttribute("tipoMensaje", "success");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("mensaje", "Ocurrió un error al guardar el valor." + e.getMessage());
            redirectAttributes.addFlashAttribute("tipoMensaje", "error");
        }
        return "redirect:/longitud";
    }

    // Editar
    @PostMapping("/longitud/editar/{id}")
    public String editarRol(@PathVariable int id, RedirectAttributes redirectAttributes,
            @ModelAttribute MedidasLongitud longitud) {
        try {
            MedidasLongitud rolUsuario = medidasLongitudService.obtenerPorId(id);
            if (rolUsuario == null) {
                return "redirect:/longitud";
            }
            rolUsuario.setTitulo(longitud.getTitulo());
            medidasLongitudService.guardar(rolUsuario);

            redirectAttributes.addFlashAttribute("mensaje", "El valor se editó correctamente.");
            redirectAttributes.addFlashAttribute("tipoMensaje", "success");
            return "redirect:/longitud";

        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("mensaje", "Ocurrió un error al editar el valor.");
            redirectAttributes.addFlashAttribute("tipoMensaje", "error");
        }
        return "redirect:/longitud";
    }

    // Form editar
    @GetMapping("/longitud/editar/{id}")
    public String editarRolForm(@PathVariable int id, Model model) {
        MedidasLongitud medLong = medidasLongitudService.obtenerPorId(id);
        if (medLong == null) {
            return "redirect:/longitud";
        }
        model.addAttribute("longitudes", medLong);
        return "unidades/crear_editar_longitudes";
    }

    // Eliminar
    @GetMapping("/longitud/eliminar/{id}")
    public String eliminarParametro(@PathVariable Integer id, RedirectAttributes redirectAttributes) {
        try {
            medidasLongitudService.eliminar(id);
            redirectAttributes.addFlashAttribute("mensaje", "El valor se eliminó correctamente.");
            redirectAttributes.addFlashAttribute("tipoMensaje", "success");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("mensaje", "Ocurrió un error al eliminar el valor.");
            redirectAttributes.addFlashAttribute("tipoMensaje", "error");
        }
        return "redirect:/longitud";
    }
}
