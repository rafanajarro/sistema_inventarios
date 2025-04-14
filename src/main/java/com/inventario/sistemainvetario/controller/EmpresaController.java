package com.inventario.sistemainvetario.controller;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.inventario.sistemainvetario.model.Empresa;
import com.inventario.sistemainvetario.service.EmpresaService;

@Controller
public class EmpresaController {

    @Autowired
    private EmpresaService empresaService;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private Environment environment;
    private static Logger logger = LoggerFactory.getLogger(EmpresaController.class);

    @GetMapping("/empresa")
    public String listarEmpresa(Model model) {
        List<Empresa> empresa = empresaService.obtenerTodos();
        model.addAttribute("empresa", empresa);        
        return "empresas/listar_empresa";
    }

    @GetMapping("/empresa/nuevo")
    public String mostrarFormularioNuevaEmpreas(Model model) {
        model.addAttribute("empresa", new Empresa());
        return "empresas/crear_editar_empresa";
    }

    @PostMapping("/empresa/guardar")
    public String guardarEmpresa(@ModelAttribute Empresa empresa, RedirectAttributes redirect,
            @RequestParam(value = "logoEmpresa", required = false) MultipartFile logo) {
        try {
            // Verificar si ya existe alguna empresa registrada
            if (empresaService.contarEmpresas() > 0) {
                redirect.addFlashAttribute("tipoMensaje", "error");
                redirect.addFlashAttribute("mensaje", "Ya existe una empresa registrada. No se pueden agregar más.");
                return "redirect:/empresa/nuevo";
            }

            // Procesar rutas de destino para los archivos
            HashMap<String, String> rutas = guardarLogo(logo);
            empresa.setLogo(rutas != null && rutas.containsKey("rutaLogo") ? rutas.get("rutaLogo") : "");

            String hashedPassword = passwordEncoder.encode(empresa.getPassword());
            empresa.setPassword(hashedPassword);

            empresaService.guardar(empresa);
            redirect.addFlashAttribute("mensaje", "La empresa se guardó correctamente.");
            redirect.addFlashAttribute("tipoMensaje", "success");
            return "redirect:/empresa";
        } catch (Exception e) {
            redirect.addFlashAttribute("tipoMensaje", "error");
            redirect.addFlashAttribute("mensaje", "Ocurrió un error al guardar la empresa." + e.getMessage());
            return "empresas/crear_editar_empresa";
        }
    }

    public HashMap<String, String> guardarLogo(MultipartFile logo) {
        HashMap<String, String> rutas = new HashMap<>();
        try {
            DateTimeFormatter format = DateTimeFormatter.ofPattern("MMddyyyyHHmmss");
            System.out.println("LOGO: " + logo);
            if (logo != null && !logo.isEmpty()) {
                String contentTypeFU = logo.getContentType();

                @SuppressWarnings("null")
                String[] contentTypeFUS = contentTypeFU.split("/");
                String nameFileFU = String.format("%s_FU.%s", format.format(LocalDateTime.now()), contentTypeFUS[1]);
                String destinyRouteFU = environment.getProperty("route.destiny.files") + File.separator + nameFileFU;

                logo.transferTo(new File(destinyRouteFU));
                rutas.put("rutaLogo", nameFileFU);
                logger.info("DENTRO DEL IF");
            }
            logger.info("DESPUES FUERA DEL IF");

        } catch (Exception e) {
            logger.error("Error al procesar archivos: {}", e.getMessage());
        }
        return rutas;
    }

    // Obtener imagenes de la carpeta
    @Value("${route.destiny.files}")
    private String uploadDir;

    @GetMapping("/images/{filename}")
    public ResponseEntity<Resource> getImage(@PathVariable String filename) {
        try {
            Path filePath = Paths.get(uploadDir).resolve(filename).normalize();
            Resource resource = new UrlResource(filePath.toUri());

            if (resource.exists() && resource.isReadable()) {
                return ResponseEntity.ok()
                        .contentType(MediaType.IMAGE_JPEG)
                        .body(resource);
            } else {
                // Imagen por defecto
                Path defaultImagePath = Paths.get(uploadDir).resolve("11244147.jpg").normalize();
                Resource defaultResource = new UrlResource(defaultImagePath.toUri());

                if (defaultResource.exists() && defaultResource.isReadable()) {
                    return ResponseEntity.ok()
                            .contentType(MediaType.IMAGE_JPEG)
                            .body(defaultResource);
                } else {
                    return ResponseEntity.notFound().build();
                }
            }
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    //
    @GetMapping("/empresa/editar/{id}")
    public String mostrarFormularioModificarEmpresa(@PathVariable Integer id, Model model) {
        Empresa empresa = empresaService.obtenerPorId(id);
        if (empresa == null) {
            return "redirect:/empresas";
        }
        model.addAttribute("empresa", empresa);
        return "empresas/crear_editar_empresa";
    }

    //
    @PostMapping("/empresa/editar/{id}")
    public String actualizarEmpresa(@PathVariable Integer id, @ModelAttribute Empresa empresa, Model model,
            RedirectAttributes redirect, @RequestParam(value = "logoEmpresa", required = false) MultipartFile logo) {
        try {
            Empresa empresaExistente = empresaService.obtenerPorId(id);
            if (empresaExistente == null) {
                return "redirect:/empresa";
            }

            if (passwordEncoder.matches(empresa.getPassword(), empresaExistente.getPassword())) {
                // Obtener usuario logueado
                // Authentication authentication =
                // SecurityContextHolder.getContext().getAuthentication();
                // String username = authentication != null ? authentication.getName() :
                // "Anónimo";

                empresaExistente.setNombreEmpresa(empresa.getNombreEmpresa());
                empresaExistente.setCorreoEmpresa(empresa.getCorreoEmpresa());
                empresaExistente.setDireccion(empresa.getDireccion());
                empresaExistente.setNit(empresa.getNit());
                empresaExistente.setRubroEmpresa(empresa.getRubroEmpresa());
                empresaExistente.setRepresentante(empresa.getRepresentante());
                empresaExistente.setTelefono(empresa.getTelefono());

                // LOGO
                HashMap<String, String> rutas = new HashMap<>();
                if (logo != null && !logo.isEmpty()) {
                    // Eliminar archivo antiguo de la foto si existía
                    if (empresaExistente.getLogo() != null
                            && !empresaExistente.getLogo().isEmpty()) {
                        eliminarArchivoAntiguo(uploadDir + "/" + empresaExistente.getLogo());
                    }
                    // Guardar nueva foto
                    rutas.putAll(guardarLogo(logo));
                }
                if (rutas.containsKey("rutaLogo")) {
                    empresaExistente.setLogo(rutas.get("rutaLogo"));
                }

                empresaService.guardar(empresaExistente);
                redirect.addFlashAttribute("mensaje", "La empresa se actualizó correctamente.");
                redirect.addFlashAttribute("tipoMensaje", "success");
            } else {
                redirect.addFlashAttribute("tipoMensaje", "error");
                redirect.addFlashAttribute("mensaje", "La contraseña debe coincidar para realizar el cambio.");
            }
            return "redirect:/empresa";
        } catch (Exception e) {
            model.addAttribute("tipoMensaje", "error");
            model.addAttribute("mensaje", "Ocurrió un error al actualizar la empresa.");
            return "empresas/crear_editar_empresa";
        }
    }

    // Eliminar archivos antiguos
    public void eliminarArchivoAntiguo(String rutaArchivo) {
        try {
            Path filePath = Paths.get(rutaArchivo);
            logger.info("RUTA" + filePath);
            if (Files.exists(filePath)) {
                Files.delete(filePath);
                logger.info("Archivo eliminado");
            }
        } catch (Exception e) {
            logger.error("Error al eliminar el archivo antiguo: {}", e.getMessage());
        }
    }

}
