package net.crisjr.service;

import java.util.List;

import net.crisjr.model.Usuario;

public interface IUsuariosService {
    void guardar(Usuario usuario);
    List<Usuario> buscarTodas();
    Usuario buscarPorId(Integer idUsuario);
}
