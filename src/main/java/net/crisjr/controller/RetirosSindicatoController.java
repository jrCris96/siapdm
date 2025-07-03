package net.crisjr.controller;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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
                                 @RequestParam("monto") BigDecimal monto,
                                 @RequestParam("motivo") String motivo,
                                 RedirectAttributes attributes) {

        DetalleMesa hacienda = detalleMesaService.buscarPorId(idMesaDetalle);
        if (hacienda == null) {
            attributes.addFlashAttribute("error", "No se encontró el detalle de mesa con ID proporcionado.");
            return "redirect:/retiros/create";
        }

        retiro.setDetalleMesa(hacienda);
        retiro.setFecha(LocalDate.now());
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
}
