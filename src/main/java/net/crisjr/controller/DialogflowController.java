package net.crisjr.controller;

import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import net.crisjr.model.Amonestacion;
import net.crisjr.model.AporteSocio;
import net.crisjr.model.Usuario;
import net.crisjr.service.IAmonestacionService;
import net.crisjr.service.IAporteSocioService;
import net.crisjr.service.IUsuariosService;

@RestController
@RequestMapping("/api/dialogflow")
public class DialogflowController {

    @Autowired
    private IUsuariosService serviceUsuario;

    @Autowired
    private IAmonestacionService amonestacionService;

    @Autowired
    private IAporteSocioService aporteSocioService;

    @SuppressWarnings("unchecked")
    @PostMapping("/fulfillment")
    public Map<String, Object> fulfillment(@RequestBody Map<String, Object> request) {

        String numeroWhatsApp = null;
        try {
            Map<String, Object> originalRequest = (Map<String, Object>) request.get("originalDetectIntentRequest");
            if (originalRequest != null) {
                Map<String, Object> payload = (Map<String, Object>) originalRequest.get("payload");
                if (payload != null && payload.containsKey("data")) {
                    Map<String, Object> data = (Map<String, Object>) payload.get("data");
                    if (data != null && data.containsKey("contacts")) {
                        List<Map<String, Object>> contacts = (List<Map<String, Object>>) data.get("contacts");
                        if (contacts != null && !contacts.isEmpty()) {
                            numeroWhatsApp = contacts.get(0).get("wa_id").toString();
                        }
                    }
                }
            }
        } catch (Exception e) {
            numeroWhatsApp = null;
        }
        // Extrae el intent
        Map<String, Object> queryResult = (Map<String, Object>) request.get("queryResult");
        String intent = ((Map<String, Object>) queryResult.get("intent")).get("displayName").toString();

        String response = "Lo siento, no entendí tu consulta.";

        
        if ("ConsultarPagos".equals(intent)) {
            String pagosRespuesta = "No se encontraron datos con tu número registrado.";
            String numeroSolo = null;

            if (numeroWhatsApp != null && numeroWhatsApp.startsWith("591") && numeroWhatsApp.length() > 8) {
                numeroSolo = numeroWhatsApp.substring(3);
            }

            if (numeroSolo != null) {
                Usuario socio = serviceUsuario.buscarPorCelular(numeroSolo);
                if (socio != null) {
                    List<AporteSocio> pagos = aporteSocioService.listarPagosPorIdSocio(socio.getId());
                    if (pagos != null && !pagos.isEmpty()) {
                        StringBuilder detalle = new StringBuilder("Pagos registrados:\n");
                        for (AporteSocio a : pagos) {
                            LocalDate fecha = a.getAporteGrupal().getFecha();
                            String mes = fecha.getMonth().getDisplayName(TextStyle.FULL, new Locale("es")).toUpperCase();
                            String año = String.valueOf(fecha.getYear());
                            String estadoPago = (a.getPagado() != null && a.getPagado()) ? "Pagado" : "No pagado";

                            detalle.append("- Mes: ").append(mes).append(" ").append(año)
                                .append(" | Estado: ").append(estadoPago)
                                .append("\n");
                        }
                        pagosRespuesta = detalle.toString();
                    } else {
                        pagosRespuesta = "No hay registros de pagos asociados a tu cuenta.";
                    }
                }
            } else {
                pagosRespuesta = "Simulación:\n- Mes: julio 2025 | Estado: Pagado\n- Mes: agosto 2025 | Estado: No pagado";
            }

            response = pagosRespuesta;
        }

        else if ("ConsultarAmonestaciones".equals(intent)) {
            String amonestacionRespuesta = "No se encontraron datos con tu número registrado.";
            String numeroSolo = null;

            if (numeroWhatsApp != null && numeroWhatsApp.startsWith("591") && numeroWhatsApp.length() > 8) {
                numeroSolo = numeroWhatsApp.substring(3);
            }

            if (numeroSolo != null) {
                Usuario socio = serviceUsuario.buscarPorCelular(numeroSolo);
                if (socio != null) {
                    List<Amonestacion> amonestaciones = amonestacionService.obtenerPorUsuarioId(socio.getId());
                    if (amonestaciones != null && !amonestaciones.isEmpty()) {
                        StringBuilder detalle = new StringBuilder("Tienes " + amonestaciones.size() + " amonestación(es):\n");
                        for (Amonestacion a : amonestaciones) {
                            detalle.append("- ")
                                .append("Nivel: ").append(a.getNivel())
                                .append(" | Fecha: ").append(a.getFecha())
                                .append(" | Motivo: ").append(a.getDescripcion())
                                .append("\n");
                        }
                        amonestacionRespuesta = detalle.toString();
                    } else {
                        amonestacionRespuesta = "¡No tienes amonestaciones!";
                    }
                }
            } else {
                amonestacionRespuesta = "Simulación: Tienes 1 amonestación registrada.\n- Nivel: Segundo tiempo | Fecha: 2025-07-21 | Motivo: Falta de puntualidad";
            }

            response = amonestacionRespuesta;
        }



        // Intent para ver datos personales
        else if ("VerDato".equals(intent) || "ConsultarDatos".equals(intent)) {
            String datosRespuesta = "No se encontraron datos con tu número registrado.";
            String numeroSolo = null;

            // Extrae y normaliza el número de WhatsApp (si viene desde WhatsApp real)
            if (numeroWhatsApp != null && numeroWhatsApp.startsWith("591") && numeroWhatsApp.length() > 8) {
                numeroSolo = numeroWhatsApp.substring(3); // Ej: "59162392330" -> "62392330"
            }

            // Si llega desde WhatsApp real
            if (numeroSolo != null) {
                Usuario socio = serviceUsuario.buscarPorCelular(numeroSolo); // Tu servicio personalizado
                if (socio != null) {
                    datosRespuesta = "Tus datos son:\n" +
                            "Nombre: " + socio.getNombre() + " " + socio.getApellido() + "\n" +
                            "Carnet: " + socio.getCarnet() + "\n" +
                            "Grupo: " + (socio.getGrupo() != null ? socio.getGrupo().getNombre() : "No asignado");
                }
            } else {
                // Si estás probando en el simulador de Dialogflow (número es null)
                datosRespuesta = "Simulación:\nNombre: Juan Pérez\nCarnet: 12345678\nGrupo: Minibuses - Grupo 1";
            }

            response = datosRespuesta;
        }



        Map<String, Object> result = new HashMap<>();
        result.put("fulfillmentText", response);
        return result;
    }
}
