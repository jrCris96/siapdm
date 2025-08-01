package net.crisjr.service.db;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.crisjr.model.Grupo;
import net.crisjr.model.Sector;
import net.crisjr.repository.SectoresRepository;
import net.crisjr.service.IGruposService;
import net.crisjr.service.ISectorService;

@Service
public class SectoresServiceJpa implements ISectorService {

    @Autowired
    private SectoresRepository sectoresRepo;
    @Autowired
    private IGruposService grupoRepo;

    @Override
    public List<Sector> buscarTodas() {
       return sectoresRepo.findAll();
    }

    @Override
    public Sector buscarPorId(Integer idSector) {
        Optional<Sector> optional= sectoresRepo.findById(idSector);
        if(optional.isPresent()){
            return optional.get();
        }
        return null;
    }

    @Override
    public List<Grupo> buscarGruposPorSector(Integer sectorId) {
        return grupoRepo.findBySectorId(sectorId);
    }

}
