package net.crisjr.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import net.crisjr.enums.EstadoJefeGrupo;
import net.crisjr.model.Grupo;
import net.crisjr.model.JefeGrupo;
import net.crisjr.model.Sector;

public interface JefeGrupoRepository extends JpaRepository<JefeGrupo, Integer> {
    List<JefeGrupo> findByEstado(EstadoJefeGrupo estado);
    boolean existsByGrupoAndEstado(Grupo grupo, EstadoJefeGrupo estado);
    boolean existsBySocioAndEstado(net.crisjr.model.Usuario socio, net.crisjr.enums.EstadoJefeGrupo estado);
    List<JefeGrupo> findByGrupo_SectorAndEstado(Sector sector, EstadoJefeGrupo estado);
}
