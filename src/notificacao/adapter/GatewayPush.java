package notificacao.adapter;

/** ADAPTER — Target: interface que o sistema espera para enviar push. */
public interface GatewayPush {
    boolean enviarPush(String deviceToken, String titulo, String corpo);
}
