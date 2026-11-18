package notificacao.adapter;

import notificacao.externo.TwilioClient;

/** ADAPTER — converte exceções e SID do Twilio em um simples boolean. */
public class TwilioAdapter implements GatewaySms {
    private final TwilioClient client;
    private final String numeroOrigem;

    public TwilioAdapter(TwilioClient client, String numeroOrigem) {
        this.client = client;
        this.numeroOrigem = numeroOrigem;
    }

    @Override
    public boolean enviarSms(String telefone, String texto) {
        try {
            return client.createMessage(numeroOrigem, telefone, texto) != null;
        } catch (IllegalStateException e) {
            System.out.println("      [TwilioAdapter] erro do provedor: " + e.getMessage());
            return false;
        }
    }
}
