package net.crisjr.service;

import java.util.List;

import net.crisjr.model.Usuario;
import net.crisjr.model.Vehiculo;

public interface IVehiculosService {

    void guardar(Vehiculo vehiculo);
    List<Vehiculo> buscarTodas();
    Vehiculo buscarPorId(Integer idVehiculo);
    List<Vehiculo> obtenerPorUsuarioYEstado(Usuario usuario, String estado);
    long contarVehiculosActivosPorUsuario(Usuario usuario, Integer excluirId);
    boolean asalariadoYaTieneVehiculoActivo(Usuario asalariado);
}
   