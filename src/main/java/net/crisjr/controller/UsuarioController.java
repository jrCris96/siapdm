package net.crisjr.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import net.crisjr.model.Grupo;
import net.crisjr.model.Usuario;
import net.crisjr.service.IGruposService;
import net.crisjr.service.IPerfilService;
import net.crisjr.service.ISectorService;
import net.crisjr.service.IUsuariosService;
import net.crisjr.util.Utileria;

import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
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

    @GetMapping("/index") 
    public String mostrarIndex(Model model) {

        List<Usuario> lista= serviceUsuario.buscarTodas();
        model.addAttribute("usuarios", lista);

        return "/usuarios/listSocio";
    }
    
    @GetMapping("/create")
    public String crear(Usuario usuario, Model model){
        model.addAttribute("sectores", serviceSector.buscarTodas());
        model.addAttribute("perfiles", servicePerfil.buscarTodas()); 

        if (usuario.getPerfiles() == null) {
        usuario.setPerfiles(new LinkedList<>());
        }
        return "usuarios/formRegistroSocio";
    }

    @GetMapping("/grupos-por-sector/{sectorId}")
    @ResponseBody
    public List<Grupo> obtenerGruposPorSector(@PathVariable Long sectorId) {
        return serviceGrupos.findBySectorId(sectorId);
    }

    @PostMapping("/save")
    public String guardar(Usuario usuario, BindingResult result, RedirectAttributes attributes, @RequestParam("archivoImagen") MultipartFile multipart){
        if(result.hasErrors()){
            for(ObjectError error: result.getAllErrors()){
                System.out.println("Ocurrio un error: "+error.getDefaultMessage());
            }
            return "usuarios/formRegistroSocio";
        }
        //para Subir la imagen
        if (!multipart.isEmpty()) {
            //String ruta = "c:/empleos/img-vacantes/"; 
            String nombreImagen = Utileria.guardarArchivo(multipart, ruta);

            if (nombreImagen != null){ // La imagen si se subio
            // Procesamos la variable nombreImagen
            usuario.setFoto(nombreImagen);
            }
        }

        serviceUsuario.guardar(usuario);
        attributes.addFlashAttribute("msg", "Registro Guardado!");
        System.out.println("Usuario: "+usuario);
        return "redirect:/usuarios/index";
    }

    @InitBinder
    public void initBinder(WebDataBinder webDataBinder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        webDataBinder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, false));
    }
}
