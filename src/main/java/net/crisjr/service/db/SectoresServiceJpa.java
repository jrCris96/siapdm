package net.crisjr.service.db;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.crisjr.model.Sector;
import net.crisjr.repository.SectoresRepository;
import net.crisjr.service.ISectorService;

@Service
public class SectoresServiceJpa implements ISectorService {

    @Autowired
    private SectoresRepository sectoresRepo;

    @Override
    public List<Sector> buscarTodas() {
       return sectoresRepo.findAll();
    }

}
