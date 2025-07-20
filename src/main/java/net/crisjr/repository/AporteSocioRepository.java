package net.crisjr.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import net.crisjr.dto.PagoDTO;
import net.crisjr.model.AporteGrupal;
import net.crisjr.model.AporteSocio;
import net.crisjr.model.Usuario;

public interface AporteSocioRepository extends JpaRepository<AporteSocio, Integer> {
    List<AporteSocio> findByAporteGrupal(AporteGrupal aporteGrupal);

    List<AporteSocio> findByUsuario(Usuario usuario);

    boolean existsByUsuarioAndAporteGrupal(Usuario usuario, AporteGrupal aporteGrupal);

    @Query("SELECT new net.crisjr.dto.PagoDTO(ag.fecha, a.pagado) " +
        "FROM AporteSocio a JOIN a.aporteGrupal ag " +
        "WHERE a.usuario.id = :idSocio ORDER BY ag.fecha DESC")
    List<PagoDTO> obtenerPagosPorSocio(@Param("idSocio") Integer idSocio);

}
