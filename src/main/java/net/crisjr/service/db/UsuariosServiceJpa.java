package net.crisjr.service.db;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

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
        Usuario saved= usuariosRepo.save(usuario);
        saved.setId_usuario("spdm_soc_"+saved.getId());
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
