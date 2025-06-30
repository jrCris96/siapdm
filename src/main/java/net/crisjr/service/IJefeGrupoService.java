package net.crisjr.service;

import java.util.List;

import net.crisjr.enums.EstadoJefeGrupo;
import net.crisjr.model.Grupo;
import net.crisjr.model.JefeGrupo;
import net.crisjr.model.Sector;
import net.crisjr.model.Usuario;

public interface IJefeGrupoService {
    void guardar(JefeGrupo jefe);
    List<JefeGrupo> buscarTodos();
    List<JefeGrupo> buscarPorEstado(EstadoJefeGrupo estado);
    JefeGrupo buscarPorId(Integer id); 
    void eliminar(Integer id);
    boolean existeJefeActivoEnGrupo(Grupo grupo);
    boolean socioYaEsJefeActivo(Usuario socio);

    List<JefeGrupo> buscarJefesActivosPorSector(Sector sector);
    List<JefeGrupo> buscarTodosActivos();
}
