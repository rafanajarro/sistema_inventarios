package com.inventario.sistemainvetario.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.inventario.sistemainvetario.model.RolUsuario;
import com.inventario.sistemainvetario.service.RolUsuarioService;

@Controller
@RequestMapping("/roles")
public class RolUsuarioController {
    @Autowired
    private RolUsuarioService rolUsuarioService;

    // Mostrar roles
    @GetMapping("/obtenerTodos")
    public String listarTodos(Model model) {

        List<RolUsuario> roles = rolUsuarioService.obtenerTodos();

        model.addAttribute("roles", roles);

        return "rol/listado_roles";
    }

    // Form nuevo rol
    @GetMapping("/nuevo")
    public String mostrarFormularioRol(Model model) {
        model.addAttribute("rol", new RolUsuario());
        return "rol/creareditarrol";
    }

    // Guardar rol
    @PostMapping("/crear")
    public String guardarRol(@ModelAttribute RolUsuario rol, RedirectAttributes redirectAttributes) {
        try {
            //rolUsuarioService.crearRol(rol);
            redirectAttributes.addFlashAttribute("mensaje", "El rol se guardó correctamente.");
            redirectAttributes.addFlashAttribute("tipoMensaje", "success");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("mensaje", "Ocurrió un error al guardar el rol.");
            redirectAttributes.addFlashAttribute("tipoMensaje", "error");
        }
        return "redirect:/roles/obtenerTodos";
    }

    // Editar rol
    @PostMapping("/editar/{id}")
    public String editarRol(@PathVariable int id, RedirectAttributes redirectAttributes,
            @ModelAttribute RolUsuario rol) {
        /*try {
            Optional<RolUsuario> optional = rolUsuarioService.obtenerDataModificar(id);

            if (optional.isPresent()) {
                RolUsuario rolExistente = optional.orElse(new RolUsuario());
                rolExistente.setDESCRIPCION(rol.getDESCRIPCION());
                rolExistente.setESTADO(rol.getESTADO());
                rolUsuarioService.modificarRol(rolExistente);
                redirectAttributes.addFlashAttribute("mensaje", "El rol se editó correctamente.");
                redirectAttributes.addFlashAttribute("tipoMensaje", "success");
                return "redirect:/roles/obtenerTodos";
            }

        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("mensaje", "Ocurrió un error al editar el rol.");
            redirectAttributes.addFlashAttribute("tipoMensaje", "error");
        }*/
        return "redirect:/roles/obtenerTodos";
    }

    // Form editar rol
    @GetMapping("/editar/{id}")
    public String editarRolForm(@PathVariable int id, Model model) {
        /*try {
            Optional<RolUsuario> rol = rolUsuarioService.obtenerDataModificar(id);

            if (rol.isPresent()) {
                model.addAttribute("rol", rol.get());
            }

            return "rol/creareditarrol";
        } catch (Exception e) {
            return "redirect:/roles/obtenerTodos";
        }*/
        return "redirect:/roles/obtenerTodos";
    }

    // Eliminar rol
    @GetMapping("/eliminar/{id}")
    public String eliminarParametro(@PathVariable Integer id, RedirectAttributes redirectAttributes) {
        /*try {
            rolUsuarioService.eliminarRol(id);
            redirectAttributes.addFlashAttribute("mensaje", "El rol se eliminó correctamente.");
            redirectAttributes.addFlashAttribute("tipoMensaje", "success");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("mensaje", "Ocurrió un error al eliminar el rol.");
            redirectAttributes.addFlashAttribute("tipoMensaje", "error");
        }*/
        return "redirect:/roles/obtenerTodos";
    }
}
