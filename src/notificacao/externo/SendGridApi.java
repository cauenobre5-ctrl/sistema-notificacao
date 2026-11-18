package notificacao.externo;

import java.util.Map;

/** Adaptee (simulado): SDK de terceiro com API própria, que NÃO podemos alterar. */
public class SendGridApi {
    private final String apiKey;

    public SendGridApi(String apiKey) { this.apiKey = apiKey; }

    /** Recebe um payload em Map e devolve um código HTTP. */
    public int send(Map<String, String> payload) {
        System.out.println("      [SendGrid] POST /v3/mail/send key=" + apiKey.substring(0, 3)
                + "*** to=" + payload.get("to"));
        return 202;
    }
}
