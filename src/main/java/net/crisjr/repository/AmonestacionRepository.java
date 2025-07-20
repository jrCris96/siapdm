package net.crisjr.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import net.crisjr.model.Amonestacion;
import net.crisjr.model.Usuario;

public interface AmonestacionRepository extends JpaRepository<Amonestacion, Integer> {
    List<Amonestacion> findBySocio(Usuario socio);

    @Query("SELECT a FROM Amonestacion a WHERE a.socio.grupo.sector.id = :sectorId")
    List<Amonestacion> findBySectorId(@Param("sectorId") Integer sectorId);

    List<Amonestacion> findBySocio_Id(Integer idUsuario);
;


}
