package net.crisjr.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import net.crisjr.model.CargoMesa;
import net.crisjr.model.DetalleMesa;
import net.crisjr.model.MesaDirectiva;
import net.crisjr.model.Usuario;

public interface DetalleMesaRepository extends JpaRepository<DetalleMesa, Integer>{
    List<DetalleMesa> findByMesaDirectiva(MesaDirectiva mesa);
    List<DetalleMesa> findByMesaDirectivaAndEstado(MesaDirectiva mesa, boolean estado);
    long countByMesaDirectivaAndEstado(MesaDirectiva mesa, boolean estado);

    List<DetalleMesa> findByMesaDirectivaAndEstadoTrue(MesaDirectiva mesa);
    boolean existsByMesaDirectivaAndUsuarioAndEstadoTrue(MesaDirectiva mesa, Usuario usuario);
    
    List<DetalleMesa> findByCargo_IdAndEstado(Integer idCargo, boolean estado);
    boolean existsByMesaDirectivaAndCargoAndEstadoTrue(MesaDirectiva mesa, CargoMesa cargo);

}
