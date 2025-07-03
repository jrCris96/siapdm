package net.crisjr.service.db;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.crisjr.repository.RetirosSindicatoRepository;
import net.crisjr.model.DetalleMesa;
import net.crisjr.model.RetiroSindicato;
import net.crisjr.service.IRetirosSindicatoService;

@Service
public class RetirosSindicatoService implements IRetirosSindicatoService{

    @Autowired
    private RetirosSindicatoRepository repo;

    @Override
    public void guardar(RetiroSindicato retiro) {
        repo.save(retiro);
    }

    @Override
    public List<RetiroSindicato> buscarTodos() {
        return repo.findAllByOrderByFechaDesc();
    }

    @Override
    public RetiroSindicato buscarPorId(Integer id) {
        return repo.findById(id).orElse(null);
    }

    @Override
    public List<RetiroSindicato> buscarPorMesaDetalle(DetalleMesa mesaDetalle) {
        return repo.findByDetalleMesa(mesaDetalle);
    }
}
