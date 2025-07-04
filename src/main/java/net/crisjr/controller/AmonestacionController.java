package net.crisjr.controller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import net.crisjr.model.Amonestacion;
import net.crisjr.model.Usuario;
import net.crisjr.service.IAmonestacionService;
import net.crisjr.service.ISectorService;
import net.crisjr.service.IUsuariosService;

@Controller
@RequestMapping("/amonestaciones")
public class AmonestacionController {
    @Autowired
    private IAmonestacionService amonestacionService;

    @Autowired
    private IUsuariosService usuarioService;

    @Autowired
    private ISectorService sectorService;

    // 👉 Mostrar formulario de nueva amonestación
    @GetMapping("/create")
    public String mostrarFormulario(Model model) {
        model.addAttribute("socios", usuarioService.buscarTodas());
        model.addAttribute("amonestacion", new Amonestacion());
        return "amonestaciones/formularioAmonestacion";
    }

    // 👉 Guardar amonestación
    @PostMapping("/save")
public String guardarAmonestacion(@RequestParam("idUsuario") String idUsuario,
                                  @RequestParam("fecha") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fecha,
                                  @RequestParam("nivel") String nivel,
                                  @RequestParam("descripcion") String descripcion,
                                  @RequestParam(name = "fechaReingreso", required = false)
                                  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaReingreso,
                                  RedirectAttributes attributes) {

    Usuario socio = usuarioService.buscarPorIdUsuario(idUsuario);

    if (socio == null) {
        attributes.addFlashAttribute("error", "No se encontró un socio con el ID proporcionado.");
        return "redirect:/amonestaciones/create";
    }

    Amonestacion amonestacion = new Amonestacion();
    amonestacion.setSocio(socio);
    amonestacion.setFecha(fecha);
    amonestacion.setNivel(nivel);
    amonestacion.setDescripcion(descripcion);
    amonestacion.setFechaReingreso(fechaReingreso);

    amonestacionService.guardar(amonestacion);

    attributes.addFlashAttribute("msg", "Amonestación registrada correctamente.");
    return "redirect:/";
}

    // 👉 Listar todas las amonestaciones
    @GetMapping("/listar")
    public String listarAmonestaciones(
            @RequestParam(name = "idUsuario", required = false) String idUsuario,
            @RequestParam(name = "sectorId", required = false) Integer sectorId,
            @RequestParam(name = "grupoId", required = false) Integer grupoId,
            Model model) {

        List<Amonestacion> amonestaciones = new ArrayList<>();

        // Buscar por ID personalizado del socio (prioridad)
        if (idUsuario != null && !idUsuario.isBlank()) {
            Usuario socio = usuarioService.buscarPorIdUsuario(idUsuario);
            if (socio != null) {
                amonestaciones = amonestacionService.buscarPorSocio(socio);
            }
        } else if (grupoId != null) {
            // Si solo se seleccionó grupo
            amonestaciones = amonestacionService.buscarPorGrupo(grupoId);
        }

        model.addAttribute("sectores", sectorService.buscarTodas());
        model.addAttribute("amonestaciones", amonestaciones);

        return "amonestaciones/listarAmonestaciones";
    }

}
