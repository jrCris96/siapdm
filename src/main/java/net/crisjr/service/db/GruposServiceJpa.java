package net.crisjr.service.db;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.crisjr.model.Grupo;
import net.crisjr.repository.GruposRepository;
import net.crisjr.service.IGruposService;

@Service
public class GruposServiceJpa implements IGruposService {

    @Autowired
    private GruposRepository gruposRepo;

    @Override 
    public List<Grupo> buscarTodas() {
        return gruposRepo.findAll();
    }

    @Override
    public List<Grupo> findBySectorId(Long sectorId) {
        return gruposRepo.findBySectorId(sectorId);
    }
}
