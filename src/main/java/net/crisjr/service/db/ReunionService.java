package net.crisjr.service.db;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.crisjr.model.JefeGrupo;
import net.crisjr.model.Reunion;
import net.crisjr.model.ReunionJefe;
import net.crisjr.repository.ReunionRepository;
import net.crisjr.service.IJefeGrupoService;
import net.crisjr.service.IReunionJefeService;
import net.crisjr.service.IReunionService;

@Service
public class ReunionService implements IReunionService{ 

    @Autowired
    private ReunionRepository reunionRepository;

    @Autowired
    private IJefeGrupoService jefeGrupoService;

    @Autowired
    private IReunionJefeService reunionJefeService;

    @Autowired
    private WhatsAppService whatsAppService;

    @Override
    public void guardarReunion(Reunion reunion) {
        reunionRepository.save(reunion);
    }

    @Override
    public List<Reunion> listarReuniones() {
        return reunionRepository.findAll();
    }

    @Override
    public Reunion obtenerPorId(Integer id) {
        return reunionRepository.findById(id).orElse(null);
    }

    public void crearYNotificarReunion(Reunion reunion) {
    // 1. Guardar la reunión
    reunionRepository.save(reunion);

    // 2. Buscar todos los jefes de grupo activos
    List<JefeGrupo> jefesActivos = jefeGrupoService.buscarTodosActivos();

    // 3. Para cada jefe, crear ReunionJefe y enviar mensaje solo si el número es válido
    for (JefeGrupo jefe : jefesActivos) {
        ReunionJefe reunionJefe = new ReunionJefe();
        reunionJefe.setReunion(reunion);
        reunionJefe.setJefeGrupo(jefe);
        reunionJefe.setMensajeEnviado(false);

        String celular = jefe.getSocio().getCelular();

        if (celular != null && celular.matches("\\d{8}")) {
            String numero = "+591" + celular;
            String mensaje = "Nueva reunión:\nAsunto: " + reunion.getAsunto()
                    + "\nDetalle: " + reunion.getDetalle()
                    + "\nFecha: " + reunion.getFechaReunion();

            try {
                whatsAppService.enviarMensaje(numero, mensaje);
                reunionJefe.setMensajeEnviado(true);
            } catch (Exception e) {
                e.printStackTrace(); // Así verás el error exacto
                reunionJefe.setMensajeEnviado(false);
                // Puedes loguear el error si deseas
            }
        } else {
            // Aquí puedes registrar en logs o notificar que el número está mal
            System.out.println("Número inválido para WhatsApp: " + celular 
                + " (Socio: " + jefe.getSocio().getNombre() + ")");
            reunionJefe.setMensajeEnviado(false);
        }

        reunionJefeService.guardarReunionJefe(reunionJefe);
    }
}

    
}