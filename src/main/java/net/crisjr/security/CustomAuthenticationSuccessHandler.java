package net.crisjr.security;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import net.crisjr.model.Usuario;
import net.crisjr.service.IUsuariosService;

@Component
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    @Autowired
    private IUsuariosService serviceUsuario;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {
        String username = authentication.getName();
        Usuario usuario = serviceUsuario.buscarPorUserName(username);

        for (GrantedAuthority auth : authentication.getAuthorities()) {
            String role = auth.getAuthority();

            if (role.equals("SUPER_ADMIN")) {
                response.sendRedirect("/index");
                return;
            }
            if (role.equals("Socio Propietario") || role.equals("Socio Asalariado")) {
                response.sendRedirect("/usuarios/view/" + usuario.getIdUsuario());
                return;
            }
        }
        // Por defecto, si no hay roles conocidos:
        response.sendRedirect("/index");
    }
}

