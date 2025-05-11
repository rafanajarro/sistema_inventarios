package com.inventario.sistemainvetario.controller;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.inventario.sistemainvetario.model.Categorias;
import com.inventario.sistemainvetario.model.Producto;
import com.inventario.sistemainvetario.model.ProductoActividad;
import com.inventario.sistemainvetario.service.CategoriaService;
import com.inventario.sistemainvetario.service.ProductoService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/productos")
public class ProductoController {

    @Autowired
    private ProductoService productoService;

    @Autowired
    private CategoriaService categoriaService;

    @Autowired
    private Environment environment;
    private static Logger logger = LoggerFactory.getLogger(ProductoController.class);

    @GetMapping("/entradasSalidasPorMes")
    public ResponseEntity<?> obtenerMovimientosProducto(@RequestParam Integer idProducto) {
        List<Map<String, Object>> resumen = productoService.obtenerMovimientosProducto(idProducto);        
        return ResponseEntity.ok(resumen);
    }

    @GetMapping("/stockBajo")
    public ResponseEntity<?> obtenerStockBajo() {
        List<Map<String, Object>> resumen = productoService.obtenerStockBajo();
        return ResponseEntity.ok(resumen);
    }

    @GetMapping
    public String listarProductos(Model model) {
        List<Producto> productos = productoService.obtenerTodos();

        model.addAttribute("productos", productos);
        return "producto/listado_productos";
    }

    @GetMapping("/nuevo")
    public String mostrarFormularioNuevo(Model model) {
        List<Categorias> categorias = categoriaService.obtenerTodos();
        model.addAttribute("producto", new Producto());
        model.addAttribute("categorias", categorias);
        return "producto/crear_editar_producto";
    }

    @PostMapping("/guardar")
    public String guardarProducto(@ModelAttribute Producto producto, RedirectAttributes redirectAttributes,
            @RequestParam(value = "fotoProducto", required = false) MultipartFile fotoProducto) {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String username = authentication != null ? authentication.getName() : "Anónimo";
            HashMap<String, String> rutas = guardarFoto(fotoProducto);
            producto.setImagen(rutas != null && rutas.containsKey("rutaFoto") ? rutas.get("rutaFoto") : "");
            producto.setUsuarioCrea(username);
            producto.setUsuarioMod(username);
            productoService.guardar(producto);
            redirectAttributes.addFlashAttribute("mensaje", "Producto guardado correctamente.");
            redirectAttributes.addFlashAttribute("tipoMensaje", "success");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("mensaje",
                    "Ocurrió un error al guardar el producto." + e.getMessage());
            redirectAttributes.addFlashAttribute("tipoMensaje", "error");
        }
        return "redirect:/productos";
    }

    public HashMap<String, String> guardarFoto(MultipartFile logo) {
        HashMap<String, String> rutas = new HashMap<>();
        try {
            DateTimeFormatter format = DateTimeFormatter.ofPattern("MMddyyyyHHmmss");
            if (logo != null && !logo.isEmpty()) {
                String contentTypeFU = logo.getContentType();

                @SuppressWarnings("null")
                String[] contentTypeFUS = contentTypeFU.split("/");
                String nameFileFU = String.format("%s_FU.%s", format.format(LocalDateTime.now()), contentTypeFUS[1]);
                String destinyRouteFU = environment.getProperty("route.destiny.files") + File.separator + "productos"
                        + File.separator
                        + nameFileFU;

                logo.transferTo(new File(destinyRouteFU));
                rutas.put("rutaFoto", nameFileFU);
            }
        } catch (Exception e) {
            logger.error("Error al procesar archivos: {}", e.getMessage());
        }
        return rutas;
    }

    @GetMapping("/editar/{id}")
    public String editarProducto(@PathVariable Integer id, Model model) {
        List<Categorias> categorias = categoriaService.obtenerTodos();
        Producto producto = productoService.obtenerPorId(id);
        if (producto == null) {
            return "redirect:/productos";
        }
        model.addAttribute("producto", producto);
        model.addAttribute("categorias", categorias);

        return "producto/crear_editar_producto";
    }

    @PostMapping("/editar/{id}")
    public String editarProducto(@PathVariable Integer id, @ModelAttribute Producto producto,
            RedirectAttributes redirectAttributes,
            @RequestParam(value = "fotoProducto", required = false) MultipartFile fotoProducto) {
        try {
            Producto productoExistente = productoService.obtenerPorId(id);
            if (productoExistente == null) {
                return "redirect:/productos";
            }
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String username = authentication != null ? authentication.getName() : "Anónimo";

            productoExistente.setNombre(producto.getNombre());
            productoExistente.setDescripcion(producto.getDescripcion());
            productoExistente.setIdCategoria(producto.getIdCategoria());
            productoExistente.setPrecioCompra(producto.getPrecioCompra());
            productoExistente.setPrecioVenta(producto.getPrecioVenta());
            productoExistente.setStockMinimo(producto.getStockMinimo());
            productoExistente.setEstado(producto.getEstado());
            productoExistente.setUsuarioMod(username);

            if (producto.getUnidadMedida() != null && !producto.getUnidadMedida().isEmpty()) {
                productoExistente.setUnidadMedida(producto.getUnidadMedida());
            }

            // IMAGEN
            HashMap<String, String> rutas = new HashMap<>();
            if (fotoProducto != null && !fotoProducto.isEmpty()) {
                // Eliminar archivo antiguo de la foto si existía
                if (productoExistente.getImagen() != null
                        && !productoExistente.getImagen().isEmpty()) {
                    eliminarArchivoAntiguo(uploadDir + "/productos/" + productoExistente.getImagen());
                }
                // Guardar nueva foto
                rutas.putAll(guardarFoto(fotoProducto));
            }
            if (rutas.containsKey("rutaFoto")) {
                productoExistente.setImagen(rutas.get("rutaFoto"));
            }

            productoService.guardar(productoExistente);
            redirectAttributes.addFlashAttribute("mensaje", "El producto se actualizó correctamente.");
            redirectAttributes.addFlashAttribute("tipoMensaje", "success");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("mensaje",
                    "Ocurrió un error al editar el producto.");
            redirectAttributes.addFlashAttribute("tipoMensaje", "error");
        }
        return "redirect:/productos";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminarProducto(@PathVariable Integer id, RedirectAttributes redirectAttributes) {
        productoService.eliminar(id);
        redirectAttributes.addFlashAttribute("mensaje", "Producto eliminado correctamente.");
        redirectAttributes.addFlashAttribute("tipoMensaje", "success");
        return "redirect:/productos";
    }

    // Ver producto
    @GetMapping("/ver/{id}")
    public String mostrarProducto(@PathVariable Integer id, Model model) {
        Producto producto = productoService.obtenerPorId(id);
        if (producto == null) {
            return "redirect:/productos";
        }

        List<ProductoActividad> actividad = productoService.obtenerUltimaActividadProducto(id);
        String cantidadTotalProducto = productoService.obtenerCantidadTotalProducto(id);
        model.addAttribute("producto", producto);
        model.addAttribute("actividad", actividad);
        model.addAttribute("cantidadTotalProducto", cantidadTotalProducto);
        return "producto/ver_producto";
    }

    // Eliminar archivos antiguos
    public void eliminarArchivoAntiguo(String rutaArchivo) {
        try {
            Path filePath = Paths.get(rutaArchivo);
            if (Files.exists(filePath)) {
                Files.delete(filePath);
                logger.info("Archivo eliminado");
            }
        } catch (Exception e) {
            logger.error("Error al eliminar el archivo antiguo: {}", e.getMessage());
        }
    }

    // Obtener imagenes de la carpeta
    @Value("${route.destiny.files}")
    private String uploadDir;

    @GetMapping("/images/{filename}")
    public ResponseEntity<Resource> getImage(@PathVariable String filename) {
        try {
            Path filePath = Paths.get(uploadDir + "/productos").resolve(filename).normalize();
            Resource resource = new UrlResource(filePath.toUri());

            if (!resource.exists()) {
                throw new RuntimeException("Archivo no encontrado: " + filename);
            }

            return ResponseEntity.ok()
                    .contentType(MediaType.IMAGE_JPEG)
                    .body(resource);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
}
