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
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import net.crisjr.model.DetalleMesa;
import net.crisjr.model.RetiroSindicato;
import net.crisjr.service.IDetalleMesaService;
import net.crisjr.service.IRetirosSindicatoService;

@Controller
@RequestMapping("/retiros")
public class RetirosSindicatoController {

    @Autowired
    private IRetirosSindicatoService retirosService;

    @Autowired
    private IDetalleMesaService detalleMesaService;

    @GetMapping("/create")
    public String mostrarFormulario(Model model) {
        model.addAttribute("retiro", new RetiroSindicato());
        model.addAttribute("detallesMesa", detalleMesaService.buscarHaciendasActivas());
        return "retiros/formRetiro";
    }

    @PostMapping("/save")
    public String guardarRetiro(@ModelAttribute RetiroSindicato retiro,
                                @RequestParam("idMesaDetalle") Integer idMesaDetalle,
                                @RequestParam("fecha") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fecha,
                                @RequestParam("monto") BigDecimal monto,
                                @RequestParam("motivo") String motivo,
                                RedirectAttributes attributes) {

        DetalleMesa hacienda = detalleMesaService.buscarPorId(idMesaDetalle);
        if (hacienda == null) {
            attributes.addFlashAttribute("error", "No se encontró el detalle de mesa con ID proporcionado.");
            return "redirect:/retiros/create";
        }

        retiro.setDetalleMesa(hacienda);
        retiro.setFecha(fecha); 
        retiro.setMonto(monto);
        retiro.setMotivo(motivo);
        retirosService.guardar(retiro);

        attributes.addFlashAttribute("msg", "Retiro registrado correctamente.");
        return "redirect:/";
    }


    @GetMapping("/lista")
    public String verLista(Model model) {
        List<RetiroSindicato> lista = retirosService.buscarTodos();
        model.addAttribute("retiros", lista);
        return "retiros/listaRetiros";
    }

    @GetMapping("/reportes")
    public String mostrarReporteRetiros(
            @RequestParam(name = "desde", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate desde,
            @RequestParam(name = "hasta", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate hasta,
            Model model) {

        if (desde != null && hasta != null) {
            List<RetiroSindicato> retiros = retirosService.buscarPorRangoFechas(desde, hasta);

            // Ordenar del más reciente al más antiguo
            retiros.sort(Comparator.comparing(RetiroSindicato::getFecha).reversed());

            // Calcular total retirado
            BigDecimal totalRetirado = retiros.stream()
                                            .map(RetiroSindicato::getMonto)
                                            .reduce(BigDecimal.ZERO, BigDecimal::add);

            model.addAttribute("retiros", retiros);
            model.addAttribute("totalRetirado", totalRetirado);
        }

        model.addAttribute("desde", desde);
        model.addAttribute("hasta", hasta);
        return "retiros/reportesRetiro";
    }
}
