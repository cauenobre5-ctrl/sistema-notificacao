package notificacao.adapter;

import java.util.HashMap;
import java.util.Map;
import notificacao.externo.SendGridApi;

/** ADAPTER — converte a chamada GatewayEmail em payload Map + código HTTP do SendGrid. */
public class SendGridAdapter implements GatewayEmail {
    private final SendGridApi api;

    public SendGridAdapter(SendGridApi api) { this.api = api; }

    @Override
    public boolean enviarEmail(String remetente, String destinatario, String assunto, String corpoHtml) {
        Map<String, String> payload = new HashMap<>();
        payload.put("from", remetente);
        payload.put("to", destinatario);
        payload.put("subject", assunto);
        payload.put("html", corpoHtml);
        int status = api.send(payload);
        return status >= 200 && status < 300;
    }
}
