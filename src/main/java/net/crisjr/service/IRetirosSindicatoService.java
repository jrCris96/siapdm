package net.crisjr.service;

import net.crisjr.model.RetiroSindicato;
import net.crisjr.model.DetalleMesa;

import java.util.List;

public interface IRetirosSindicatoService {
    void guardar(RetiroSindicato retiro);
    List<RetiroSindicato> buscarTodos();
    RetiroSindicato buscarPorId(Integer id);
    List<RetiroSindicato> buscarPorMesaDetalle(DetalleMesa mesaDetalle);
}
