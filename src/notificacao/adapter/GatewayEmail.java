package notificacao.adapter;

/** ADAPTER — Target: interface que o sistema espera para enviar e-mails. */
public interface GatewayEmail {
    boolean enviarEmail(String remetente, String destinatario, String assunto, String corpoHtml);
}
