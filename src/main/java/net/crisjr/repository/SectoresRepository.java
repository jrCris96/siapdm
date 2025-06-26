package net.crisjr.repository;

import org.springframework.data.jpa.repository.JpaRepository; 

import net.crisjr.model.Sector;

public interface SectoresRepository extends JpaRepository<Sector, Integer>  {

}
