package net.crisjr.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import net.crisjr.model.AporteGrupal;
import net.crisjr.model.Grupo;


public interface AporteGrupalRepository extends JpaRepository<AporteGrupal, Integer> {
    List<AporteGrupal> findByGrupo(Grupo grupo);
    boolean existsByGrupoAndFecha(Grupo grupo, java.time.LocalDate fecha);
}
