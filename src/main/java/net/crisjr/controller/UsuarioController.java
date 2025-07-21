package net.crisjr.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.servlet.http.HttpSession;
import net.crisjr.dto.PagoDTO;
import net.crisjr.model.Amonestacion;
import net.crisjr.model.Grupo;
import net.crisjr.model.Perfil;
import net.crisjr.model.Usuario;
import net.crisjr.model.Vehiculo;
import net.crisjr.service.IAmonestacionService;
import net.crisjr.service.IAporteSocioService;
import net.crisjr.service.IGruposService;
import net.crisjr.service.IPerfilService;
import net.crisjr.service.ISectorService;
import net.crisjr.service.IUsuariosService;
import net.crisjr.service.IVehiculosService;
import net.crisjr.util.Utileria;

import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;


@Controller
@RequestMapping(value = "/usuarios")
public class UsuarioController {

    @Value("${empleosapp.ruta.imagenes}")
    private String ruta;

    @Autowired
    private IUsuariosService serviceUsuario;

    @Autowired
    private IGruposService serviceGrupos;

    @Autowired
    private ISectorService serviceSector;

    @Autowired
    private IPerfilService servicePerfil;

    @Autowired
    private IVehiculosService serviceVehiculo;

    @Autowired
    private IAporteSocioService serviceAporteSocio;

    @Autowired
    private IAmonestacionService serviceAmonestacionService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/index") 
    public String mostrarIndex(Model model) {

        List<Usuario> lista= serviceUsuario.buscarTodas();
        model.addAttribute("usuarios", lista);

        return "/usuarios/listSocio";
    }

    @GetMapping(value = "/indexPaginate")
    public String mostrarIndexPaginado(Model model, Pageable page) {
        Page<Usuario> lista = serviceUsuario.buscarPorEstado("habilitado", page);
        model.addAttribute("usuarios", lista);
        return "usuarios/listSocio";
    }

    
    @GetMapping("/create") 
    public String crear(Usuario usuario, Model model){
        if (usuario.getPerfiles() == null) {
        usuario.setPerfiles(new LinkedList<>());
        }

        List<Perfil> perfiles = servicePerfil.buscarTodas();
        perfiles.removeIf(perfil -> "SUPER_ADMIN".equalsIgnoreCase(perfil.getPerfil()));
        model.addAttribute("perfiles", perfiles);
        return "usuarios/formRegistroSocio";
    }

    @GetMapping("/grupos-por-sector/{sectorId}")
    @ResponseBody
    public List<Grupo> obtenerGruposPorSector(@PathVariable int sectorId) {
        return serviceGrupos.findBySectorId(sectorId);
    }

    @PostMapping("/save")
    public String guardar(Usuario usuario, BindingResult result, RedirectAttributes attributes, @RequestParam("archivoImagen") MultipartFile multipart) {
        
        // Codifica el carnet y lo asigna como password ENCRIPTADO
        usuario.setPassword(passwordEncoder.encode(usuario.getCarnet()));

        if (result.hasErrors()) {
            for (ObjectError error : result.getAllErrors()) {
                System.out.println("Ocurrio un error: " + error.getDefaultMessage());
            }
            return "usuarios/formRegistroSocio";
        }

        try {
            // Subir la imagen si existe 
            if (!multipart.isEmpty()) {
                String nombreImagen = Utileria.guardarArchivo(multipart, ruta);
                if (nombreImagen != null) {
                    usuario.setFoto(nombreImagen); 
                }
            } 

            // Guardar usuario con la lógica del servicio (que puede lanzar excepciones)
            serviceUsuario.guardar(usuario);

            attributes.addFlashAttribute("msg", "Registro Guardado!");

             // Verificar si el usuario tiene perfil "SOCIO PROPIETARIO"
            boolean esPropietario = usuario.getPerfiles().stream()
                .anyMatch(p -> p.getPerfil().equalsIgnoreCase("SOCIO PROPIETARIO"));

            if (esPropietario) {
                // Redirigir al registro de vehículo con el ID personalizado ya incluido
                return "redirect:/vehiculos/create?idUsuario=" + usuario.getIdUsuario();
            } else {
                // Redirigir al inicio
                return "redirect:/index";
            }

        } catch (IllegalArgumentException e) {
            // Captura la excepción y manda mensaje de error a la vista
            attributes.addFlashAttribute("error", e.getMessage());

            // Para que el formulario conserve los datos ingresados
            attributes.addFlashAttribute("usuario", usuario);

            // Redirige al formulario de creación para que el usuario corrija
            return "redirect:/usuarios/create";
        }
}

    @GetMapping("/view/{idUsuario}")
    public String verDetalle(@PathVariable("idUsuario") String idUsuario, Model model, Authentication auth, HttpSession session) {
        System.out.println(">>> LLEGUE A /usuarios/view/" + idUsuario);

        // Siempre asegura que el usuario logueado esté en sesión
        if(session.getAttribute("usuario") == null){
            Usuario usuarioSesion = serviceUsuario.buscarPorUserName(auth.getName());
            if(usuarioSesion != null) usuarioSesion.setPassword(null);
            session.setAttribute("usuario", usuarioSesion);
        }

        Usuario usuario = null;

        // Detecta si es un número (id numérico)
        if (idUsuario.matches("\\d+")) {
            int id = Integer.parseInt(idUsuario);
            usuario = serviceUsuario.buscarPorId(id);
        } else {
            // Si no, busca por id_usuario (string personalizado)
            usuario = serviceUsuario.buscarPorUserName(idUsuario);
        }

        if (usuario == null) {
            model.addAttribute("error", "Usuario no encontrado");
            return "error";
        }

        // --- CONTROL DE ACCESO ---
        String username = auth.getName();

        boolean isAdmin = auth.getAuthorities().stream()
            .anyMatch(a -> a.getAuthority().equals("SUPER_ADMIN"));

        // Si NO es admin, solo puede ver su propio perfil
        if (!isAdmin) {
            Usuario usuarioLogueado = serviceUsuario.buscarPorUserName(username);
            if (usuarioLogueado == null || 
                !(usuarioLogueado.getId() == usuario.getId() || usuarioLogueado.getIdUsuario().equals(idUsuario))) {
                model.addAttribute("error", "No tienes permiso para ver este perfil");
                return "error";
            }
        }
        // --- FIN CONTROL ---

        // Lógica para cargar datos adicionales
        List<Vehiculo> vehiculosPropios = serviceVehiculo.obtenerPorUsuarioYEstado(usuario, "ACTIVO");
        List<Vehiculo> vehiculosAsignados = usuario.getVehiculosAsignados();
        List<Amonestacion> amonestaciones = serviceAmonestacionService.obtenerPorUsuarioId(usuario.getId());
        List<PagoDTO> pagos = serviceAporteSocio.obtenerPagosPorSocio(usuario.getId());

        model.addAttribute("usuarios", usuario);
        model.addAttribute("vehiculosPropios", vehiculosPropios);
        model.addAttribute("vehiculosAsignados", vehiculosAsignados);
        model.addAttribute("amonestaciones", amonestaciones);
        model.addAttribute("pagos", pagos);

        return "/usuarios/verSocio";
    }



    @GetMapping("/desactivar/{id}")
    public String desactivarUsuario(@PathVariable("id") Integer idUsuario, RedirectAttributes attributes) {

        Usuario usuario = serviceUsuario.buscarPorId(idUsuario);

        if (usuario != null) {
            usuario.setEstado("deshabilitado"); 
            serviceUsuario.guardar(usuario);
            attributes.addFlashAttribute("msg","El usuario fue deshabilitado correctamente.");
        } else {
            attributes.addFlashAttribute("error",
                    "Usuario no encontrado.");
        }
        return "redirect:/usuarios/buscar?estado=habilitado";
    }

    @GetMapping("/activar/{id}")
    public String activarUsuario(@PathVariable("id") Integer idUsuario, RedirectAttributes attributes) {
        Usuario usuario = serviceUsuario.buscarPorId(idUsuario);
        if (usuario != null) {
            usuario.setEstado("habilitado");
            serviceUsuario.guardar(usuario);
            attributes.addFlashAttribute("msg", "Usuario habilitado correctamente.");
        } else {
            attributes.addFlashAttribute("error", "Usuario no encontrado.");
        }
        return "redirect:/usuarios/buscar?estado=deshabilitado";
    }

    @GetMapping("/edit/{id}")
    public String editar(@PathVariable("id") int idUsuario, Model model){
        Usuario usuario= serviceUsuario.buscarPorId(idUsuario);
        model.addAttribute("usuario", usuario);

        Grupo grupoUsuario= usuario.getGrupo();
        model.addAttribute("grupoUsuario", grupoUsuario);

        List<Grupo> gruposPorSector= new ArrayList<>();
        if(grupoUsuario !=null && grupoUsuario.getSector() !=null){
            gruposPorSector= serviceGrupos.findBySectorId(grupoUsuario.getSector().getId());
        }
        model.addAttribute("grupoPorSector", gruposPorSector);

        return "usuarios/formRegistroSocio";
    }

    @ModelAttribute
    public void setGenericos(Model model){
        Usuario usuarioSearch= new Usuario();
        model.addAttribute("sectores", serviceSector.buscarTodas());
        model.addAttribute("perfiles", servicePerfil.buscarTodas()); 
        model.addAttribute("search", usuarioSearch);
    }
    
    @GetMapping("/buscar")
    public String buscarUsuarios(
        @RequestParam(name = "idUsuario", required = false) String idUsuario,
        @RequestParam(name = "idGrupo", required = false) Integer idGrupo,
        @RequestParam(name = "idSector", required = false) Integer idSector,
        @RequestParam(name = "estado", required = false) String estado,
        Model model) {

        if (idUsuario != null && idUsuario.trim().isEmpty()) {
            idUsuario = null;
        }
        if (estado == null || estado.trim().isEmpty()) {
            estado = "habilitado"; // por defecto
        }

        List<Usuario> lista = serviceUsuario.buscarPorFiltros(idUsuario, idGrupo, idSector, estado);
        model.addAttribute("usuarios", lista);
        model.addAttribute("estadoActual", estado);
        model.addAttribute("sectores", serviceSector.buscarTodas());

        if (idSector != null) {
            List<Grupo> grupos = serviceGrupos.findBySectorId(idSector);
            model.addAttribute("grupoPorSector", grupos);
        }

        return "usuarios/listSocio";
    }
 
    @InitBinder
    public void initBinder(WebDataBinder webDataBinder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        webDataBinder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, false));
    }
}
