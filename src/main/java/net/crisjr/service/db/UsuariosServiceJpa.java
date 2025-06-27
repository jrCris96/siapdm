package net.crisjr.service.db;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import net.crisjr.model.Perfil;
import net.crisjr.model.Usuario;
import net.crisjr.repository.UsuariosRepository;
import net.crisjr.service.IUsuariosService;

@Service
@Primary
public class UsuariosServiceJpa implements IUsuariosService {

    @Autowired
    private UsuariosRepository usuariosRepo;

    @Override
public void guardar(Usuario usuario) {
    // Validar que tenga al menos un perfil
    if (usuario.getPerfiles() == null || usuario.getPerfiles().isEmpty()) {
        throw new IllegalArgumentException("El usuario debe tener al menos un perfil asignado.");
    }

    // Verificar si ya existe un usuario con el mismo carnet (para evitar duplicados)
    Usuario usuarioExistente = usuariosRepo.findByCarnet(usuario.getCarnet());
    
    // Si existe y no es el mismo (para el caso de actualización), lanzar error
    if (usuarioExistente != null && (usuario.getId() == null || !usuarioExistente.getId().equals(usuario.getId()))) {
        throw new IllegalArgumentException("Ya existe un usuario con ese carnet.");
    }

    // Guardar el usuario para obtener el ID autoincremental si es nuevo
    Usuario saved = usuariosRepo.save(usuario);

    // Determinar el prefijo en base al perfil asignado
    String prefijo = "spdm_"; 

    for (Perfil perfil : saved.getPerfiles()) {
        String nombrePerfil = perfil.getPerfil();
        if ("Socio Propietario".equalsIgnoreCase(nombrePerfil)) {
            prefijo += "scp_";
            break;
        } else if ("Socio Asalariado".equalsIgnoreCase(nombrePerfil)) {
            prefijo += "sca_";
            break;
        }
    }

    // Validar si se encontró un perfil válido
    if (prefijo.equals("spdm_")) {
        throw new IllegalArgumentException("El perfil asignado no es válido. Debe ser 'Socio Propietario' o 'Socio Asalariado'.");
    }

    // Asignar el ID personalizado y guardar nuevamente
    saved.setIdUsuario(prefijo + saved.getId());
    usuariosRepo.save(saved);
}

    @Override
    public List<Usuario> buscarTodas() {
        return usuariosRepo.findAll();
    }

    @Override
    public Usuario buscarPorId(Integer idUsuario) {
        Optional<Usuario> optional= usuariosRepo.findById(idUsuario);
        if(optional.isPresent()){
            return optional.get();
        }
        return null;
    }

}
