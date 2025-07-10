package net.crisjr.service.db;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import net.crisjr.model.Usuario;
import net.crisjr.model.Vehiculo;
import net.crisjr.repository.VehiculosRepository;
import net.crisjr.service.IVehiculosService;

@Service
@Primary
public class VehiculosServiceJpa implements IVehiculosService {
 
    @Autowired
    private VehiculosRepository vehiculosRepo;

    @Override
    public void guardar(Vehiculo vehiculo) { 
        vehiculosRepo.save(vehiculo);
    }

    @Override
    public List<Vehiculo> buscarTodas() {
        return vehiculosRepo.findAll();
    } 

    @Override
    public Vehiculo buscarPorId(Integer idVehiculo) {
        Optional<Vehiculo> optional= vehiculosRepo.findById(idVehiculo);
        if(optional.isPresent()){
            return optional.get();
        }
        return null;
    }

    @Override
    public List<Vehiculo> obtenerPorUsuarioYEstado(Usuario usuario, String estado) {
        return vehiculosRepo.findByUsuarioIdAndEstado(usuario, estado);
    }

    @Override
    public long contarVehiculosActivosPorUsuario(Usuario usuario, Integer excluirId) {
        return vehiculosRepo.countVehiculosActivosByUsuario(usuario, excluirId);
    }

}
  