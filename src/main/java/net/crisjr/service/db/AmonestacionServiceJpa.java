package net.crisjr.service.db;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.crisjr.model.Amonestacion;
import net.crisjr.model.Usuario;
import net.crisjr.repository.AmonestacionRepository;
import net.crisjr.service.IAmonestacionService;
import net.crisjr.service.IUsuariosService;

@Service
public class AmonestacionServiceJpa implements IAmonestacionService{

    @Autowired
    private AmonestacionRepository amonestacionRepo;

    @Autowired
    private IUsuariosService usuarioService;

    @Override
    public void guardar(Amonestacion amonestacion) {
        amonestacionRepo.save(amonestacion);
    }

    @Override
    public List<Amonestacion> buscarPorSocio(Usuario socio) {
        return amonestacionRepo.findBySocio(socio);
    }

    @Override
    public List<Amonestacion> listarTodas() {
        return amonestacionRepo.findAll();
    }

    @Override
    public Amonestacion buscarPorId(Integer id) {
        return amonestacionRepo.findById(id).orElse(null);
    }

    @Override
    public void eliminar(Integer id) {
        amonestacionRepo.deleteById(id);
    }

    @Override
    public List<Amonestacion> buscarPorGrupo(Integer grupoId) {
    List<Usuario> usuarios = usuarioService.buscarPorGrupo(grupoId);
    List<Amonestacion> amonestaciones = new ArrayList<>();

    for (Usuario usuario : usuarios) {
        amonestaciones.addAll(amonestacionRepo.findBySocio(usuario));
    }
    return amonestaciones;
    }

    @Override
    public List<Amonestacion> buscarPorSector(Integer sectorId) {
        return amonestacionRepo.findBySectorId(sectorId);
    }

    @Override
    public List<Amonestacion> obtenerPorUsuarioId(int idUsuario) {
        return amonestacionRepo.findBySocio_Id(idUsuario);
    }
}
