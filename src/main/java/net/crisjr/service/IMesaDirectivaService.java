package net.crisjr.service;

import java.util.List;

import net.crisjr.model.MesaDirectiva;

public interface IMesaDirectivaService {
    void guardar(MesaDirectiva mesa);
    List<MesaDirectiva> buscarTodas();
    MesaDirectiva buscarPorId(Integer idMesa);

    boolean hayMesaActiva(); // Para validar si ya hay una mesa vigente
    MesaDirectiva buscarMesaActiva(); // Para mostrar la mesa activa actual
}
