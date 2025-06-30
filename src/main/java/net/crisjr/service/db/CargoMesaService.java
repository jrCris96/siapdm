package net.crisjr.service.db;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.crisjr.model.CargoMesa;
import net.crisjr.repository.CargoMesaRepository;
import net.crisjr.service.ICargoMesaService;

@Service
public class CargoMesaService implements ICargoMesaService {

    @Autowired
    private CargoMesaRepository cargoMesaRepo;

    @Override
    public List<CargoMesa> buscarTodas() {

        return cargoMesaRepo.findAll();
    }

}
