package net.crisjr.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import net.crisjr.model.DetalleMesa;
import net.crisjr.model.RetiroSindicato;

public interface RetirosSindicatoRepository extends JpaRepository<RetiroSindicato, Integer>{
        // Buscar retiros hechos por un miembro específico
    List<RetiroSindicato> findByDetalleMesa(DetalleMesa detalleMesa);

    // Buscar todos los retiros por orden descendente
    List<RetiroSindicato> findAllByOrderByFechaDesc();
}
