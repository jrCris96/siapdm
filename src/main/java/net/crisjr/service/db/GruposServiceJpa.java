package net.crisjr.service.db;

import java.util.List;
import java.util.Optional;

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
    public Grupo buscarPorId(Integer idGrupo) {
        Optional<Grupo> optional= gruposRepo.findById(idGrupo);
        if(optional.isPresent()){
            return optional.get();
        }
        return null;
    }

    @Override
    public List<Grupo> findBySectorId(Integer sectorId) {
        return gruposRepo.findBySectorId(sectorId);
    }
    
}
