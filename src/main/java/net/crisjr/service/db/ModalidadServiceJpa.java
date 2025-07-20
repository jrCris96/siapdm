package net.crisjr.service.db;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.crisjr.model.Modalidad;
import net.crisjr.repository.ModalidadRepository;
import net.crisjr.service.IModalidadService;

@Service
public class ModalidadServiceJpa implements IModalidadService{

    @Autowired
    private ModalidadRepository modalidadRepo;

    @Override
    public List<Modalidad> listarTodas() {
        return modalidadRepo.findAll();
    }

    @Override
    public Modalidad guardar(Modalidad modalidad) {
        return modalidadRepo.save(modalidad);
    }

    @Override
    public void eliminar(Integer id) {
        modalidadRepo.deleteById(id);
    }

    @Override
    public Modalidad buscarPorId(Integer id) {
        return modalidadRepo.findById(id).orElse(null);
    }

    @Override
    public List<Modalidad> listarPorSector(Integer sectorId) {
        return modalidadRepo.findBySectorId(sectorId);
    }
}
