package net.crisjr.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import net.crisjr.model.Usuario;

public interface UsuariosRepository extends JpaRepository<Usuario, Integer> {
    Usuario findByCarnet(String carnet);
    Usuario findByIdUsuario(String idUsuario);

    @Query("SELECT u FROM Usuario u WHERE " +
       "(:idUsuario IS NULL OR u.idUsuario = :idUsuario) AND " +
       "(:idGrupo IS NULL OR u.grupo.id = :idGrupo) AND " +
       "(:idSector IS NULL OR u.grupo.sector.id = :idSector)")
    List<Usuario> buscarPorFiltros(@Param("idUsuario") String idUsuario, @Param("idGrupo") Integer idGrupo, @Param("idSector") Integer idSector);

    List<Usuario> findByGrupoId(Integer grupoId);
}
  