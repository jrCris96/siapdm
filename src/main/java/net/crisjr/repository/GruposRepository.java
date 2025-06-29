package net.crisjr.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import net.crisjr.model.Grupo;

public interface GruposRepository extends JpaRepository<Grupo, Integer> {
    List<Grupo> findBySectorId(Integer sectorId);
} 
