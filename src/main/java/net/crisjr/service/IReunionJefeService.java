package net.crisjr.service;

import java.util.List;

import net.crisjr.model.ReunionJefe;

public interface IReunionJefeService {

    void guardarReunionJefe(ReunionJefe reunionJefe);
    List<ReunionJefe> listarTodos();
}
