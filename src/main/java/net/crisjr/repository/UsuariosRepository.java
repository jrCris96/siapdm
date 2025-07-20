package net.crisjr.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import net.crisjr.model.Usuario;

public interface UsuariosRepository extends JpaRepository<Usuario, Integer> {
    Usuario findByCarnet(String carnet);
    Usuario findByIdUsuario(String idUsuario);

    @Query("SELECT u FROM Usuario u " +
       "WHERE (:idUsuario IS NULL OR u.idUsuario = :idUsuario) " +
       "AND (:idGrupo IS NULL OR u.grupo.id = :idGrupo) " +
       "AND (:idSector IS NULL OR u.grupo.sector.id = :idSector) " +
       "AND (:estado IS NULL OR u.estado = :estado) " +
       "ORDER BY u.fechaIngreso DESC")
    List<Usuario> buscarPorFiltros(
        @Param("idUsuario") String idUsuario,
        @Param("idGrupo") Integer idGrupo,
        @Param("idSector") Integer idSector,
        @Param("estado") String estado
    );

    Page<Usuario> findByEstadoOrderByFechaIngresoDesc(String estado, Pageable pageable);

    List<Usuario> findByGrupoId(Integer grupoId);

    @Query("SELECT u FROM Usuario u WHERE u.grupo.id = :idGrupo AND u.estado = 'habilitado'")
    List<Usuario> findHabilitadosByGrupo(@Param("idGrupo") Integer idGrupo);

    @Query("SELECT u.grupo.sector.nombre, COUNT(u) FROM Usuario u WHERE u.estado = 'habilitado' GROUP BY u.grupo.sector.nombre")
    List<Object[]> contarSociosPorSector();


}
  