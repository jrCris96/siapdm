package net.crisjr.service;

import java.time.LocalDate;
import java.util.List;

import net.crisjr.dto.ReporteDTO;
import net.crisjr.model.AporteGrupal;
import net.crisjr.model.Grupo;

public interface IAporteGrupalService {
    void guardar(AporteGrupal aporteGrupal);
    List<AporteGrupal> buscarPorGrupo(Grupo grupo);
    AporteGrupal buscarPorId(Integer id);
    boolean existeRegistro(Grupo grupo, java.time.LocalDate fecha);
    List<ReporteDTO> reportePorSector(LocalDate desde, LocalDate hasta);
    List<ReporteDTO> reportePorGrupo(LocalDate desde, LocalDate hasta, Integer sectorId);
    List<ReporteDTO> reporteDetalleGrupo(LocalDate desde, LocalDate hasta, Integer grupoId);
    List<AporteGrupal> obtenerPagosDetalleGrupo(LocalDate desde, LocalDate hasta, Integer grupoId);


}
