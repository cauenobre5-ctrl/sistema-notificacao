package notificacao.adapter;

/** ADAPTER — Target: interface que o sistema espera para enviar SMS. */
public interface GatewaySms {
    boolean enviarSms(String telefone, String texto);
}
