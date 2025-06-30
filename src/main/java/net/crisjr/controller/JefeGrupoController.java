package net.crisjr.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import net.crisjr.enums.EstadoJefeGrupo;
import net.crisjr.model.Grupo;
import net.crisjr.model.JefeGrupo;
import net.crisjr.model.Usuario;
import net.crisjr.service.IGruposService;
import net.crisjr.service.IJefeGrupoService;
import net.crisjr.service.ISectorService;
import net.crisjr.service.IUsuariosService;



@Controller
@RequestMapping("/jefes")
public class JefeGrupoController {
    @Autowired
    private IJefeGrupoService jefeGrupoService;

    @Autowired
    private ISectorService sectorService;

     @Autowired
    private IUsuariosService usuariosService;

    @Autowired
    private IGruposService grupoService;

    @GetMapping("/lista")
    public String listarJefes(Model model) {
        List<JefeGrupo> jefes = jefeGrupoService.buscarPorEstado(EstadoJefeGrupo.ACTIVO);
        model.addAttribute("jefes", jefes);
        return "jefeGrupo/lista";
    }

    @GetMapping("/create")
    public String mostrarFormulario(Model model) {
        model.addAttribute("jefeGrupo", new JefeGrupo());
        model.addAttribute("sectores", sectorService.buscarTodas());
        return "jefeGrupo/formJefeGrupo";
    }

    @PostMapping("/save")
    public String guardarJefeGrupo(@RequestParam("idUsuario") String idUsuario,
                                   @RequestParam("grupoId") Integer grupoId,
                                   RedirectAttributes attributes) {

        Usuario socio = usuariosService.buscarPorIdUsuario(idUsuario);
        if (socio == null) {
            attributes.addFlashAttribute("error", "No se encontró un socio con el ID proporcionado.");
            return "redirect:/jefes/create";
        }

        Grupo grupo = grupoService.buscarPorId(grupoId);
        if (grupo == null) {
            attributes.addFlashAttribute("error", "Grupo no válido.");
            return "redirect:/jefes/create";
        }

        // Validar que no exista otro jefe activo en el grupo
        if (jefeGrupoService.existeJefeActivoEnGrupo(grupo)) {
            attributes.addFlashAttribute("error", "Este grupo ya tiene un jefe activo.");
            return "redirect:/jefes/create";
        }

        JefeGrupo jefe = new JefeGrupo();
        jefe.setSocio(socio);
        jefe.setGrupo(grupo);
        jefe.setFechaInicio(LocalDate.now());
        jefe.setEstado(EstadoJefeGrupo.ACTIVO);

        jefeGrupoService.guardar(jefe);
        attributes.addFlashAttribute("msg", "Jefe de grupo registrado exitosamente.");
        return "redirect:/jefes/create";
    }

    @GetMapping("/grupos-por-sector/{sectorId}")
    @ResponseBody
    public List<Grupo> obtenerGruposPorSector(@PathVariable int sectorId) {
        return grupoService.findBySectorId(sectorId);
    }

    @PostMapping("/retirar/{id}")
    public String retirar(@PathVariable Integer id) {
        JefeGrupo jefe = jefeGrupoService.buscarPorId(id);
        if (jefe != null) {
            jefe.setEstado(EstadoJefeGrupo.INACTIVO);
            jefe.setFechaFin(LocalDate.now());
            jefeGrupoService.guardar(jefe);
        }
        return "redirect:/jefes-grupo/lista";
    }
}
