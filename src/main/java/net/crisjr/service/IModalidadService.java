package net.crisjr.service;

import java.util.List;

import net.crisjr.model.Modalidad;

public interface IModalidadService {
    List<Modalidad> listarTodas();
    Modalidad guardar(Modalidad modalidad); 
    void eliminar(Integer id);
    Modalidad buscarPorId(Integer id);
    List<Modalidad> listarPorSector(Integer sectorId); // Para listar solo las de un sector
}
