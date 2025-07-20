package net.crisjr.service.db;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.crisjr.model.CargoMesa;
import net.crisjr.model.DetalleMesa;
import net.crisjr.model.MesaDirectiva;
import net.crisjr.model.Usuario;
import net.crisjr.repository.DetalleMesaRepository;
import net.crisjr.service.IDetalleMesaService;

@Service
public class DetalleMesaService implements IDetalleMesaService{

    @Autowired
    private DetalleMesaRepository detalleMesaRepo;

    @Override
    public void guardar(DetalleMesa detalle) {
        detalleMesaRepo.save(detalle);
    }

    @Override
    public List<DetalleMesa> buscarPorMesa(MesaDirectiva mesa) {
        return detalleMesaRepo.findByMesaDirectiva(mesa);
    }

    @Override
    public List<DetalleMesa> buscarPorMesaActivos(MesaDirectiva mesa) {
        return detalleMesaRepo.findByMesaDirectivaAndEstadoTrue(mesa);
    }
    @Override
    public DetalleMesa buscarPorId(Integer id) {
        return detalleMesaRepo.findById(id).orElse(null);
    }

    @Override
    public boolean existeSocioEnMesa(MesaDirectiva mesa, Usuario socio) {
        return detalleMesaRepo.existsByMesaDirectivaAndUsuarioAndEstadoTrue(mesa, socio);
    }

    @Override
    public List<DetalleMesa> buscarHaciendasActivas() {
        return detalleMesaRepo.findByCargo_IdAndEstado(3, true);
    }

    @Override
    public boolean existeCargoEnMesa(MesaDirectiva mesa, CargoMesa cargo) {
        return detalleMesaRepo.existsByMesaDirectivaAndCargoAndEstadoTrue(mesa, cargo);
    }

}
