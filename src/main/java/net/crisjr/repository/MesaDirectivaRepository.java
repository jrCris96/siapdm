package net.crisjr.repository;

import java.util.Date;

import org.springframework.data.jpa.repository.JpaRepository;

import net.crisjr.model.MesaDirectiva;

public interface MesaDirectivaRepository extends JpaRepository<MesaDirectiva, Integer> {
    boolean existsByFechaFinAfter(Date fecha);
    MesaDirectiva findFirstByFechaFinAfterOrderByFechaInicioDesc(Date fecha);
}
