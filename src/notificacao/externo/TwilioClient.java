package notificacao.externo;

/** Adaptee (simulado): cliente de SMS de terceiro. Pode falhar de forma intermitente. */
public class TwilioClient {
    private int falhasRestantes;

    public TwilioClient(int falhasSimuladas) { this.falhasRestantes = falhasSimuladas; }

    /** Retorna o SID da mensagem ou lança exceção em caso de instabilidade. */
    public String createMessage(String from, String to, String body) {
        if (falhasRestantes > 0) {
            falhasRestantes--;
            throw new IllegalStateException("Twilio 503 Service Unavailable");
        }
        System.out.println("      [Twilio] SMS " + from + " -> " + to + " (" + body.length() + " caracteres)");
        return "SM" + Math.abs(to.hashCode());
    }
}
