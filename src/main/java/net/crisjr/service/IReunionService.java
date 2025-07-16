package net.crisjr.service;

import java.util.List;

import net.crisjr.model.Reunion;

public interface IReunionService {
    void guardarReunion(Reunion reunion);
    List<Reunion> listarReuniones();
    Reunion obtenerPorId(Integer id);
}
