package net.crisjr.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import net.crisjr.model.Usuario;
import net.crisjr.model.Vehiculo;
import net.crisjr.repository.UsuariosRepository;
import net.crisjr.repository.VehiculosRepository;
import net.crisjr.service.IVehiculosService; 
import net.crisjr.util.Utileria; 

@Controller
@RequestMapping(value = "/vehiculos")
public class VehiculosController {

    @Value("${empleosapp.ruta.imagenes}")
    private String ruta;

    @Autowired
    private IVehiculosService serviceVehiculos;

    @Autowired
    private VehiculosRepository vehiculosRepo;
 
    @Autowired
    private UsuariosRepository usuariosRepo;

    @GetMapping("/create")
    public String crear(Vehiculo vehiculo, Model model){
        
        return "vehiculos/formRegistroMovilidad";
    }

    @PostMapping("/save")
    public String guardarVehiculo(
        Vehiculo vehiculo,
        @RequestParam("idPropietario") String idPropietario,
        @RequestParam(value = "idAsalariado", required = false) String idAsalariado, 
        @RequestParam("archivoImagen") MultipartFile multiPart,
        RedirectAttributes attributes) {

        // Buscar propietario por ID dinámico
        Usuario propietario = usuariosRepo.findByIdUsuario(idPropietario);
        if (propietario == null) {
            attributes.addFlashAttribute("error", "No se encontró el propietario con ID: " + idPropietario);
            return "redirect:/vehiculos/create";
        }

        // Validar que no tenga más de 2 vehículos
        long cantidadVehiculos = vehiculosRepo.countByUsuarioId(propietario);
        if (cantidadVehiculos >= 2) {
            attributes.addFlashAttribute("error", "Este usuario ya tiene 2 vehículos asignados.");
            return "redirect:/vehiculos/create";
        }

        // Asignar propietario
        vehiculo.setUsuarioId(propietario);

        // Si hay asalariado, buscarlo
        if (idAsalariado != null && !idAsalariado.trim().isEmpty()) { 
            Usuario asalariado = usuariosRepo.findByIdUsuario(idAsalariado);
            if (asalariado == null) {
                attributes.addFlashAttribute("error", "No se encontró el asalariado con ID: " + idAsalariado);
                return "redirect:/vehiculos/create";
            }
            vehiculo.setSocioAsalariadoId(asalariado);
        }

        //para Subir la imagen
            if (!multiPart.isEmpty()) {
                //String ruta = "c:/empleos/img-vacantes/"; 
                String nombreImagen = Utileria.guardarArchivo(multiPart, ruta);

                if (nombreImagen != null){ // La imagen si se subio
                // Procesamos la variable nombreImagen
                vehiculo.setFoto(nombreImagen);
                }
            }


        // Guardar vehículo
        serviceVehiculos.guardar(vehiculo);
        attributes.addFlashAttribute("msg", "Vehículo registrado correctamente.");
        return "redirect:/vehiculos/create";
    }

    @GetMapping("/view/{id}")
    public String verDetalle(@PathVariable("id") int idVehiculo, Model model){

        Vehiculo vehiculo= serviceVehiculos.buscarPorId(idVehiculo);
        System.out.println("vehiculo: "+vehiculo);
        model.addAttribute("vehiculos", vehiculo);
        //Buscar los detalles de la vacante en la base de datos

        return "/usuarios/verSocio";
    }

}
 