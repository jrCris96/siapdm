package net.crisjr.service;

import java.util.List;

import net.crisjr.model.Grupo;

public interface IGruposService {
    List<Grupo> buscarTodas();
    Grupo buscarPorId(Integer idGrupo);
    List<Grupo> findBySectorId(Integer sectorId);
}
  