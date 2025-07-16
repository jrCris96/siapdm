package net.crisjr.service.db;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.crisjr.model.ReunionJefe;
import net.crisjr.repository.ReunionJefeRepository;
import net.crisjr.service.IReunionJefeService;

@Service
public class ReunionJefeService implements IReunionJefeService{

    @Autowired
    private ReunionJefeRepository reunionJefeRepository;

    @Override
    public void guardarReunionJefe(ReunionJefe reunionJefe) {
        reunionJefeRepository.save(reunionJefe);
    }

    @Override
    public List<ReunionJefe> listarTodos() {
        return reunionJefeRepository.findAll();
    }

}
