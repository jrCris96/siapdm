package net.crisjr.service;

import java.util.List;

import net.crisjr.dto.PagoDTO;
import net.crisjr.model.AporteGrupal;
import net.crisjr.model.AporteSocio;
import net.crisjr.model.Usuario;

public interface IAporteSocioService {
    void guardar(AporteSocio aporteSocio);
    List<AporteSocio> buscarPorAporteGrupal(AporteGrupal aporteGrupal);
    List<AporteSocio> buscarPorUsuario(Usuario usuario);
    boolean existeRegistro(Usuario usuario, AporteGrupal aporteGrupal);
    AporteSocio buscarPorId(Integer id);
    List<PagoDTO> obtenerPagosPorSocio(Integer idSocio);

}
