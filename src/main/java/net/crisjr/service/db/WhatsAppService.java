package net.crisjr.service.db;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class WhatsAppService {
    private static final String WHATSAPP_API_URL = "https://graph.facebook.com/v19.0/779223828598133/messages";
    private static final String TOKEN = "EAA5tszEXctMBPAVtaRCGYSLY6TzxT0ZBBwc16oZB6aQas2IgEcZBWpHTPOOE1tZB7WEnwZAti6ayZCHQD9zKoGZBWAQUI9iVD8WWKPC4xlZARQSH3bzOtB5Y5U50A0EawOKqHozDEEvODbiavHtiGiEi2G25OLfQn5RSVsghFFHf25DSuNCLihZBHUFk15b3ZAb0XH40oTTQmJXPrVGhaTDgIB9ZAHnBVw0TRurfbTwSLvyp82otecZD";

    public void enviarMensaje(String numeroDestino, String mensaje) throws Exception {
        RestTemplate restTemplate = new RestTemplate();

        Map<String, Object> body = new HashMap<>();
        body.put("messaging_product", "whatsapp");
        body.put("to", numeroDestino);
        body.put("type", "text");

        Map<String, String> text = new HashMap<>();
        text.put("body", mensaje);

        body.put("text", text);

        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(body);

        System.out.println("Body JSON: " + json);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(TOKEN);

        HttpEntity<String> entity = new HttpEntity<>(json, headers);

        ResponseEntity<String> response = restTemplate.postForEntity(WHATSAPP_API_URL, entity, String.class);

        System.out.println("Respuesta WhatsApp: " + response.getBody());
    }
}
