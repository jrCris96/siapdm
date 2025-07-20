package net.crisjr.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import net.crisjr.model.Modalidad;

public interface ModalidadRepository extends JpaRepository<Modalidad, Integer>{
    // Puedes agregar consultas personalizadas aquí si necesitas
    List<Modalidad> findBySectorId(Integer sectorId);
}
