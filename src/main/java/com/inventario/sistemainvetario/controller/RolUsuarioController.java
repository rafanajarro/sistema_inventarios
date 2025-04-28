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

import com.inventario.sistemainvetario.model.RolUsuario;
import com.inventario.sistemainvetario.service.RolUsuarioService;

@Controller
public class RolUsuarioController {
    @Autowired
    private RolUsuarioService rolUsuarioService;

    // Mostrar roles
    @GetMapping("/roles")
    public String listarTodos(Model model) {
        List<RolUsuario> roles = rolUsuarioService.obtenerTodos();
        model.addAttribute("roles", roles);
        return "rol/listado_roles";
    }

    // Form nuevo rol
    @GetMapping("/roles/nuevo")
    public String mostrarFormularioRol(Model model) {
        model.addAttribute("rol", new RolUsuario());
        return "rol/crear_editar_rol";
    }

    // Guardar rol
    @PostMapping("/roles/crear")
    public String guardarRol(@ModelAttribute RolUsuario rol, RedirectAttributes redirectAttributes) {
        try {
            rolUsuarioService.guardar(rol);
            redirectAttributes.addFlashAttribute("mensaje", "El rol se guardó correctamente.");
            redirectAttributes.addFlashAttribute("tipoMensaje", "success");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("mensaje", "Ocurrió un error al guardar el rol.");
            redirectAttributes.addFlashAttribute("tipoMensaje", "error");
        }
        return "redirect:/roles";
    }

    // Editar rol
    @PostMapping("/roles/editar/{id}")
    public String editarRol(@PathVariable int id, RedirectAttributes redirectAttributes,
            @ModelAttribute RolUsuario rol) {
        try {
            RolUsuario rolUsuario = rolUsuarioService.obtenerPorId(id);
            if (rolUsuario == null) {
                return "redirect:/roles";
            }
            rolUsuario.setDescripcion(rol.getDescripcion());
            rolUsuario.setEstado(rol.getEstado());
            rolUsuarioService.guardar(rolUsuario);
            redirectAttributes.addFlashAttribute("mensaje", "El rol se editó correctamente.");
            redirectAttributes.addFlashAttribute("tipoMensaje", "success");
            return "redirect:/roles";

        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("mensaje", "Ocurrió un error al editar el rol.");
            redirectAttributes.addFlashAttribute("tipoMensaje", "error");
        }
        return "redirect:/roles";
    }

    // Form editar rol
    @GetMapping("/roles/editar/{id}")
    public String editarRolForm(@PathVariable int id, Model model) {
        RolUsuario rolUsuario = rolUsuarioService.obtenerPorId(id);
        if (rolUsuario == null) {
            return "redirect:/roles";
        }
        model.addAttribute("rol", rolUsuario);
        return "rol/crear_editar_rol";
    }

    // Eliminar rol
    @GetMapping("/roles/eliminar/{id}")
    public String eliminarParametro(@PathVariable Integer id, RedirectAttributes redirectAttributes) {
        try {
            rolUsuarioService.eliminar(id);
            redirectAttributes.addFlashAttribute("mensaje", "El rol se eliminó correctamente.");
            redirectAttributes.addFlashAttribute("tipoMensaje", "success");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("mensaje", "Ocurrió un error al eliminar el rol.");
            redirectAttributes.addFlashAttribute("tipoMensaje", "error");
        }
        return "redirect:/roles";
    }
}
