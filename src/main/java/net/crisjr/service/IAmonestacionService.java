package net.crisjr.service;

import java.util.List;

import net.crisjr.model.Amonestacion;
import net.crisjr.model.Usuario;

public interface IAmonestacionService {
    void guardar(Amonestacion amonestacion);
    List<Amonestacion> buscarPorSocio(Usuario socio);
    List<Amonestacion> listarTodas();
    Amonestacion buscarPorId(Integer id);
    void eliminar(Integer id);
    List<Amonestacion> buscarPorGrupo(Integer grupoId);
    List<Amonestacion> buscarPorSector(Integer sectorId);
    List<Amonestacion> obtenerPorUsuarioId(int idUsuario);
    int contarPorIdSocio(Integer idSocio);


}
