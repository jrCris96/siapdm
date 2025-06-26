package net.crisjr.service.db;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.crisjr.model.Perfil;
import net.crisjr.repository.PerfilRepository;
import net.crisjr.service.IPerfilService;

@Service
public class PerfilServicejpa implements IPerfilService{

    @Autowired
    private PerfilRepository perfilRepo;

    @Override
    public List<Perfil> buscarTodas() {
        return perfilRepo.findAll();
    }   

}
