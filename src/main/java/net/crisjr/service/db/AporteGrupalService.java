package net.crisjr.service.db;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.crisjr.dto.ReporteDTO;
import net.crisjr.model.AporteGrupal;
import net.crisjr.model.Grupo;
import net.crisjr.repository.AporteGrupalRepository;
import net.crisjr.service.IAporteGrupalService;

@Service
public class AporteGrupalService implements IAporteGrupalService{

    @Autowired
    private AporteGrupalRepository aporteGrupalRepo;
    
    @Override
    public void guardar(AporteGrupal aporteGrupal) {
        aporteGrupalRepo.save(aporteGrupal);
    }

    @Override
    public List<AporteGrupal> buscarPorGrupo(Grupo grupo) {
        return aporteGrupalRepo.findByGrupo(grupo);
    }

    @Override
    public AporteGrupal buscarPorId(Integer id) {
        return aporteGrupalRepo.findById(id).orElse(null);
    }

    @Override
    public boolean existeRegistro(Grupo grupo, LocalDate fecha) {
        return aporteGrupalRepo.existsByGrupoAndFecha(grupo, fecha);
    }

    @Override
    public List<ReporteDTO> reportePorSector(LocalDate desde, LocalDate hasta) {
        return aporteGrupalRepo.obtenerTotalesPorSector(desde, hasta);
    }

    @Override
    public List<ReporteDTO> reportePorGrupo(LocalDate desde, LocalDate hasta, Integer sectorId) {
        return aporteGrupalRepo.obtenerTotalesPorGrupo(desde, hasta, sectorId);
    }

    @Override
    public List<ReporteDTO> reporteDetalleGrupo(LocalDate desde, LocalDate hasta, Integer grupoId) {
        return aporteGrupalRepo.obtenerTotalDeUnGrupo(desde, hasta, grupoId);
    }

    @Override
    public List<AporteGrupal> obtenerPagosDetalleGrupo(LocalDate desde, LocalDate hasta, Integer grupoId) {
        return aporteGrupalRepo.findPagosPorGrupoYFechas(grupoId, desde, hasta);
    }

}
