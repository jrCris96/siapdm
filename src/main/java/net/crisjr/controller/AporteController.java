package net.crisjr.controller;

import java.math.BigDecimal;
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
        return usuarioService.buscarPorGrupo(grupoId)
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
        List<Usuario> socios = usuarioService.buscarPorGrupo(grupoId);
        for (Usuario socio : socios) {
            AporteSocio aporteSocio = new AporteSocio();
            aporteSocio.setUsuario(socio);
            aporteSocio.setAporteGrupal(aporteGrupal);
            aporteSocio.setPagado(idsSociosPagaron != null && idsSociosPagaron.contains(socio.getId()));
            aporteSocioService.guardar(aporteSocio);
        }

        attributes.addFlashAttribute("msg", "Registro de aporte guardado correctamente.");
        return "redirect:/aportes/create";
    }
}
