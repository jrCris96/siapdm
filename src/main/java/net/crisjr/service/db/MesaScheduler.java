package net.crisjr.service.db;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import net.crisjr.model.DetalleMesa;
import net.crisjr.model.MesaDirectiva;
import net.crisjr.service.IDetalleMesaService;
import net.crisjr.service.IMesaDirectivaService;

@Service
public class MesaScheduler {

    @Autowired
    private IMesaDirectivaService mesaService;

    @Autowired
    private IDetalleMesaService detalleService;

    @Scheduled(cron = "0 0 3 * * *") // Todos los días a las 03:00 AM
    public void verificarMesasFinalizadas() {
        List<MesaDirectiva> mesas = mesaService.buscarTodas(); // O solo activas si tienes ese filtro
        Date hoy = new Date();

        for (MesaDirectiva mesa : mesas) {
            if (mesa.getFecha_fin() != null && mesa.getFecha_fin().before(hoy)) {
                List<DetalleMesa> integrantes = detalleService.buscarPorMesaActivos(mesa);
                for (DetalleMesa detalle : integrantes) {
                    detalle.setEstado(false);
                    detalleService.guardar(detalle); 
                }
            }
        }
    }
}
