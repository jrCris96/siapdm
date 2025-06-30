package net.crisjr.service.db;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.crisjr.model.MesaDirectiva;
import net.crisjr.repository.MesaDirectivaRepository;
import net.crisjr.service.IMesaDirectivaService;

@Service
public class MesaDirectivaService implements IMesaDirectivaService {

    @Autowired
    private MesaDirectivaRepository mesaDirectivaRepo;

    @Override
    public List<MesaDirectiva> buscarTodas() {
        return mesaDirectivaRepo.findAll();
    }

    @Override
    public void guardar(MesaDirectiva mesa) {
        mesaDirectivaRepo.save(mesa);
    }

    @Override
    public MesaDirectiva buscarPorId(Integer idMesa) {
        Optional<MesaDirectiva> optional = mesaDirectivaRepo.findById(idMesa);
        return optional.orElse(null);
    }

    @Override
    public boolean hayMesaActiva() {
        Date hoy = new Date();
        return mesaDirectivaRepo.existsByFechaFinAfter(hoy);
    }

    @Override
    public MesaDirectiva buscarMesaActiva() {
        Date hoy = new Date();
        return mesaDirectivaRepo.findFirstByFechaFinAfterOrderByFechaInicioDesc(hoy);
    }

}
