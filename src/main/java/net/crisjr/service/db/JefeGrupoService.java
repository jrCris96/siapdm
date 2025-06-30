package net.crisjr.service.db;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.crisjr.enums.EstadoJefeGrupo;
import net.crisjr.model.Grupo;
import net.crisjr.model.JefeGrupo;
import net.crisjr.repository.JefeGrupoRepository;
import net.crisjr.service.IJefeGrupoService;

@Service
public class JefeGrupoService implements IJefeGrupoService {

    @Autowired
    private JefeGrupoRepository jefeGrupoRepo;

    @Override
    public void guardar(JefeGrupo jefe) {
        jefeGrupoRepo.save(jefe);
    }

    @Override
    public List<JefeGrupo> buscarTodos() {
        return jefeGrupoRepo.findAll();
    }

    @Override
    public List<JefeGrupo> buscarPorEstado(EstadoJefeGrupo estado) {
        return jefeGrupoRepo.findByEstado(estado);
    }

    @Override
    public JefeGrupo buscarPorId(Integer id) {
        return jefeGrupoRepo.findById(id).orElse(null);
    }

    @Override
    public void eliminar(Integer id) {
        jefeGrupoRepo.deleteById(id);
    }

    @Override
    public boolean existeJefeActivoEnGrupo(Grupo grupo) {
        return jefeGrupoRepo.existsByGrupoAndEstado(grupo, EstadoJefeGrupo.ACTIVO);
    }

}
