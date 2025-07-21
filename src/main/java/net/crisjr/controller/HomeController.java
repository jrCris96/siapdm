package net.crisjr.controller;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import net.crisjr.model.Sector;
import net.crisjr.model.Usuario;
import net.crisjr.repository.AporteGrupalRepository;
import net.crisjr.service.ISectorService;
import net.crisjr.service.IUsuariosService;

@Controller
public class HomeController {

    private final PasswordEncoder passwordEncoder;

    @Autowired
    private ISectorService sectorService;

    @Autowired
    private AporteGrupalRepository aporteGrupalRepo;

    @Autowired
    private IUsuariosService serviceUsuario;

    HomeController(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request) {
        SecurityContextLogoutHandler logoutHandler= new SecurityContextLogoutHandler();
        logoutHandler.logout(request, null, null);
        return "redirect:/login";
    }

    @GetMapping("/login")
    public String mostrarLogin() {
        return "login";
    }

    @GetMapping("/index")
    public String mostrarIndex(Authentication auth, HttpSession session, Model model) {

        String username= auth.getName();
        System.out.println("nombre del usuario: "+username);

        for(GrantedAuthority rol: auth.getAuthorities()){
            System.out.println("Rol: "+rol.getAuthority());
        }

        if(session.getAttribute("usuario")==null){
            Usuario usuario= serviceUsuario.buscarPorUserName(username);
            usuario.setPassword(null);
            System.out.println("Usuario: "+usuario);
            session.setAttribute("usuario", usuario);
        }
        Map<String, Integer> sociosPorSector = serviceUsuario.obtenerSociosPorSector();
        model.addAttribute("sociosPorSector", sociosPorSector);
        return "home";
    }


    @GetMapping("/api/aportes-mensuales-por-sector")
    @ResponseBody
    public Map<String, List<BigDecimal>> obtenerAportesMensualesPorSector() {
        LocalDate hoy = LocalDate.now();
        LocalDate hace12Meses = hoy.minusMonths(11).withDayOfMonth(1); // desde el primer día hace 11 meses

        List<Sector> sectores = sectorService.buscarTodas();
        Map<String, List<BigDecimal>> datos = new LinkedHashMap<>();

        for (Sector sector : sectores) {
            List<BigDecimal> montosMensuales = new ArrayList<>();

            for (int i = 0; i < 12; i++) {
                LocalDate mesInicio = hace12Meses.plusMonths(i);
                LocalDate mesFin = mesInicio.withDayOfMonth(mesInicio.lengthOfMonth());

                BigDecimal total = aporteGrupalRepo.sumaAportesPorSectorYMes(sector.getId(), mesInicio, mesFin);
                montosMensuales.add(total != null ? total : BigDecimal.ZERO);
            }

            datos.put(sector.getNombre(), montosMensuales);
        }

        return datos;
    }

    @GetMapping("/api/total-recaudado-por-sector")
    @ResponseBody
    public Map<String, BigDecimal> obtenerTotalRecaudadoPorSector() {
        List<Object[]> resultados = aporteGrupalRepo.obtenerTotalRecaudadoPorSector();
        Map<String, BigDecimal> datos = new LinkedHashMap<>();

        for (Object[] fila : resultados) {
            String sector = (String) fila[0];
            BigDecimal total = (BigDecimal) fila[1];
            datos.put(sector, total);
        }

        return datos;
    }

    @GetMapping("/bcrypt/{texto}")
    @ResponseBody
    public String encriptar(@PathVariable("texto") String texto){
        return texto + " Encriptado en Bcrypt: "+passwordEncoder.encode(texto);
    }

}
