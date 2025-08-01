package net.crisjr.controller;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import net.crisjr.dto.ReporteDTO;
import net.crisjr.dto.UsuarioDTO;
import net.crisjr.model.AporteGrupal;
import net.crisjr.model.AporteSocio;
import net.crisjr.model.Grupo;
import net.crisjr.model.Usuario;
import net.crisjr.service.IAporteGrupalService;
import net.crisjr.service.IAporteSocioService;
import net.crisjr.service.IGruposService;
import net.crisjr.service.ISectorService;
import net.crisjr.service.IUsuariosService;

@Controller
@RequestMapping("/aportes")
public class AporteController {

    @Autowired
    private IAporteSocioService aporteSocioService;

    @Autowired
    private IAporteGrupalService aporteGrupalService;

    @Autowired
    private ISectorService sectorService;
 
    @Autowired
    private IGruposService grupoService;

    @Autowired
    private IUsuariosService usuarioService;

    @GetMapping("/create")
    public String mostrarFormulario(Model model) {
        model.addAttribute("sectores", sectorService.buscarTodas());
        return "aportes/registroAporte";
    }

    // Cargar grupos por sector (para el select dinámico)
    @GetMapping("/grupos-por-sector/{sectorId}")
    @ResponseBody
    public List<Grupo> obtenerGruposPorSector(@PathVariable Integer sectorId) {
        return grupoService.findBySectorId(sectorId);
    }

    // 👉 Cargar socios del grupo seleccionado
    @GetMapping("/socios-por-grupo/{grupoId}")
    @ResponseBody
    public List<UsuarioDTO> obtenerSociosPorGrupo(@PathVariable Integer grupoId) {
        return usuarioService.buscarHabilitadosPorGrupo(grupoId)
                            .stream()
                            .map(UsuarioDTO::new)
                            .toList();
    }

        // Guardar el registro completo del aporte
    @PostMapping("/guardar")
    public String guardarAporte(@RequestParam("grupoId") Integer grupoId,
                                 @RequestParam("fecha") String fecha,
                                 @RequestParam("montoTotal") BigDecimal montoTotal,
                                 @RequestParam(value = "pagados", required = false) List<Integer> idsSociosPagaron,
                                 RedirectAttributes attributes) {

        System.out.println("Socios que pagaron: " + idsSociosPagaron);
        Grupo grupo = grupoService.buscarPorId(grupoId);
        LocalDate fechaPago = LocalDate.parse(fecha);

        // Crear el aporte grupal
        AporteGrupal aporteGrupal = new AporteGrupal();
        aporteGrupal.setGrupo(grupo);
        aporteGrupal.setFecha(fechaPago);
        aporteGrupal.setMontoTotal(montoTotal);
        aporteGrupalService.guardar(aporteGrupal);
        
        if (grupo == null) {
            attributes.addFlashAttribute("error", "Grupo no válido.");
            return "redirect:/aportes/create";
        }
        

        // Registrar estado de pago de cada socio
        List<Usuario> socios = usuarioService.buscarHabilitadosPorGrupo(grupoId);
        for (Usuario socio : socios) {
            System.out.println("Socio: " + socio.getIdUsuario() + " | Estado: " + socio.getEstado());
            AporteSocio aporteSocio = new AporteSocio();
            aporteSocio.setUsuario(socio);
            aporteSocio.setAporteGrupal(aporteGrupal);
            aporteSocio.setPagado(idsSociosPagaron != null && idsSociosPagaron.contains(socio.getId()));
            aporteSocioService.guardar(aporteSocio);
        }

        attributes.addFlashAttribute("msg", "Registro de aporte guardado correctamente.");
        return "redirect:/aportes/create";
    }

@GetMapping("/reportes")
public String mostrarReportes(
        @RequestParam(name = "desde", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate desde,
        @RequestParam(name = "hasta", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate hasta,
        @RequestParam(name = "sectorId", required = false) Integer sectorId,
        @RequestParam(name = "grupoId", required = false) Integer grupoId,
        Model model) {

    model.addAttribute("sectores", sectorService.buscarTodas());

    if (desde == null || hasta == null) {
        model.addAttribute("error", "Debe seleccionar un rango de fechas para realizar la búsqueda.");
        return "aportes/reporteAportes";
    }

    List<ReporteDTO> resultados=null;
    String tipoReporte;

    if (sectorId == null && grupoId == null) {
        resultados = aporteGrupalService.reportePorSector(desde, hasta);
        tipoReporte = "SECTOR";
    } else if (sectorId != null && grupoId == null) {
        resultados = aporteGrupalService.reportePorGrupo(desde, hasta, sectorId);
        tipoReporte = "GRUPO";
    } else {
        tipoReporte = "DETALLE";
    }

    if ("DETALLE".equals(tipoReporte)) {
        List<AporteGrupal> detallePagos = aporteGrupalService.obtenerPagosDetalleGrupo(desde, hasta, grupoId);
        detallePagos.sort(Comparator.comparing(AporteGrupal::getFecha).reversed());
        model.addAttribute("detallePagos", detallePagos);
    }

    model.addAttribute("resultados", resultados);
    model.addAttribute("tipoReporte", tipoReporte);
    return "aportes/reporteAportes";
}
}
