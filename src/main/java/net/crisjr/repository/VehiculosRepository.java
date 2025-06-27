package net.crisjr.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import net.crisjr.model.Usuario;
import net.crisjr.model.Vehiculo;

public interface VehiculosRepository extends JpaRepository<Vehiculo, Integer> {
    long countByUsuarioId(Usuario usuario);
    List<Vehiculo> findByUsuarioId_Id(Integer idUsuario);
}
  