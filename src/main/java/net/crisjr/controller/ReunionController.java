package net.crisjr.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;


import net.crisjr.model.Reunion;
import net.crisjr.service.IReunionService;
import net.crisjr.service.db.ReunionService;

@Controller
@RequestMapping("/reuniones")
public class ReunionController {

    @Autowired
    private IReunionService reunionService;

    @Autowired
    private ReunionService reunionServiceJpa;

    // Mostrar formulario para crear nueva reunión
    @GetMapping("/nueva")
    public String mostrarFormularioNuevaReunion(Model model) {
        model.addAttribute("reunion", new Reunion());
        return "/reuniones/formularioReunion";
    }

    // Guardar reunión y asociar a los jefes de grupo activos
    @PostMapping("/guardar")
    public String guardarReunion(@ModelAttribute("reunion") Reunion reunion) {
        reunionServiceJpa.crearYNotificarReunion(reunion); 
        return "redirect:/reuniones/lista";
    }

    // Mostrar lista de reuniones
    @GetMapping("/lista")
    public String listarReuniones(Model model) {
        model.addAttribute("reuniones", reunionService.listarReuniones());
        return "/reuniones/listaReuniones";
    }

    @GetMapping("/detalle/{id}")
    public String verDetalleReunion(@PathVariable("id") Integer id, Model model) {
        Reunion reunion = reunionService.obtenerPorId(id); 
        model.addAttribute("reunion", reunion);
        return "/reuniones/detalleReunion";
    }
}
