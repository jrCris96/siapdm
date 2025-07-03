package net.crisjr.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import net.crisjr.model.AporteGrupal;
import net.crisjr.model.AporteSocio;
import net.crisjr.model.Usuario;

public interface AporteSocioRepository extends JpaRepository<AporteSocio, Integer> {
    List<AporteSocio> findByAporteGrupal(AporteGrupal aporteGrupal);

    List<AporteSocio> findByUsuario(Usuario usuario);

    boolean existsByUsuarioAndAporteGrupal(Usuario usuario, AporteGrupal aporteGrupal);
}
