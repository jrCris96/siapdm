package net.crisjr.service;

import java.util.List;

import net.crisjr.model.Grupo;
import net.crisjr.model.Sector;

public interface ISectorService {
    List<Sector> buscarTodas();
    Sector buscarPorId(Integer idSector);
    List<Grupo> buscarGruposPorSector(Integer sectorId);
}
