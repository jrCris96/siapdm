package net.crisjr.repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import net.crisjr.dto.ReporteDTO;
import net.crisjr.model.AporteGrupal;
import net.crisjr.model.Grupo;


public interface AporteGrupalRepository extends JpaRepository<AporteGrupal, Integer> {
    
    List<AporteGrupal> findByGrupo(Grupo grupo);
    boolean existsByGrupoAndFecha(Grupo grupo, java.time.LocalDate fecha);

    @Query("SELECT new net.crisjr.dto.ReporteDTO(g.sector.nombre, SUM(ag.montoTotal)) " +
       "FROM AporteGrupal ag " +
       "JOIN ag.grupo g " +
       "WHERE ag.fecha BETWEEN :desde AND :hasta " +
       "GROUP BY g.sector.nombre")
    List<ReporteDTO> obtenerTotalesPorSector(LocalDate desde, LocalDate hasta);

    @Query("SELECT g.sector.nombre, SUM(ag.montoTotal) FROM AporteGrupal ag JOIN ag.grupo g GROUP BY g.sector.nombre")
    List<Object[]> obtenerTotalRecaudadoPorSector();

    @Query("SELECT new net.crisjr.dto.ReporteDTO(g.nombre, SUM(ag.montoTotal)) " +
        "FROM AporteGrupal ag " +
        "JOIN ag.grupo g " +
        "WHERE g.sector.id = :sectorId AND ag.fecha BETWEEN :desde AND :hasta " +
        "GROUP BY g.nombre")
    List<ReporteDTO> obtenerTotalesPorGrupo(LocalDate desde, LocalDate hasta, Integer sectorId);

    @Query("SELECT new net.crisjr.dto.ReporteDTO(g.nombre, SUM(ag.montoTotal)) " +
        "FROM AporteGrupal ag " +
        "JOIN ag.grupo g " +
        "WHERE g.id = :grupoId AND ag.fecha BETWEEN :desde AND :hasta " +
        "GROUP BY g.nombre")
    List<ReporteDTO> obtenerTotalDeUnGrupo(LocalDate desde, LocalDate hasta, Integer grupoId);

    @Query("SELECT SUM(ag.montoTotal) FROM AporteGrupal ag " +
       "JOIN ag.grupo g " +
       "WHERE g.sector.id = :sectorId AND ag.fecha BETWEEN :inicio AND :fin")
    BigDecimal sumaAportesPorSectorYMes(@Param("sectorId") Integer sectorId,
                                        @Param("inicio") LocalDate inicio,
                                        @Param("fin") LocalDate fin);


    @Query("SELECT ag FROM AporteGrupal ag WHERE ag.grupo.id = :grupoId AND ag.fecha BETWEEN :desde AND :hasta ORDER BY ag.fecha")
    List<AporteGrupal> findPagosPorGrupoYFechas(@Param("grupoId") Integer grupoId,
                                            @Param("desde") LocalDate desde,
                                            @Param("hasta") LocalDate hasta);
}
