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
    public String crearVehiculo(@RequestParam(name = "idUsuario", required = false) String idUsuario, Model model) {
    Vehiculo vehiculo = new Vehiculo();

    if (idUsuario != null) {
        Usuario propietario = usuariosRepo.findByIdUsuario(idUsuario);
        if (propietario != null) {
            vehiculo.setUsuarioId(propietario);
        }
    }

    model.addAttribute("vehiculo", vehiculo);
    return "vehiculos/formRegistroMovilidad";
}

@PostMapping("/save")
public String guardarVehiculo(
        Vehiculo vehiculo,
        @RequestParam("idPropietario") String idPropietario,
        @RequestParam(value = "idAsalariado", required = false) String idAsalariado,
        @RequestParam("archivoImagen") MultipartFile multiPart,
        RedirectAttributes attributes) {

    // Buscar propietario
    Usuario propietario = usuariosRepo.findByIdUsuario(idPropietario);
    if (propietario == null) {
        attributes.addFlashAttribute("error", "No se encontró el propietario con ID: " + idPropietario);
        return "redirect:/vehiculos/create";
    }

    // Validar cantidad de vehículos activos
    if (vehiculo.getId() == null) {
        long cantidadVehiculos = serviceVehiculos.contarVehiculosActivosPorUsuario(propietario, null);
        if (cantidadVehiculos >= 2) {
            attributes.addFlashAttribute("error", "Este usuario ya tiene 2 vehículos asignados.");
            return "redirect:/vehiculos/create";
        }
    } else {
        long cantidadVehiculos = vehiculosRepo.countByUsuarioIdExceptVehiculoId(propietario, vehiculo.getId());
        if (cantidadVehiculos >= 2) {
            attributes.addFlashAttribute("error", "Este usuario ya tiene 2 vehículos asignados.");
            return "redirect:/vehiculos/edit/" + vehiculo.getId();
        }
    }

    // Asignar propietario
    vehiculo.setUsuarioId(propietario);

    // Validar asalariado si se proporciona
    if (idAsalariado != null && !idAsalariado.trim().isEmpty()) {
        Usuario asalariado = usuariosRepo.findByIdUsuario(idAsalariado);
        if (asalariado == null) {
            attributes.addFlashAttribute("error", "No se encontró el asalariado con ID: " + idAsalariado);
            return vehiculo.getId() == null ? "redirect:/vehiculos/create" : "redirect:/vehiculos/edit/" + vehiculo.getId();
        }

        // 🚫 Verificar que no sea un socio propietario
        if (asalariado.tienePerfil("Socio Propietario")) {
            attributes.addFlashAttribute("error", "Este socio es propietario y no puede ser asignado como asalariado.");
            return vehiculo.getId() == null ? "redirect:/vehiculos/create" : "redirect:/vehiculos/edit/" + vehiculo.getId();
        }

        boolean asalariadoAsignadoEnOtro = false;

        if (vehiculo.getId() != null) {
            Vehiculo vehiculoExistente = serviceVehiculos.buscarPorId(vehiculo.getId());
            Usuario asalariadoAnterior = vehiculoExistente.getSocioAsalariadoId();

            // Validar solo si se cambió el asalariado
            if (asalariadoAnterior == null || !asalariadoAnterior.getId().equals(asalariado.getId())) {
                asalariadoAsignadoEnOtro = serviceVehiculos.asalariadoYaTieneVehiculoActivo(asalariado);
            }
        } else {
            asalariadoAsignadoEnOtro = serviceVehiculos.asalariadoYaTieneVehiculoActivo(asalariado);
        }

        if (asalariadoAsignadoEnOtro) {
            attributes.addFlashAttribute("error", "Este socio asalariado ya está asignado a otro vehículo activo.");
            return vehiculo.getId() == null ? "redirect:/vehiculos/create" : "redirect:/vehiculos/edit/" + vehiculo.getId();
        }

        vehiculo.setSocioAsalariadoId(asalariado);
    } else {
        vehiculo.setSocioAsalariadoId(null);
    }

    // Subir imagen
    if (!multiPart.isEmpty()) {
        String nombreImagen = Utileria.guardarArchivo(multiPart, ruta);
        if (nombreImagen != null) {
            vehiculo.setFoto(nombreImagen);
        }
    }

    // Guardar vehículo
    serviceVehiculos.guardar(vehiculo);

    attributes.addFlashAttribute("msg", vehiculo.getId() == null ? "Vehículo registrado correctamente." : "Vehículo actualizado correctamente.");

    return "redirect:/usuarios/view/" + propietario.getId();
}


    @GetMapping("/view/{id}")
    public String verDetalle(@PathVariable("id") int idVehiculo, Model model){

        

        Vehiculo vehiculo= serviceVehiculos.buscarPorId(idVehiculo);
        System.out.println("vehiculo: "+vehiculo);
        model.addAttribute("vehiculos", vehiculo);
        //Buscar los detalles de la vacante en la base de datos

        return "/usuarios/verSocio";
    }

    @GetMapping("/edit/{id}")
    public String editarVehiculo(@PathVariable("id") int idVehiculo, Model model) {

        Vehiculo vehiculo = serviceVehiculos.buscarPorId(idVehiculo);

        model.addAttribute("vehiculo", vehiculo);

        // Para mostrar datos del propietario actual
        Usuario propietario = vehiculo.getUsuarioId();
        model.addAttribute("propietario", propietario);

        // Para mostrar datos del asalariado actual (si hay)
        Usuario asalariado = vehiculo.getSocioAsalariadoId();
        model.addAttribute("asalariado", asalariado);

        return "/vehiculos/formRegistroMovilidad"; // tu formulario para editar el vehículo
    }

    @GetMapping("/desactivar/{id}")
    public String desactivarVehiculo(@PathVariable("id") Integer id, RedirectAttributes attributes) {
        Vehiculo vehiculo = serviceVehiculos.buscarPorId(id);
        if (vehiculo != null) {
            vehiculo.setEstado("INACTIVO");
            serviceVehiculos.guardar(vehiculo);
            attributes.addFlashAttribute("msg", "Vehículo desactivado correctamente.");
        }
        return "redirect:/usuarios/view/" + vehiculo.getUsuarioId().getIdUsuario();
    }
}
 