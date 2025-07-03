package net.crisjr.service;

import java.util.List;


import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import net.crisjr.model.Usuario;
 
public interface IUsuariosService {
    void guardar(Usuario usuario); 
    List<Usuario> buscarTodas();
    Usuario buscarPorId(Integer idUsuario);
    List<Usuario> buscarByExample(Example<Usuario> example);
    List<Usuario> buscarPorFiltros(String idUsuario, Integer idGrupo, Integer idSector);
    Page<Usuario>buscarTodas(Pageable page);
    Usuario buscarPorIdUsuario(String idUsuario);
    List<Usuario> buscarPorGrupo(Integer grupoId);
}
