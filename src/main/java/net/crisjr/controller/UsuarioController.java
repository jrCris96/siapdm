package net.crisjr.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import net.crisjr.model.Usuario;
import net.crisjr.service.IUsuariosService;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
@RequestMapping(value = "/usuarios")
public class UsuarioController {

    @Autowired
    private IUsuariosService serviceUsuario;

    @GetMapping("/index")
    public String mostrarIndex(Model model) {

        List<Usuario> lista= serviceUsuario.buscarTodas();
        model.addAttribute("usuarios", lista);

        return "/usuarios/listSocio";
    }
    
}
