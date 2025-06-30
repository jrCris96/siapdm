package net.crisjr.service;

import java.util.List;
 
import net.crisjr.model.DetalleMesa;
import net.crisjr.model.MesaDirectiva;
import net.crisjr.model.Usuario;

public interface IDetalleMesaService {
    void guardar(DetalleMesa detalle);
    List<DetalleMesa> buscarPorMesa(MesaDirectiva mesa);
    List<DetalleMesa> buscarPorMesaActivos(MesaDirectiva mesa);
    boolean existeSocioEnMesa(MesaDirectiva mesa, Usuario socio);
    DetalleMesa buscarPorId(Integer id);

}
