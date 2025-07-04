package net.crisjr.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import net.crisjr.model.Amonestacion;
import net.crisjr.model.Usuario;

public interface AmonestacionRepository extends JpaRepository<Amonestacion, Integer> {
    List<Amonestacion> findBySocio(Usuario socio);
}
