package net.crisjr.service.db;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.crisjr.dto.PagoDTO;
import net.crisjr.model.AporteGrupal;
import net.crisjr.model.AporteSocio;
import net.crisjr.model.Usuario;
import net.crisjr.repository.AporteSocioRepository;
import net.crisjr.service.IAporteSocioService;

@Service
public class AporteSocioService implements IAporteSocioService{

    @Autowired
    private AporteSocioRepository aporteSocioRepo;

    @Override
    public void guardar(AporteSocio aporteSocio) {
        aporteSocioRepo.save(aporteSocio);
    }

    @Override
    public List<AporteSocio> buscarPorAporteGrupal(AporteGrupal aporteGrupal) {
        return aporteSocioRepo.findByAporteGrupal(aporteGrupal);
    }

    @Override
    public List<AporteSocio> buscarPorUsuario(Usuario usuario) {
        return aporteSocioRepo.findByUsuario(usuario);
    }

    @Override
    public boolean existeRegistro(Usuario usuario, AporteGrupal aporteGrupal) {
        return aporteSocioRepo.existsByUsuarioAndAporteGrupal(usuario, aporteGrupal);
    }

    @Override
    public AporteSocio buscarPorId(Integer id) {
        return aporteSocioRepo.findById(id).orElse(null);
    }

    @Override
    public List<PagoDTO> obtenerPagosPorSocio(Integer idSocio) {
        return aporteSocioRepo.obtenerPagosPorSocio(idSocio);
    }

}
