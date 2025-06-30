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
import net.crisjr.model.Sector;
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
    public String listarJefesPorSector(@RequestParam(required = false) Integer sectorId, Model model) {
        List<Sector> sectores = sectorService.buscarTodas();
        List<JefeGrupo> jefes;

        if (sectorId != null) {
            Sector sector = sectorService.buscarPorId(sectorId);
            jefes = jefeGrupoService.buscarJefesActivosPorSector(sector);
            model.addAttribute("sectorSeleccionado", sectorId);
        } else {
            jefes = jefeGrupoService.buscarTodosActivos();
        }

        model.addAttribute("sectores", sectores);
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

        // Validar que el socio no sea ya jefe en otro grupo
        if (jefeGrupoService.socioYaEsJefeActivo(socio)) {
            attributes.addFlashAttribute("error", "Este socio ya es jefe de otro grupo.");
            return "redirect:/jefes/create";
        }

        
        // Validar que el socio pertenece al grupo seleccionado
        if (!socio.getGrupo().getId().equals(grupo.getId())) {
            attributes.addFlashAttribute("error", "Este socio no pertenece al grupo seleccionado.");
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
    public String retirarJefe(@PathVariable Integer id, RedirectAttributes attributes) {
        JefeGrupo jefe = jefeGrupoService.buscarPorId(id);
        if (jefe != null) {
            jefe.setEstado(EstadoJefeGrupo.INACTIVO);
            jefe.setFechaFin(LocalDate.now());
            jefeGrupoService.guardar(jefe);
            attributes.addFlashAttribute("msg", "Jefe retirado correctamente.");
        }
        return "redirect:/jefes/lista";
    }

    @GetMapping("/grupos-disponibles/{sectorId}")
    @ResponseBody
    public List<Grupo> obtenerGruposSinJefe(@PathVariable int sectorId) {
        List<Grupo> todosGrupos = grupoService.findBySectorId(sectorId);
        return todosGrupos.stream()
            .filter(grupo -> !jefeGrupoService.existeJefeActivoEnGrupo(grupo))
            .toList();
    }

    @GetMapping("/grupo-del-socio/{idUsuario}")
    @ResponseBody
    public Grupo obtenerGrupoDelSocio(@PathVariable String idUsuario) {
        Usuario socio = usuariosService.buscarPorIdUsuario(idUsuario);
        return (socio != null) ? socio.getGrupo() : null;
    }
}
