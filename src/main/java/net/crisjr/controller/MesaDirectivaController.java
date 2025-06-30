package net.crisjr.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import net.crisjr.model.DetalleMesa;
import net.crisjr.model.MesaDirectiva;
import net.crisjr.model.Usuario;
import net.crisjr.service.ICargoMesaService;
import net.crisjr.service.IDetalleMesaService;
import net.crisjr.service.IMesaDirectivaService;
import net.crisjr.service.IUsuariosService;

@Controller
@RequestMapping("/mesa")
public class MesaDirectivaController {
    @Autowired
    private IMesaDirectivaService mesaService;

    @Autowired
    private ICargoMesaService cargoMesaService;

    @Autowired
    private IUsuariosService usuariosService;

    @Autowired
    private IDetalleMesaService detalleMesaService;

        // ========== CREAR NUEVA MESA DIRECTIVA ==========
    @GetMapping("/create")
    public String crearMesa(Model model, RedirectAttributes attributes) {
        boolean existeMesaActiva = mesaService.hayMesaActiva();
        if (existeMesaActiva) {
            attributes.addFlashAttribute("error", "Ya existe una mesa directiva activa.");
            return "redirect:/";
        }

        model.addAttribute("mesa", new MesaDirectiva());
        return "mesaDirectiva/formMesaDir";
    }

    @PostMapping("/save")
    public String guardarMesaDirectiva(@ModelAttribute MesaDirectiva mesa, RedirectAttributes attributes) {
        mesaService.guardar(mesa);
        attributes.addFlashAttribute("msg", "Mesa directiva creada exitosamente.");
        return "redirect:/mesa/detalle/lista/" + mesa.getId();
    }

    // ========== LISTA Y GESTIÓN DE INTEGRANTES ==========
    @GetMapping("/detalle/lista/{idMesa}")
    public String verIntegrantes(@PathVariable Integer idMesa, Model model) {
        MesaDirectiva mesa = mesaService.buscarPorId(idMesa);
        List<DetalleMesa> integrantesActivos = detalleMesaService.buscarPorMesaActivos(mesa);
        int totalActivos = integrantesActivos.size();

        DetalleMesa nuevoIntegrante = new DetalleMesa();
        nuevoIntegrante.setMesaDirectiva(mesa);

        model.addAttribute("mesa", mesa);
        model.addAttribute("integrantes", integrantesActivos);
        model.addAttribute("detalleMesa", nuevoIntegrante);
        model.addAttribute("cargos", cargoMesaService.buscarTodas());
        model.addAttribute("puedeAgregar", totalActivos < 25);

        return "mesaDirectiva/listaIntegrantes";
    }

    @PostMapping("/detalle/agregar")
    public String agregarIntegrante(@ModelAttribute DetalleMesa detalle,
                                    @RequestParam("idUsuario") String idUsuario,
                                    RedirectAttributes attributes) {

        MesaDirectiva mesa = detalle.getMesaDirectiva();
        Usuario socio = usuariosService.buscarPorIdUsuario(idUsuario);

        if (socio == null) {
            attributes.addFlashAttribute("error", "No se encontró un socio con el ID proporcionado.");
            return "redirect:/mesa/detalle/lista/" + (mesa != null ? mesa.getId() : "");
        }

        // Validar campos
        if (mesa == null || detalle.getCargo() == null) {
            attributes.addFlashAttribute("error", "Todos los campos son obligatorios.");
            return "redirect:/mesa/detalle/lista/" + (mesa != null ? mesa.getId() : "");
        }

    

        if (detalleMesaService.existeSocioEnMesa(detalle.getMesaDirectiva(), socio)) {
            attributes.addFlashAttribute("error", "Este socio ya forma parte de la mesa directiva actual.");
            return "redirect:/mesa/detalle/lista/" + detalle.getMesaDirectiva().getId();
        }

        // Validar límite
        List<DetalleMesa> activos = detalleMesaService.buscarPorMesaActivos(mesa);
        if (activos.size() >= 25) {
            attributes.addFlashAttribute("error", "No se pueden agregar más de 25 integrantes activos.");
            return "redirect:/mesa/detalle/lista/" + mesa.getId();
        }

        // Guardar
        detalle.setUsuario(socio);
        detalle.setEstado(true);
        detalleMesaService.guardar(detalle);
        attributes.addFlashAttribute("msg", "Integrante agregado correctamente.");
        return "redirect:/mesa/detalle/lista/" + mesa.getId();
    }

    @PostMapping("/detalle/desactivar/{idDetalle}")
    public String desactivarIntegrante(@PathVariable Integer idDetalle, RedirectAttributes attributes) {
        DetalleMesa detalle = detalleMesaService.buscarPorId(idDetalle);
        if (detalle != null) {
            detalle.setEstado(false);
            detalleMesaService.guardar(detalle);
            attributes.addFlashAttribute("msg", "Integrante retirado.");
        }
        return "redirect:/mesa/detalle/lista/" + detalle.getMesaDirectiva().getId();
    }

    // ========== DESDE EL HOME: ACCESO A LA MESA ACTUAL ==========
    @GetMapping("/actual")
    public String verMesaActual(Model model, RedirectAttributes attributes) {
        MesaDirectiva mesa = mesaService.buscarMesaActiva();
        if (mesa == null) {
            attributes.addFlashAttribute("error", "No hay ninguna mesa directiva activa.");
            return "redirect:/";
        }
        return "redirect:/mesa/detalle/lista/" + mesa.getId();
    }

    // ========== FORMATO DE FECHAS ==========
    @InitBinder
    public void initBinder(WebDataBinder binder) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        binder.registerCustomEditor(Date.class, new CustomDateEditor(sdf, false));
    }


}
