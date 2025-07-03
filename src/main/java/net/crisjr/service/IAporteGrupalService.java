package net.crisjr.service;

import java.util.List;

import net.crisjr.model.AporteGrupal;
import net.crisjr.model.Grupo;

public interface IAporteGrupalService {
    void guardar(AporteGrupal aporteGrupal);
    List<AporteGrupal> buscarPorGrupo(Grupo grupo);
    AporteGrupal buscarPorId(Integer id);
    boolean existeRegistro(Grupo grupo, java.time.LocalDate fecha);
}
