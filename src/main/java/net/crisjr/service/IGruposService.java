package net.crisjr.service;

import java.util.List;

import net.crisjr.model.Grupo;

public interface IGruposService {
    List<Grupo> buscarTodas();

    List<Grupo> findBySectorId(Long sectorId);
}
  